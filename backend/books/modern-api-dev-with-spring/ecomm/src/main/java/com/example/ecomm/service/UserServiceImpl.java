package com.example.ecomm.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecomm.entity.AddressEntity;
import com.example.ecomm.entity.CardEntity;
import com.example.ecomm.entity.UserEntity;
import com.example.ecomm.entity.UserTokenEntity;
import com.example.ecomm.exception.CustomerNotFoundException;
import com.example.ecomm.exception.GenericAlreadyExistsException;
import com.example.ecomm.exception.InvalidRefreshTokenException;
import com.example.ecomm.exception.ResourceNotFoundException;
import com.example.ecomm.mapper.UserMapper;
import com.example.ecomm.model.RefreshToken;
import com.example.ecomm.model.SignedInUser;
import com.example.ecomm.model.User;
import com.example.ecomm.repository.UserRepository;
import com.example.ecomm.repository.UserTokenRepository;
import com.example.ecomm.security.Constants;
import com.example.ecomm.security.JwtManager;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository repository;

  private final UserTokenRepository userTokenRepository;

  private final JwtManager tokenManager;

  private final PasswordEncoder bCryptPasswordEncoder;

  private final UserMapper mapper;

  @Override
  public void deleteCustomerById(String id) {
    repository.deleteById(UUID.fromString(id));
  }

  @Override
  public Optional<Iterable<AddressEntity>> getAddressesByCustomerId(String id) {
    return repository.findById(UUID.fromString(id)).map(UserEntity::getAddresses);
  }

  @Override
  public Iterable<UserEntity> getAllCustomers() {
    return repository.findAll();
  }

  @Override
  public Optional<CardEntity> getCardByCustomerId(String id) {
    Set<CardEntity> cards = repository.findById(UUID.fromString(id))
        .map(UserEntity::getCards)
        .orElseThrow(() -> new CustomerNotFoundException(String.format(" - %s", id)));

    if (cards.isEmpty()) {
      throw new ResourceNotFoundException(String.format("No card found for customer (ID: %s)", id));
    }

    return cards.stream().findFirst();
  }

  @Override
  public Optional<UserEntity> getCustomerById(String id) {
    return repository.findById(UUID.fromString(id));
  }

  @Override
  public UserEntity findUserByUsername(String username) {
    if (Strings.isBlank(username)) {
      throw new UsernameNotFoundException("Invalid user.");
    }

    final String uname = username.trim();
    Optional<UserEntity> oUserEntity = repository.findByUsername(uname);
    UserEntity userEntity = oUserEntity
        .orElseThrow(() -> new UsernameNotFoundException(String.format("Given user(%s) not found.", uname)));

    return userEntity;
  }

  @Override
  @Transactional
  public Optional<SignedInUser> createUser(User user) {
    Integer count = repository.findByUsernameOrEmail(user.getUsername(), user.getEmail());

    if (count > 0) {
      throw new GenericAlreadyExistsException("Use different username and email.");
    }

    UserEntity userEntity = repository.save(toEntity(user));

    return Optional.of(createSignedUserWithRefreshToken(userEntity));
  }

  @Override
  @Transactional
  public SignedInUser getSignedInUser(UserEntity userEntity) {
    userTokenRepository.deleteByUserId(userEntity.getId());
    return createSignedUserWithRefreshToken(userEntity);
  }

  @Override
  @Transactional
  public Optional<SignedInUser> getAccessToken(RefreshToken refreshToken) {
    UserTokenEntity userToken = userTokenRepository
        .findByRefreshToken(refreshToken.getRefreshToken())
        .orElseThrow(() -> new InvalidRefreshTokenException("Invalid token."));

    userTokenRepository.delete(userToken);

    if (userToken.getExpiresAt().before(Timestamp.from(Instant.now()))) {
      throw new InvalidRefreshTokenException("Expired token.");
    }

    // 리프레시 토큰 회전(rotation): 재사용 시마다 이전 토큰은 폐기하고 새 토큰을 발급해
    // 탈취된 토큰이 재사용되는 것을 방지
    return Optional.of(createSignedUserWithRefreshToken(userToken.getUser()));
  }

  @Override
  public void removeRefreshToken(RefreshToken refreshToken) {
    userTokenRepository
        .findByRefreshToken(refreshToken.getRefreshToken())
        .ifPresentOrElse(userTokenRepository::delete, () -> {
          throw new InvalidRefreshTokenException("Invalid token.");
        });
  }

  private SignedInUser createSignedUserWithRefreshToken(UserEntity userEntity) {
    return createSignedInUser(userEntity).refreshToken(createRefreshToken(userEntity));
  }

  private SignedInUser createSignedInUser(UserEntity userEntity) {
    String token = tokenManager.create(org.springframework.security.core.userdetails.User.builder()
        .username(userEntity.getUsername())
        .password(userEntity.getPassword())
        .authorities(Objects.nonNull(userEntity.getRole()) ? userEntity.getRole().name() : "")
        .build());

    return new SignedInUser()
        .username(userEntity.getUsername())
        .accessToken(token)
        .userId(userEntity.getId().toString());
  }

  private String createRefreshToken(UserEntity user) {
    String token = RandomHolder.randomKey(128);
    Timestamp expiresAt = Timestamp.from(Instant.now().plusMillis(Constants.REFRESH_EXPIRATION_TIME));

    userTokenRepository.save(new UserTokenEntity()
        .setRefreshToken(token)
        .setExpiresAt(expiresAt)
        .setUser(user));

    return token;
  }

  private UserEntity toEntity(User user) {
    UserEntity userEntity = mapper.toEntity(user);

    userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

    return userEntity;
  }

  private static class RandomHolder {

    static final Random random = new SecureRandom();

    public static String randomKey(int length) {
      return String.format("%" + length + "s", new BigInteger(length * 5 /* base 32, 2^5 */, random).toString(32))
          .replace('\u0020', '0');
    }
  }
}

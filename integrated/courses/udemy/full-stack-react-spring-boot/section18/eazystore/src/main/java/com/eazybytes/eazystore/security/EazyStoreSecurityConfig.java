package com.eazybytes.eazystore.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.eazybytes.eazystore.filter.JwtTokenValidatorFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class EazyStoreSecurityConfig {

    private final List<String> publicPaths;

    // @Bean
    // SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws
    // Exception {
    // return http.authorizeHttpRequests((request) -> request
    // // .requestMatchers(HttpMethod.GET, "api/v1/products/**").permitAll()
    // .requestMatchers("api/v1/products/**", "api/v1/contacts/**").permitAll()
    // .requestMatchers("api/v1/dummy/**").authenticated()
    // .anyRequest().authenticated())
    // .formLogin(withDefaults())
    // .httpBasic(withDefaults())
    // .build();
    // }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                // .csrf(csrfConfig -> csrfConfig.disable())
                // .csrf(csrfConfig -> csrfConfig.ignoringRequestMatchers("/api/v1/auth/login"))
                .csrf(csrfConfig -> csrfConfig.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))
                .cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests((request) -> {
                    publicPaths.forEach((path) -> request.requestMatchers(path).permitAll());
                    request.requestMatchers("/api/v1/admin/**")
                            // .hasAuthority("VIEWORDER");
                            // .hasAnyAuthority("VIEWORDER", "CONFIRMORDER", "DELETEORDER");
                            .hasRole("ADMIN");
                    request.requestMatchers("/eazystore/actuator/**").hasRole("OPS_ENG");
                    request.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**")
                            .hasAnyRole("DEV_ENG", "QA_ENG");
                    request.anyRequest()
                            // .authenticated();
                            // .hasAuthority("USER");
                            // .hasRole("DUMMY");
                            .hasAnyRole("USER", "ADMIN");
                })
                .addFilterBefore(new JwtTokenValidatorFilter(publicPaths), BasicAuthenticationFilter.class)
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    // @Bean
    // public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder)
    // {
    // var user1 = User.builder()
    // .username("koolunkle")
    // // .password("eazystore")
    // // .password(passwordEncoder.encode("eazystore"))
    // .password("$2a$12$OvSj0esHiBbu2yd8gl.7judMh/GWu7ez7W0lrzE.YlDfQptj8B/wC")
    // .roles("USER")
    // .build();

    // var user2 = User.builder()
    // .username("admin")
    // // .password("admin")
    // // .password(passwordEncoder.encode("admin"))
    // .password("$2a$12$zLVRmhKwkJwFay8rcvEe4uGFNR4jnCdO43kiQb1IBioqGYil3gLQa")
    // .roles("USER", "ADMIN")
    // .build();

    // return new InMemoryUserDetailsManager(user1, user2);
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationProvider authenticationProvider) {
        // var daoAuthenticationProvider = new
        // DaoAuthenticationProvider(userDetailsService);

        // var daoAuthenticationProvider = new DaoAuthenticationProvider();
        // daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        var providerManager = new ProviderManager(authenticationProvider);

        return providerManager;
    }
}

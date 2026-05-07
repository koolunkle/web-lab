package com.eazybytes.eazystore.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.eazybytes.eazystore.constants.ApplicationConstants;
import com.eazybytes.eazystore.dto.ContactRequestDto;
import com.eazybytes.eazystore.dto.ContactResponseDto;
import com.eazybytes.eazystore.entity.Contact;
import com.eazybytes.eazystore.exception.ResourceNotFoundException;
import com.eazybytes.eazystore.repository.ContactRepository;
import com.eazybytes.eazystore.service.IContactService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements IContactService {

    private final ContactRepository contactRepository;

    @Override
    public boolean saveContact(ContactRequestDto contactRequestDto) {
        Contact contact = transformToEntity(contactRequestDto);
        contactRepository.save(contact);
        return true;
    }

    @Override
    public List<ContactResponseDto> getAllOpenMessages() {
        // List<Contact> contact =
        // contactRepository.findByStatus(ApplicationConstants.OPEN_MESSAGE);
        List<Contact> contact = contactRepository.fetchByStatus(ApplicationConstants.OPEN_MESSAGE);

        return contact.stream().map(this::mapToContactResponseDto).collect(Collectors.toList());
    }

    @Override
    public void updateMessageStatus(Long contactId, String status) {
        Contact contact = contactRepository.findById(contactId).orElseThrow(
                () -> new ResourceNotFoundException("Contact", "ContactId", contactId.toString()));

        contact.setStatus(status);
        contactRepository.save(contact);
    }

    private ContactResponseDto mapToContactResponseDto(Contact contact) {
        ContactResponseDto responseDto = new ContactResponseDto(
                contact.getContactId(),
                contact.getName(),
                contact.getEmail(),
                contact.getMobileNumber(),
                contact.getMessage(),
                contact.getStatus());

        return responseDto;
    }

    private Contact transformToEntity(ContactRequestDto contactRequestDto) {
        Contact contact = new Contact();

        BeanUtils.copyProperties(contactRequestDto, contact);
        contact.setStatus(ApplicationConstants.OPEN_MESSAGE);

        return contact;
    }
}

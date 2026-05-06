package com.eazybytes.eazystore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eazybytes.eazystore.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByStatus(String status);
}
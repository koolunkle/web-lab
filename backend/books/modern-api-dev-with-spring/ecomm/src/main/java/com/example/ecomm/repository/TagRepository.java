package com.example.ecomm.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.example.ecomm.entity.TagEntity;

public interface TagRepository extends CrudRepository<TagEntity, UUID> {
}

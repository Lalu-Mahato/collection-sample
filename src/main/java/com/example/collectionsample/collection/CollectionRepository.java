package com.example.collectionsample.collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.collectionsample.collection.entity.Collection;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Integer> {

}

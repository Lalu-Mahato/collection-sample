package com.example.collectionsample.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.collectionsample.group.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

}

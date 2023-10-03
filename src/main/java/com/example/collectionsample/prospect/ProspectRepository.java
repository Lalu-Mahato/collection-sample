package com.example.collectionsample.prospect;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.collectionsample.prospect.entity.Prospect;

@Repository
public interface ProspectRepository extends JpaRepository<Prospect, Integer> {

}

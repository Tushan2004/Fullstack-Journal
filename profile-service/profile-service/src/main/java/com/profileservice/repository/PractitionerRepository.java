package com.profileservice.repository;

import com.profileservice.entity.Practitioner;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PractitionerRepository extends JpaRepository<Practitioner, Long> {
    
    @Override
    List<Practitioner> findAll();
}

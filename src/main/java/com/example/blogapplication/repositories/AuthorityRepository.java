package com.example.blogapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blogapplication.models.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {

    
}

package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Subscripcion;

@Repository
public interface SubscripcionRepository extends MongoRepository<Subscripcion, String> {
	
	Optional<Subscripcion> findOneByEmail(String email);
}

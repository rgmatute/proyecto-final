package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Parametros;

@Repository
public interface ParametroRepository extends MongoRepository<Parametros, String> {
	
	Optional<Parametros> findOneByCode(String code);

}

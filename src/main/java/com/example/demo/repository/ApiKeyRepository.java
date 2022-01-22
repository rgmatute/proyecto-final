package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.ApiKey;

@Repository
public interface ApiKeyRepository extends MongoRepository<ApiKey, String> {
	
	
	List<ApiKey> findAllByOwner(String userId);
	
	Optional<ApiKey> findOneByApiKey(String apikey);

}

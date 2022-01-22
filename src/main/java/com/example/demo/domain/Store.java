package com.example.demo.domain;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "store")
public class Store {
	
	@Id
	private String id;
	
	@Field("api_key")
	private String apiKey;
	
	@Field("resource_id")
	private String resourceId;
	
	@Field("owner")
	private String owner;
	
	@Field("request_date")
	private Instant requestDate = Instant.now();
	
}

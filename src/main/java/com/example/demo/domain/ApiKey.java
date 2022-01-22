package com.example.demo.domain;

import java.time.Instant;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class ApiKey {
	
	@Id
	private String id;

	private String owner;

	private String apiKey;

	private Instant createdAt = Instant.now();

}

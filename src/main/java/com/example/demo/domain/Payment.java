package com.example.demo.domain;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class Payment {

	@Id
	private String id;
	
	@Field("subscription_id")
	private String subscriptionId;
	
	@Field("resource_id")
	private String resourceId;
	
	@Field("order_number")
	private String orderNumber;
	
	@Field("created_at")
	private Instant createdAt = Instant.now();

}

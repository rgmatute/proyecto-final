package com.example.demo.domain;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Resource {

	@Id
	private String id;

	private String documentId;

}

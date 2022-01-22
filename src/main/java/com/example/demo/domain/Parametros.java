package com.example.demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "parametros")
public class Parametros {

	@Id
	private String id;

	private String code;

	private String name;

	private String description;

	private Object value;

}

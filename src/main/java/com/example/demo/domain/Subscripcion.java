package com.example.demo.domain;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Document("subscripcion")
public class Subscripcion implements Serializable {

	private String id;
	private String catalogoId;
	private String email;
	private String nombre;
	private String apellidos;
	private String direccion;
	private double credito;
	private double orderPay;
	private boolean isActivo;

}

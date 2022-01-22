package com.example.demo.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Document(collection = "catalogo")
public class Catalogo implements Serializable {

	@Id
	private String id;

	private String nombre;
	private String descripcion;
	private double precio;
	private int descuento;
	private boolean isOpen;
	private double credito;

}

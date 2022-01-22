package com.example.demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class PayOrder {

	@Id
	private String id;
	
	@Field("subscription_id")
	private String subscriptionId;
	
	private String descripcion;
	private String datalle;
	private Object transaction;
	private Object card;
	private double subtotal;
	private double ivaPorcentaje;
	private double impuestos;
	private double descuento;
	private double total;
	private boolean finalConsumer;

}

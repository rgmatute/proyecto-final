package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Catalogo;
import com.example.demo.domain.PayOrder;
import com.example.demo.domain.Subscripcion;
import com.example.demo.repository.CatalogoRepository;
import com.example.demo.repository.PayOrderRepository;
import com.example.demo.repository.SubscripcionRepository;

@Service
public class SubscripcionServices {

	@Autowired
	SubscripcionRepository subscripcionRepository;
	
	@Autowired
	CatalogoRepository catalogoRepository;
	
	@Autowired
	PayOrderRepository payOrderRepository;
	
	@Value("${epico.param.iva}")
	private String ivaPorcentaja;
	

	public List<Subscripcion> findAll() {
		return subscripcionRepository.findAll();
	}

	public Subscripcion save(Subscripcion subscripcion) throws Exception {
		
		Optional<Subscripcion> subOptional = subscripcionRepository.findOneByEmail(subscripcion.getEmail());
		
		if(subOptional.isPresent()) {
			throw new Exception("Ya existe una cuenta con el correo especificado, Inicie session o recupere su contraseña.");
		}
		
		Optional<Catalogo> catalogoOptional = catalogoRepository.findById(subscripcion.getCatalogoId());
		if(catalogoOptional.isEmpty()) {
			throw new Exception("EL Catalogo seleccionado no esta disponible.");
		}
		
		Catalogo catalogo = catalogoOptional.get();
		
		double desc = catalogo.getDescuento();
		double precio = catalogo.getPrecio();
		
		PayOrder payOrder = null;
		
		if(desc == 100) {
			subscripcion.setActivo(true);
		}else {
			subscripcion.setActivo(false);
		}
		subscripcion.setCredito(catalogo.getCredito());
		
		subscripcion = subscripcionRepository.save(subscripcion);
		
		// Guardo la orden de pago
		this.generaOrderPago(subscripcion, catalogo);
		
		return subscripcion;
	}
	
	
	private void generaOrderPago(Subscripcion subscripcion, Catalogo catalogo) {
		PayOrder payOrder = new PayOrder();
		payOrder.setDatalle(catalogo.getNombre().concat(" | ").concat(catalogo.getDescripcion()));
		payOrder.setSubscriptionId(subscripcion.getId());
		
		double subtotal = catalogo.getPrecio();
		
		double iva = 0;
		iva = subtotal * Double.valueOf(ivaPorcentaja) / 100;
		
		double total = subtotal + iva;
		
		double descuento = 0;
		if(catalogo.getDescuento() > 0 ) {
			descuento = total * Double.valueOf(catalogo.getDescuento()) / 100;
		}
		
		total = total - descuento;
		
		payOrder.setImpuestos(iva);
		payOrder.setIvaPorcentaje(Double.valueOf(ivaPorcentaja));
		payOrder.setSubtotal(subtotal);
		payOrder.setDescuento(descuento);
		payOrder.setTotal(total);
		
		payOrderRepository.save(payOrder);
	}
	
	
	

	public Subscripcion update(Subscripcion subscripcion) throws Exception {
		return this.save(subscripcion);
	}

	public Subscripcion findById(String id) {
		Optional<Subscripcion> subscripcionOptional = subscripcionRepository.findById(id);

		return subscripcionOptional.isEmpty() ? null : subscripcionOptional.get();
	}

	public void delete(String id) {
		subscripcionRepository.deleteById(id);
	}

}

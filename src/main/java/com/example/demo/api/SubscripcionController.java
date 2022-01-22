package com.example.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Subscripcion;
import com.example.demo.services.SubscripcionServices;

@RestController
@RequestMapping("open-api")
public class SubscripcionController {

	@Autowired
	SubscripcionServices subscripcionServices;

	@GetMapping("/subscripcion")
	public List<Subscripcion> findAll() {
		return subscripcionServices.findAll();
	}

	@PostMapping("/subscripcion")
	public Subscripcion save(@RequestBody Subscripcion subscripcion) throws Exception {
		return subscripcionServices.save(subscripcion);
	}

	@GetMapping("/subscripcion/{id}")
	public Subscripcion findById(String id) {
		return subscripcionServices.findById(id);
	}

	@DeleteMapping("/subscripcion/{id}")
	public void delete(String id) {
		subscripcionServices.delete(id);
	}

}

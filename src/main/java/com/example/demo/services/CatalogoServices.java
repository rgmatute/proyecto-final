package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Catalogo;
import com.example.demo.repository.CatalogoRepository;

@Service
public class CatalogoServices {

	@Autowired
	CatalogoRepository catalogoRepository;

	public List<Catalogo> findAll() {
		return catalogoRepository.findAll();
	}

	public Catalogo save(Catalogo catalog) {
		return catalogoRepository.save(catalog);
	}
	
	public Catalogo update(Catalogo catalog) {
		return this.save(catalog);
	}

	public Catalogo findById(String id) {
		Optional<Catalogo> catalogoOptional = catalogoRepository.findById(id);

		return catalogoOptional.isEmpty() ? null : catalogoOptional.get();
	}
	
	public void delete(String id) {
		catalogoRepository.deleteById(id);
	}

}

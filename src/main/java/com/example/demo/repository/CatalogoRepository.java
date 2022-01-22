package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Catalogo;

@Repository
public interface CatalogoRepository extends MongoRepository<Catalogo, String> {
	
	
	//List<Catalogo> findAllByCreditoIsNull();
	
	@Query("{ $and: [{ 'credito': ?0 } ] }")
	List<Catalogo> findAllByCredito(double credito);
	
	// List<Catalogo> findAllByNombreAndPrecio(String nombre, double precio);
	
	
	List<Catalogo> findAllByNombreIsNullAndDescripcionIsNull();
	
}

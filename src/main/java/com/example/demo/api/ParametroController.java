package com.example.demo.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Parametros;
import com.example.demo.repository.ParametroRepository;

@RestController
@RequestMapping("open-api")
public class ParametroController {
	
	@Autowired
	ParametroRepository parametroRepository;
	
	@PostMapping("/parametros")
	public Parametros save(@RequestBody Parametros parametro) throws Exception {
		
		Optional<Parametros> paraOptional = parametroRepository.findOneByCode(parametro.getCode());
		
		if(paraOptional.isPresent()) {
			throw new Exception("El parametro con codigo: " + parametro.getCode() + " ya existe");
		}
		return parametroRepository.save(parametro);
	}
	
	@GetMapping("/parametros/{codigo}")
	public ResponseEntity<?> get(@PathVariable String codigo) {
		
		Optional<Parametros> paraOptional = parametroRepository.findOneByCode(codigo);
		
		return ResponseEntity
				.of(paraOptional);
		
	}

}






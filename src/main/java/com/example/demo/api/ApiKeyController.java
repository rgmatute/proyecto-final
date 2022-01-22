package com.example.demo.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Utils.EpicoUtils;
import com.example.demo.domain.ApiKey;
import com.example.demo.domain.Subscripcion;
import com.example.demo.repository.ApiKeyRepository;
import com.example.demo.repository.SubscripcionRepository;

@RestController
@RequestMapping("open-api")
public class ApiKeyController {

	@Autowired
	ApiKeyRepository apiKeyRepository;
	
	@Autowired
	SubscripcionRepository subscripcionRepository;

	@PostMapping("/api-keys")
	public ApiKey save(@RequestBody ApiKey apiKey) throws Exception {
		
		Optional<Subscripcion> subOptional = subscripcionRepository.findById(apiKey.getOwner());
		
		if(subOptional.isEmpty()) {
			throw new Exception("la subscripcion que estas relaconando no existe. subscribete :).");
		}
		
		if(subOptional.isPresent()) {
			Subscripcion subscripcion = subOptional.get();
			if(!subscripcion.isActivo()) {
				throw new Exception("Tu Cuenta esta inactiva, necesitas cancelar para consumir los servicios.");
			}
		}
		
		ApiKey apiKey2 = new ApiKey();
		apiKey2.setOwner(apiKey.getOwner());
		apiKey2.setApiKey(EpicoUtils.generateApiKey());
		
		return apiKeyRepository.save(apiKey2);
	}

	@GetMapping("/api-keys/{subscriptionId}")
	public List<ApiKey> findAll(@PathVariable String subscriptionId) {
		return apiKeyRepository.findAllByOwner(subscriptionId);
	}

}

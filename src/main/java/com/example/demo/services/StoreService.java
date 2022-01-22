package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Utils.EpicoUtils;
import com.example.demo.domain.ApiKey;
import com.example.demo.domain.Parametros;
import com.example.demo.domain.Resource;
import com.example.demo.domain.Store;
import com.example.demo.domain.Subscripcion;
import com.example.demo.repository.ApiKeyRepository;
import com.example.demo.repository.ParametroRepository;
import com.example.demo.repository.ResourceRepository;
import com.example.demo.repository.StoreRepository;
import com.example.demo.repository.SubscripcionRepository;

@Service
public class StoreService {

	@Autowired
	GridFileService gridFileService;

	@Autowired
	StoreRepository storeRepository;

	@Autowired
	ResourceRepository resourceRepository;

	@Autowired
	ApiKeyRepository apiKeyRepository;

	@Autowired
	ParametroRepository paraRepository;

	@Autowired
	SubscripcionRepository subscripcionRepository;

	public Store publicar(MultipartFile resource, String apiKey) throws Exception {
		
		ApiKey apikeyObject = tratarApiKey(apiKey);
		
		if(this.tratarSubscripcion(apikeyObject.getOwner())) {

			String documenId = gridFileService.store(resource);
	
			Resource resource2 = new Resource();
			resource2.setDocumentId(documenId);
			resource2 = resourceRepository.save(resource2);
	
			Store store = new Store();
			store.setApiKey(apiKey);
			store.setResourceId(resource2.getId());
			store.setOwner(apikeyObject.getOwner());
	
			storeRepository.save(store);
	
			return store;
		}
		
		throw new Exception("No se pudo continuar");
	}

	private ApiKey tratarApiKey(String apiKey) throws Exception {
		// consulto los api key
		Optional<ApiKey> apikeyOptional = apiKeyRepository.findOneByApiKey(apiKey);
		if (apikeyOptional.isEmpty()) {
			throw new Exception("El api key no existe");
		}
		return apikeyOptional.get();
	}

	private boolean tratarSubscripcion(String owner) throws Exception {
		Optional<Subscripcion> subscripcionOptional = subscripcionRepository.findById(owner);
		if (subscripcionOptional.isEmpty()) {
			throw new Exception("SUbscripipcion no existe.");
		}

		Subscripcion subscripcion = subscripcionOptional.get();

		Optional<Parametros> paraOptional = paraRepository.findOneByCode("PRICE_PER_REQUEST");

		if (paraOptional.isEmpty()) {
			throw new Exception(",,,,,,,,,,,,,,,");
		}

		Parametros parametros = paraOptional.get();
		
		EpicoUtils.console("parametros", EpicoUtils.toJson(parametros));

		if (subscripcion.getCredito() > 0
				&& subscripcion.getCredito() > Integer.valueOf(parametros.getValue() + "")) {
			
			Double cre = subscripcion.getCredito() - Integer.valueOf(parametros.getValue() + "");
			subscripcion.setCredito(cre);
			
			subscripcion = subscripcionRepository.save(subscripcion);
			
			return true;
			
			// transaccionar
		} else {
			throw new Exception("No tienes creditos sufiente para cnotinuar......");
		}
	}

}

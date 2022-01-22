package com.example.demo.api;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.services.StoreService;

@RestController
@RequestMapping("open-api")
public class StoreController {
	
	@Autowired
	StoreService storeService;
	
	
	@PostMapping("/store")
	public ResponseEntity<?> publicar(@RequestParam MultipartFile resource, @RequestHeader(value = "api-key", required = false) String apiKey) throws Exception {
		
		if(apiKey == null) {
			return ResponseEntity
					.status(401)
					.body(null);
		}
		
		if(resource.isEmpty()) {
			throw new Exception("Necesito que envies un fichero de lo cnotrario no podre procesarlo.");
		}

		return ResponseEntity
				.status(200)
				.body(storeService.publicar(resource, apiKey));
	}
	

}

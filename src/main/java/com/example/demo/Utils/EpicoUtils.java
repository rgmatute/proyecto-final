package com.example.demo.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bson.internal.Base64;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class EpicoUtils {

	public static String getJWT(String username, String storeCode) {

		int validezInMin = 1; // 1440;
		int validezInSeconds = 60;
		String key = "cpMW1RjvLE41Ra3GwMZYTLhyMwZNesxo";
		String emisorToken = "https://www.google.com";

		int audience = 1;

		Date time = new Date(System.currentTimeMillis());
		List<GrantedAuthority> grandAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		// ALMACENA INFORMACION EN EL TOKEN
		HashMap<String, Object> clain = new HashMap<>();
		clain.put("username", username);
		clain.put("storeCode", storeCode);

		String token = Jwts.builder().setIssuer(emisorToken).setAudience(String.valueOf(audience))
				.setId(MD5(username + time)).setIssuedAt(time).setNotBefore(time)
				.setExpiration(new Date(System.currentTimeMillis() + (validezInSeconds * 1000))).claim("data", clain)
				.claim("roles",
						grandAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.signWith(SignatureAlgorithm.HS256, key.getBytes()).compact();

		return "Bearer " + token;
	}

	public static String MD5(String md5) {

		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());

			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < array.length; i++) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}

			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
			System.out.println("EpicoUtils-MD5-Error: " + e.getMessage());
		}

		return null;
	}

	public static String getFileExtension(String filename) {
		String[] fileNamArray = filename.split("\\.");
		return fileNamArray[fileNamArray.length - 1].toUpperCase();
	}

	public static String getFileExtensionV2(String filename) {
		int lastPoint = filename.lastIndexOf('.');
		return filename.substring(lastPoint + 1, filename.length()).toUpperCase();
	}

	public static String inputStreamToBase64(InputStream in) {
		try {
			return Base64.encode(in.readAllBytes());
		} catch (IOException e) {
			return "";
		}
	}

	public static void console(String title, Object message) {
		consoleTitle(title, message);
	}

	public static void consoleTitle(String title, Object message) {
		println(characterGenerate("*", 20) + title + characterGenerate("*", 20));
		println(message);
		println(characterGenerate("*", (40 + title.length())));
	}

	public static void consoleTitle(String title, Object message, int lengthCharacter) {
		println(characterGenerate("*", (lengthCharacter / 2)) + title + characterGenerate("*", (lengthCharacter / 2)));
		println(message);
		println(characterGenerate("*", (lengthCharacter + title.length())));
	}

	public static void println(Object message) {
		System.out.println(message);
	}

	public static String characterGenerate(String character, int length) {
		String r = "";
		for (int i = 0; i < length; i++) {
			r += character;
		}
		return r;
	}
	
	public static String generateApiKey() {
        return UUID.randomUUID().toString();
    }
	
	public static <T> String toJson(Object entity) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{}";
        try {
            json = objectMapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }



}

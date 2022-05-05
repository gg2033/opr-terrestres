package com.losilegales.oprterrestres;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

@SpringBootApplication
@Configuration
public class OprTerrestresApplication {

	public static void main(String[] args) {
		SpringApplication.run(OprTerrestresApplication.class, args);

	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	@Primary
	public ObjectMapper configureObjectMapper() {
		JavaTimeModule module = new JavaTimeModule();
		LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
		ObjectMapper objectMapperObj = Jackson2ObjectMapperBuilder.json().modules(module)
				.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).build();
		return objectMapperObj;
	}

	@Bean(name = "simpleRestTemplate")
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(3000);
		factory.setReadTimeout(3000);

		return new RestTemplate(factory);
	}
}

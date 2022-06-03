package com.losilegales.oprterrestres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class EmailService {

	@Autowired
	@Qualifier("simpleRestTemplate")
	private RestTemplate simpleRestTemplate;

	@Value("${com.ilegals.email.apikey}")
	private String apiKey;

	@Value("${com.ilegals.email.domain}")
	private String domain;
	
	public void sendEmail(String to, String asunto, String contenido) {
		try {
			HttpResponse<JsonNode> request = Unirest
					.post("https://api.mailgun.net/v3/" + domain
							+ "/messages")
					.basicAuth("api", apiKey)
					.queryString("from", "TheIlegals <Notification@"+domain+">").queryString("to", to)
					.queryString("subject", asunto).queryString("text", contenido).asJson();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

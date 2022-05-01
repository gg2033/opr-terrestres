package com.losilegales.oprterrestres.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@ConfigurationProperties
public class EmailConfig {

	private static final int GMAIL_SMTP_PORT = 587;

	@Autowired
	private Environment env;

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		// Set up Gmail config
		mailSender.setHost(env.getProperty("udeesa.email.sender.host"));
		mailSender.setPort(GMAIL_SMTP_PORT);

		// Set up email config (using udeesa email)
		mailSender.setUsername(env.getProperty("udeesa.email.sender.user"));
		mailSender.setPassword(env.getProperty("udeesa.email.sender.password"));

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", env.getProperty("udeesa.email.sender.debug"));
		return mailSender;
	}

}

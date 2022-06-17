package com.losilegales.oprterrestres;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.losilegales.oprterrestres.automatizacion.Task;

@SpringBootApplication
@Configuration
public class OprTerrestresApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(OprTerrestresApplication.class, args);
		
		try {
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();
            // define the job and tie it to our HelloJob class
            JobDetail job = JobBuilder.newJob(Task.class)
                .withIdentity("job1", "group1")
                .build();

            // Trigger the job to run now, and then repeat every 40 seconds
            CronTrigger trigger = TriggerBuilder.newTrigger()
            		  .withIdentity("trigger3", "group1")
            		  .withSchedule(CronScheduleBuilder.cronSchedule("0 0 13 * * ?"))
            		  .forJob("job1", "group1")
            		  .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);
//            Thread.sleep(60000);

//            scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }

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
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://boot-hheroku.herokuapp.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Access-Control-Allow-Origin",
                "Access-Control-Request-Method", "Access-Control-Request-Headers", "Origin",
                "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

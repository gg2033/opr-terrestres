package com.losilegales.oprterrestres.service;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

@Service
public class LogService {
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public String readLog(String fecha) {
		Resource resource = resourceLoader.getResource("classpath:ilegals_"+fecha+".log");
	    try (Reader reader = new InputStreamReader(resource.getInputStream())) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
//		InputStream inputStream = resource.getInputStream();
//		 return asString(resource);
	}

}

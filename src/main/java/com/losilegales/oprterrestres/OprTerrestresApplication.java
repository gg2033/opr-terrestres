package com.losilegales.oprterrestres;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.entity.Usuario;
import com.losilegales.oprterrestres.repository.UsuarioRepository;
import com.losilegales.oprterrestres.service.OpTerrGoogleSheetService;

@SpringBootApplication
@RestController
public class OprTerrestresApplication {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	public OpTerrGoogleSheetService opTerrGoogleSheetService;

	@GetMapping("/")
    @ResponseBody
    String home() {
	List<Usuario> usuarios = usuarioRepository.findAll();
	try {
		opTerrGoogleSheetService.getSpreadsheetValues();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (GeneralSecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       return usuarios.toString();
    }
	
	public static void main(String[] args) {
		SpringApplication.run(OprTerrestresApplication.class, args);
		
	}

}

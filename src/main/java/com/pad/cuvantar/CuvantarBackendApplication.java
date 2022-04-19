package com.pad.cuvantar;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Cuvantar", description = "Some long and useful description which I didn't bother writing...", version = "v1"))
public class CuvantarBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CuvantarBackendApplication.class, args);
	}

}

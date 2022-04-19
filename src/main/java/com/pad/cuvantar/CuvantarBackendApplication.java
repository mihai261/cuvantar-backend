package com.pad.cuvantar;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Cuvantar", description = "Some long and useful description which I didn't bother writing...", version = "v1"))
@SecurityScheme(name = "cuvantar-api", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class CuvantarBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CuvantarBackendApplication.class, args);
	}

}

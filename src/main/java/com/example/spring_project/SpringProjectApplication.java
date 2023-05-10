package com.example.spring_project;


import com.example.spring_project.login.LoginParameters;
import com.example.spring_project.test.TestAll;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashMap;
import java.util.function.Predicate;


@SpringBootApplication
public class SpringProjectApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext ctx=SpringApplication.run(SpringProjectApplication.class, args);
		TestAll testAll=ctx.getBean(TestAll.class);
		testAll.testAll();


	}
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.paths(Predicate.not(PathSelectors.regex("/error.*")))
				.build();
	}
//	@Bean
//	public HashMap <String, LoginParameters> sessions(){
//		return new HashMap ();
//	}


}

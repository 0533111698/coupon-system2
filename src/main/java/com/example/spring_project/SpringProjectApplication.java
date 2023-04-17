package com.example.spring_project;


import com.example.spring_project.test.TestAll;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class SpringProjectApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx=SpringApplication.run(SpringProjectApplication.class, args);
		TestAll testAll=ctx.getBean(TestAll.class);
		testAll.testAll();


	}

}

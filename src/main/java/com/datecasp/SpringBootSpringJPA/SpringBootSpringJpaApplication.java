package com.datecasp.SpringBootSpringJPA;

import com.datecasp.SpringBootSpringJPA.Entities.Alumno;
import com.datecasp.SpringBootSpringJPA.Repositories.AlumnoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBootSpringJpaApplication {

	public static void main(String[] args)
	{
		ApplicationContext context = SpringApplication.run(SpringBootSpringJpaApplication.class, args);

		AlumnoRepository alumnosRepo = context.getBean(AlumnoRepository.class);

		alumnosRepo.save(new Alumno("Alebrto", 1));

		System.out.println(alumnosRepo.findAll());
		System.out.println(alumnosRepo.count());
	}

}

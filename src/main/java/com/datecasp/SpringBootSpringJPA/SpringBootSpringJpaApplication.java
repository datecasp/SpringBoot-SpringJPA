package com.datecasp.SpringBootSpringJPA;

import com.datecasp.SpringBootSpringJPA.entities.Alumno;
import com.datecasp.SpringBootSpringJPA.helpers.SeederDB;
import com.datecasp.SpringBootSpringJPA.repositories.AlumnoRepository;
import com.datecasp.SpringBootSpringJPA.repositories.CursoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBootSpringJpaApplication {

	public static void main(String[] args)
	{
		ApplicationContext context = SpringApplication.run(SpringBootSpringJpaApplication.class, args);

		AlumnoRepository alumnosRepo = context.getBean(AlumnoRepository.class);
		CursoRepository cursoRepo = context.getBean(CursoRepository.class);

		new SeederDB(alumnosRepo, cursoRepo);
	}

}

package com.datecasp.SpringBootSpringJPA;

import com.datecasp.SpringBootSpringJPA.entities.Alumno;
import com.datecasp.SpringBootSpringJPA.helpers.SeederDB;
import com.datecasp.SpringBootSpringJPA.repositories.AlumnoRepository;
import com.datecasp.SpringBootSpringJPA.repositories.AsignaturaRepository;
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
		AsignaturaRepository asignaturaRepo = context.getBean(AsignaturaRepository.class);

		//Si alumnosRepo no tiene alumnos, usamos el Seeder
		//Si tiene, no usamos el Seeder para no cambiar la BD
		if (alumnosRepo.count() == 0){new SeederDB(alumnosRepo, cursoRepo, asignaturaRepo);}
		//new SeederDB(alumnosRepo, cursoRepo, asignaturaRepo);
	}

}

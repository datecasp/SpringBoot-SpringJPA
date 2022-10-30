package com.datecasp.SpringBootSpringJPA.repositories;

import com.datecasp.SpringBootSpringJPA.entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long>
{
}

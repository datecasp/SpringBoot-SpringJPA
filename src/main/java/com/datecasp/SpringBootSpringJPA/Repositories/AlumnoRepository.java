package com.datecasp.SpringBootSpringJPA.Repositories;

import com.datecasp.SpringBootSpringJPA.Entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long>
{
}

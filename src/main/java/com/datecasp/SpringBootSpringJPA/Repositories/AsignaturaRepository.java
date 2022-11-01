package com.datecasp.SpringBootSpringJPA.repositories;

import com.datecasp.SpringBootSpringJPA.entities.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Long>
{
}

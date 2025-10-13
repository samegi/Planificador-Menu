package com.proyecto.demo.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.demo.model.Dia;

@Repository
public interface DiaRepository extends JpaRepository<Dia, Long> {
    Optional<Dia> findByFecha(LocalDate fecha);
}

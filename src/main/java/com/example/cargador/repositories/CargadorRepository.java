package com.example.cargador.repositories;

import com.example.cargador.models.Cargador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargadorRepository extends JpaRepository<Cargador, Long> {
}

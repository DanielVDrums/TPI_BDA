package bda.tpi.vehiculos.repository;

import bda.tpi.vehiculos.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Integer> {
}

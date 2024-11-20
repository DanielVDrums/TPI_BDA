package bda.tpi.usuarios.repository;

import bda.tpi.usuarios.entity.Interesado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InteresadoRepository extends JpaRepository<Interesado, Integer> {
    Optional<Interesado> findByDocumento(Long documento);
}

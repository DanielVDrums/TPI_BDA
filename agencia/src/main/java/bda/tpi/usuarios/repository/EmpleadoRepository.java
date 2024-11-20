package bda.tpi.usuarios.repository;

import bda.tpi.usuarios.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
}

package cl.cristian.reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.cristian.reservas.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}

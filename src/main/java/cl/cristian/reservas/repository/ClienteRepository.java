package cl.cristian.reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.cristian.reservas.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Page<Cliente> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    Page<Cliente> findByEmail(String email, Pageable pageable);
}

package cl.cristian.reservas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.cristian.reservas.entity.Cliente;
import cl.cristian.reservas.repository.ClienteRepository;
import cl.cristian.reservas.exception.ResourceNotFoundException;

@Service
public class ClienteService {

    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public List<Cliente> listar() {
        return repo.findAll();
    }

    public Cliente buscarPorId(Long id) {
    return repo.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Cliente no encontrado con id: " + id)
            );
    }

    public Cliente crear(Cliente cliente) {
        return repo.save(cliente);
    }

    public void eliminar(Long id) {
    if (!repo.existsById(id)) {
        throw new ResourceNotFoundException("Cliente no encontrado con id: " + id);
    }
    repo.deleteById(id);
    }

    public Cliente actualizar(Long id, Cliente clienteActualizado) {

    Cliente cliente = repo.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Cliente no encontrado con id: " + id)
            );

    cliente.setNombre(clienteActualizado.getNombre());
    cliente.setEmail(clienteActualizado.getEmail());

    return repo.save(cliente);
    }
}

package cl.cristian.reservas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.cristian.reservas.entity.Cliente;
import cl.cristian.reservas.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}

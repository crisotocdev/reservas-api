package cl.cristian.reservas.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cl.cristian.reservas.dto.ClienteRequestDTO;
import cl.cristian.reservas.dto.ClienteResponseDTO;
import cl.cristian.reservas.entity.Cliente;
import cl.cristian.reservas.exception.ResourceNotFoundException;
import cl.cristian.reservas.mapper.ClienteMapper;
import cl.cristian.reservas.repository.ClienteRepository;
import cl.cristian.reservas.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repo;

    public ClienteServiceImpl(ClienteRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<ClienteResponseDTO> listar() {
        return repo.findAll()
                .stream()
                .map(ClienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteResponseDTO buscarPorId(Long id) {
        Objects.requireNonNull(id, "id no puede ser null");
        Cliente cliente = getClienteOrThrow(id);
        return ClienteMapper.toDTO(cliente);
    }

    @Override
    public ClienteResponseDTO crear(ClienteRequestDTO dto) {
        Objects.requireNonNull(dto, "dto no puede ser null");

        Cliente cliente = ClienteMapper.toEntity(dto);
        Cliente saved = repo.save(cliente);
        return ClienteMapper.toDTO(saved);
    }

    @Override
    public ClienteResponseDTO actualizar(Long id, ClienteRequestDTO dto) {
        Objects.requireNonNull(id, "id no puede ser null");
        Objects.requireNonNull(dto, "dto no puede ser null");

        Cliente cliente = getClienteOrThrow(id);

        cliente.setNombre(Objects.requireNonNull(dto.getNombre(), "nombre no puede ser null"));
        cliente.setEmail(Objects.requireNonNull(dto.getEmail(), "email no puede ser null"));
        cliente.setTelefono(dto.getTelefono());

        return ClienteMapper.toDTO(repo.save(cliente));
    }

    @Override
    public void eliminar(Long id) {
        Objects.requireNonNull(id, "id no puede ser null");

        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado con id: " + id);
        }
        repo.deleteById(id);
    }

    private Cliente getClienteOrThrow(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente no encontrado con id: " + id)
                );
    }
}

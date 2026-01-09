package cl.cristian.reservas.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import cl.cristian.reservas.dto.ClienteRequestDTO;
import cl.cristian.reservas.dto.ClienteResponseDTO;
import cl.cristian.reservas.dto.PageResponse;
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
    public PageResponse<ClienteResponseDTO> listar(
            @Nullable String nombre,
            @Nullable String email,
            Pageable pageable
    ) {
        Page<Cliente> page;

        if (email != null && !email.isBlank()) {
            page = repo.findByEmail(email, pageable);
        } else if (nombre != null && !nombre.isBlank()) {
            page = repo.findByNombreContainingIgnoreCase(nombre, pageable);
        } else {
            page = repo.findAll(pageable);
        }

        return new PageResponse<>(
                page.map(ClienteMapper::toDTO).getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = getClienteOrThrow(id);
        return ClienteMapper.toDTO(cliente);
    }

    @Override
    public ClienteResponseDTO crear(ClienteRequestDTO dto) {
        Cliente cliente = ClienteMapper.toEntity(dto);
        return ClienteMapper.toDTO(repo.save(cliente));
    }

    @Override
    public ClienteResponseDTO actualizar(Long id, ClienteRequestDTO dto) {
        Cliente cliente = getClienteOrThrow(id);

        cliente.setNombre(dto.getNombre());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());

        return ClienteMapper.toDTO(repo.save(cliente));
    }

    @Override
    public void eliminar(Long id) {
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

package cl.cristian.reservas.service.impl;

import java.util.Objects;

import org.springframework.data.domain.Pageable;
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

    // =====================
    // LISTAR CON FILTROS Y PAGINACIÓN
    // =====================
    @Override
    public PageResponse<ClienteResponseDTO> listar(
            String nombre,
            String email,
            Pageable pageable) {

        Objects.requireNonNull(pageable, "pageable no puede ser null");

        var page = (email != null && !email.isBlank())
                ? repo.findByEmail(email, pageable)
                : (nombre != null && !nombre.isBlank())
                    ? repo.findByNombreContainingIgnoreCase(nombre, pageable)
                    : repo.findAll(pageable);

        return new PageResponse<>(
                page.map(ClienteMapper::toDTO).getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    // =====================
    // BUSCAR POR ID
    // =====================
    @Override
    public ClienteResponseDTO buscarPorId(Long id) {
        Objects.requireNonNull(id, "id no puede ser null");
        Cliente cliente = getClienteOrThrow(id);
        return ClienteMapper.toDTO(cliente);
    }

    // =====================
    // CREAR
    // =====================
    @Override
    public ClienteResponseDTO crear(ClienteRequestDTO dto) {
        Objects.requireNonNull(dto, "dto no puede ser null");

        Cliente cliente = ClienteMapper.toEntity(dto);
        Cliente saved = repo.save(cliente);
        return ClienteMapper.toDTO(saved);
    }

    // =====================
    // ACTUALIZAR
    // =====================
    @Override
    public ClienteResponseDTO actualizar(Long id, ClienteRequestDTO dto) {
        Objects.requireNonNull(id, "id no puede ser null");
        Objects.requireNonNull(dto, "dto no puede ser null");

        Cliente cliente = getClienteOrThrow(id);

        cliente.setNombre(dto.getNombre());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());

        return ClienteMapper.toDTO(repo.save(cliente));
    }

    // =====================
    // ELIMINAR  ✅ (ESTE FALTABA / NO COINCIDÍA)
    // =====================
    @Override
    public void eliminar(Long id) {
        Objects.requireNonNull(id, "id no puede ser null");

        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado con id: " + id);
        }

        repo.deleteById(id);
    }

    // =====================
    // MÉTODO PRIVADO
    // =====================
    private Cliente getClienteOrThrow(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente no encontrado con id: " + id)
                );
    }
}

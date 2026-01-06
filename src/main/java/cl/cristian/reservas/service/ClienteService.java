package cl.cristian.reservas.service;

import org.springframework.data.domain.Pageable;

import cl.cristian.reservas.dto.ClienteRequestDTO;
import cl.cristian.reservas.dto.ClienteResponseDTO;
import cl.cristian.reservas.dto.PageResponse;

public interface ClienteService {

    PageResponse<ClienteResponseDTO> listar(
            String nombre,
            String email,
            Pageable pageable
    );

    ClienteResponseDTO buscarPorId(Long id);

    ClienteResponseDTO crear(ClienteRequestDTO dto);

    ClienteResponseDTO actualizar(Long id, ClienteRequestDTO dto);

    void eliminar(Long id);
}

package cl.cristian.reservas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import cl.cristian.reservas.dto.ClienteRequestDTO;
import cl.cristian.reservas.dto.ClienteResponseDTO;
import cl.cristian.reservas.service.ClienteService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(
    name = "Clientes",
    description = "Operaciones CRUD para la gestión de clientes"
)

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @Operation(
    summary = "Listar clientes",
    description = "Obtiene el listado completo de clientes registrados"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida correctamente")
    })
    @GetMapping
    public List<ClienteResponseDTO> listar() {
        return service.listar();
    }

    @Operation(
    summary = "Obtener cliente por ID",
    description = "Busca un cliente específico según su identificador"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ClienteResponseDTO obtener(@NonNull @PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @Operation(
    summary = "Crear cliente",
    description = "Registra un nuevo cliente en el sistema"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponseDTO crear(
            @NonNull @Valid @RequestBody ClienteRequestDTO dto) {

        return service.crear(dto);
    }

    @Operation(
    summary = "Actualizar cliente",
    description = "Actualiza los datos de un cliente existente"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente actualizado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ClienteResponseDTO actualizar(
            @NonNull @PathVariable Long id,
            @NonNull @Valid @RequestBody ClienteRequestDTO dto) {

        return service.actualizar(id, dto);
    }

    @Operation(
    summary = "Eliminar cliente",
    description = "Elimina un cliente según su ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cliente eliminado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@NonNull @PathVariable Long id) {
        service.eliminar(id);
    }
}

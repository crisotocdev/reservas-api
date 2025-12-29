package cl.cristian.reservas.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "ClienteResponseDTO",
    description = "Datos devueltos por la API al consultar un cliente"
)
public class ClienteResponseDTO {

    @Schema(
        description = "Identificador único del cliente",
        example = "1"
    )
    private Long id;

    @Schema(
        description = "Nombre completo del cliente",
        example = "Juan Pérez"
    )
    private String nombre;

    @Schema(
        description = "Correo electrónico del cliente",
        example = "juan.perez@gmail.com"
    )
    private String email;

    @Schema(
        description = "Teléfono de contacto del cliente",
        example = "+56 9 1234 5678",
        nullable = true
    )
    private String telefono;

    public ClienteResponseDTO() {
    }

    public ClienteResponseDTO(Long id, String nombre, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}

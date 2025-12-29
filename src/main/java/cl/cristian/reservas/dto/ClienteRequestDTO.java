package cl.cristian.reservas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "ClienteRequestDTO",
    description = "Datos necesarios para crear o actualizar un cliente"
)
public class ClienteRequestDTO {

    @Schema(
        description = "Nombre completo del cliente",
        example = "Juan Pérez",
        minLength = 2,
        maxLength = 100
    )
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100)
    private String nombre;

    @Schema(
        description = "Correo electrónico del cliente (debe ser único)",
        example = "juan.perez@gmail.com",
        format = "email"
    )
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;

    @Schema(
        description = "Teléfono de contacto del cliente (opcional)",
        example = "+56 9 1234 5678",
        maxLength = 20,
        nullable = true
    )
    @Size(max = 20)
    private String telefono;

    // Getters y Setters
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

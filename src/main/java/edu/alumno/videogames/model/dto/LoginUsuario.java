package edu.alumno.videogames.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginUsuario {

    @Size(min = 5, message = "El nickname debe de tener un tamaño mínimo de 5 caracteres")
    @Schema(example = "usuario123", description = "Nickname del usuario. Mínimo 5 caracteres")
    private String nickname;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "El password debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un carácter especial")
    @Schema(example = "Password@123", description = "Contraseña del usuario. Debe tener al menos 8 caracteres, incluyendo una mayúscula, una minúscula, un número y un carácter especial", required = true)
    private String password;
}

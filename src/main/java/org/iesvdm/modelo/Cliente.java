package org.iesvdm.modelo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;

//La anotación @Data de lombok proporcionará el código de:
//getters/setters, toString, equals y hashCode
//propio de los objetos POJOS o tipo Beans
@Data
//Para generar un constructor con lombok con todos los args
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {

    private long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 30, message = "El nombre no puede superar los 30 caracteres")
    private String nombre;

    @NotBlank(message = "El primer apellido es obligatorio")
    @Size(max = 30, message = "El primer apellido no puede superar los 30 caracteres")
    private String apellido1;

    // Apellido2 es opcional, no se requiere @NotBlank
    private String apellido2;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 50, message = "La ciudad no puede superar los 50 caracteres")
    private String ciudad;

    @Min(value = 100, message = "La categoría mínima es 100")
    @Max(value = 1000, message = "La categoría máxima es 1000")
    private int categoria;
	
}

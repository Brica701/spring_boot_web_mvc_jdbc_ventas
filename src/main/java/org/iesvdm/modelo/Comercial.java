package org.iesvdm.modelo;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comercial {

    private int id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 30, message = "El nombre no puede superar los 30 caracteres")
    private String nombre;

    @NotBlank(message = "El primer apellido es obligatorio")
    @Size(max = 30, message = "El primer apellido no puede superar los 30 caracteres")
    private String apellido1;

    // Apellido2 es opcional
    private String apellido2;

    @DecimalMin(value = "0.276", inclusive = true, message = "La comisión mínima es 0.276")
    @DecimalMax(value = "0.946", inclusive = true, message = "La comisión máxima es 0.946")
    private BigDecimal comision;
}

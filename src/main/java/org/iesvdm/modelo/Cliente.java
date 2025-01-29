package org.iesvdm.modelo;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {

	private int id;

	@NotBlank(message = "Por favor pon tu nombre")
	@Size(min = 3, message = "Minimo tiene que ser de mas de 2 palabras")
	@Size (max = 30, message = "Nombre como máximo de 30 palabras")

	private String nombre;

	@Size(max = 30, message = "Por favor que sea el apellido menor a 30 palabras")
	private String apellido1;


	private String apellido2; //Opcional el campo

	@NotBlank(message = "Pon una ciudad obligatoriamente")
	@Size(max = 50, message = "Introduca el nombre con un minimo de 50 letras")
	private String ciudad;

	@DecimalMax(value = "100.0", message = "La categoría debe ser al menos 100 palabras")
	@DecimalMin(value = "100", message = "La categoría debe ser al menos 100 palabras")

	private int categoria;

}
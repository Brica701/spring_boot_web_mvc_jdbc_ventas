package org.iesvdm.modelo;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comercial {

	private int id;

	@NotBlank(message = "{comercial.nombre.obligatorio}")
	@Size(max = 30, message = "{comercial.nombre.longitud}")
	private String nombre;

	@NotBlank(message = "{comercial.apellido1.obligatorio}")
	@Size(max = 30, message = "{comercial.apellido1.longitud}")
	private String apellido1;


	private String apellido2;

	@DecimalMin(value = "0.276", inclusive = true, message = "{comercial.comision.min}")
	@DecimalMax(value = "0.946", inclusive = true, message = "{comercial.comision.max}")
	private float comision;


}

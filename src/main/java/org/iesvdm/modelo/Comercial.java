package org.iesvdm.modelo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comercial {

	private int id;

	@NotBlank(message = "Introduce un nombre")
	@Size(min = 3, message = "Pon un nombre de mas de 3 letras")
	@Size(max = 30,message = "Menos a 30 letras")
	private String nombre;

	@NotBlank(message = "Introduce un apellido")
	@Size(max = 30,message = "Menos a 30 letras")
	private String apellido1;


	private String apellido2;
	private float comision;



}

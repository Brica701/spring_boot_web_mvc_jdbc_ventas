package org.iesvdm.modelo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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


	@NotBlank(message = "{cliente.nombre.obligatorio}")
	@Size(max = 30, message = "{cliente.nombre.longitud}")
	private String nombre;

	@NotBlank(message = "{cliente.apellido1.obligatorio}")
	@Size(max = 30, message = "{cliente.apellido1.longitud}")
	private String apellido1;
	private String apellido2;
	@NotBlank(message = "{cliente.ciudad.obligatorio}")
	@Size(max = 50, message = "{cliente.ciudad.longitud}")
	private String ciudad;

	@Min(value = 100, message = "{cliente.categoria.min}")
	@Max(value = 1000, message = "{cliente.categoria.max}")
	private int categoria;


}

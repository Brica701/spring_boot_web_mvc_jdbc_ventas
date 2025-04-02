package org.iesvdm.modelo;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE) // Evita problemas con el constructor completo
@RequiredArgsConstructor // Crea un constructor sin la lista de pedidos
public class Comercial {

	private final int id;
	private final String nombre;
	private final String apellido1;
	private final String apellido2;
	private final float comision;

	private List<Pedido> pedidos;
}

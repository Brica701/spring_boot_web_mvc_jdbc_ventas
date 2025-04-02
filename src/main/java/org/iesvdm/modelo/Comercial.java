package org.iesvdm.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comercial {

	private int id;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private float comision;

	private List<Pedido> pedidos;
}
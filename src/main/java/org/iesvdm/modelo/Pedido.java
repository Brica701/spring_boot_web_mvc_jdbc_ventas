package org.iesvdm.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pedido {
    private int id;
    private int idCliente;
    private int idComercial;
    private LocalDate fecha;
    private float total;

}

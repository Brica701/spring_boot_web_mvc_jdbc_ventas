package org.iesvdm.mapper;

import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.modelo.Cliente;

import java.lang.annotation.Target;
@Mapper(componentModel = "spring")
public interface ClienteMapper {
    public ClienteDTO clienteAClienteDTO(Cliente cliente, Integer numeroTotalPedidos);
}

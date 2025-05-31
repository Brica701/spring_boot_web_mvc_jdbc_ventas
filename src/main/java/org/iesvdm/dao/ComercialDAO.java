package org.iesvdm.dao;

import java.util.List;
import java.util.Optional;

import org.iesvdm.modelo.Comercial;

public interface ComercialDAO {
	
	public void create(Comercial cliente);
	
	public List<Comercial> getAll();
	public Optional<Comercial>  find(int id);

	int getCantidadPedidos(int id_comercial);

	public void update(Comercial cliente);
	
	public void delete(long id);

}

package org.iesvdm.repository;

import java.util.List;
import java.util.Optional;

import org.iesvdm.modelo.Comercial;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//Anotación lombok para logging (traza) de la aplicación
@Slf4j
@Repository
//Utilizo lombok para generar el constructor
@AllArgsConstructor
public class ComercialRepository {

	//JdbcTemplate se inyecta por el constructor de la clase automáticamente
	//
	private JdbcTemplate jdbcTemplate;
	
    public void create(Comercial comercial) {
        int rows = jdbcTemplate.update(
                "INSERT INTO comercial (nombre, apellido1, apellido2, comision) VALUES (?, ?, ?, ?)",
                comercial.getNombre(),
                comercial.getApellido1(),
                comercial.getApellido2(),
                comercial.getComision()
        );

        log.info("Insertados {} registros.", rows);
    }

	public List<Comercial> getAll() {
		
		List<Comercial> listComercial = jdbcTemplate.query(
                "SELECT * FROM comercial",
                (rs, rowNum) -> new Comercial(rs.getInt("id"), 
                							  rs.getString("nombre"), 
                							  rs.getString("apellido1"),
                							  rs.getString("apellido2"), 
                							  rs.getFloat("comision"))
                						 	
        );
		
		log.info("Devueltos {} registros.", listComercial.size());
		
        return listComercial;
	}

    public Optional<Comercial> find(int id) {
        Comercial comercial = jdbcTemplate.queryForObject(
                "SELECT * FROM comercial WHERE id = ?",
                (rs, rowNum) -> new Comercial(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getFloat("comision")
                ),
                id
        );

        log.info("Encontrado comercial: {}", comercial);
        return Optional.ofNullable(comercial);
    }

    public void update(Comercial comercial) {
        int rows = jdbcTemplate.update(
                "UPDATE comercial SET nombre = ?, apellido1 = ?, apellido2 = ?, comision = ? WHERE id = ?",
                comercial.getNombre(),
                comercial.getApellido1(),
                comercial.getApellido2(),
                comercial.getComision(),
                comercial.getId()
        );

        log.info("Actualizados {} registros.", rows);
    }

    public void delete(long id) {
        int rows = jdbcTemplate.update("DELETE FROM comercial WHERE id = ?", id);
        log.info("Eliminados {} registros.", rows);
    }
}

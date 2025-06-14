package org.iesvdm.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.iesvdm.modelo.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//Anotación lombok para logging (traza) de la aplicación
@Slf4j
@Repository
//Utilizo lombok para generar el constructor
@AllArgsConstructor
public class ComercialDAOImpl implements ComercialDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void create(Comercial comercial) {

		String sqlInsert = """
                INSERT INTO comercial (nombre, apellido1, apellido2, comisión)
                VALUES (?,?,?,?) """;

		KeyHolder keyHolder = new GeneratedKeyHolder();
		int rows = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[]{"id"});
			int idx = 1;
			ps.setString(idx++, comercial.getNombre());
			ps.setString(idx++, comercial.getApellido1());
			ps.setString(idx++, comercial.getApellido2());
			ps.setFloat(idx++, comercial.getComision());
			return ps;
		}, keyHolder);

		comercial.setId(keyHolder.getKey().intValue());

		log.info("Insertados {} registros.", rows);
	}

	@Override
	public List<Comercial> getAll() {

		List<Comercial> listComercial = jdbcTemplate.query(
				"SELECT * FROM comercial",
				(rs, rowNum) -> new Comercial(rs.getInt("id"),
						rs.getString("nombre"),
						rs.getString("apellido1"),
						rs.getString("apellido2"),
						rs.getFloat("comisión"))

		);

		log.info("Devueltos {} registros.", listComercial.size());

		return listComercial;
	}

	@Override
	public Optional<Comercial> find(int id) {

		Comercial comercial = jdbcTemplate
				.queryForObject("SELECT * FROM comercial WHERE id = ?"
						, (rs, rowNum) ->

								new Comercial(rs.getInt("id"),
										rs.getString("nombre"),
										rs.getString("apellido1"),
										rs.getString("apellido2"),
										rs.getFloat("comisión"))
						, id
				);

		if (comercial != null) {
			return Optional.of(comercial);
		} else {
			log.info("Comercial no encontrado.");
			return Optional.empty();
		}
	}

	@Override
	public int getCantidadPedidos(int id_comercial) {
		String sql = "SELECT COUNT(*) from comercial " +
				"JOIN ventas.pedido p ON comercial.id = p.id_comercial " +
				" WHERE comercial.id = ?";


		Integer cantidad = jdbcTemplate.queryForObject(sql, Integer.class, id_comercial);
		return cantidad != null ? cantidad : 0;
	}

	@Override
	public void update(Comercial comercial) {

		int rows = jdbcTemplate.update("""
                        UPDATE comercial SET 
                        				nombre = ?, 
                        				apellido1 = ?, 
                        				apellido2 = ?,
                        				comisión = ? 
                        		WHERE id = ?
                        """
				, comercial.getNombre()
				, comercial.getApellido1()
				, comercial.getApellido2()
				, comercial.getComision()
				, comercial.getId());

		log.info("Update de Comercial con {} registros actualizados.", rows);
	}

	@Override
	public void delete(long id) {

		int rows = jdbcTemplate.update("DELETE FROM comercial WHERE id = ?", id);

		log.info("Delete de Comercial con {} registros eliminados.", rows);
	}

}

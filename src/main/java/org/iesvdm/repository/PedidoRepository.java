package org.iesvdm.repository;

import java.sql.PreparedStatement;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class PedidoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Inserta en base de datos el nuevo Pedido, actualizando el id en el bean Pedido.
     */
    public synchronized void create(Pedido pedido) {
        String sqlInsert = """
                INSERT INTO pedido (id_cliente, id_comercial, fecha, total)
                VALUES (?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rows = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[]{"id"});
            int idx = 1;
            ps.setInt(idx++, pedido.getIdCliente());
            ps.setInt(idx++, pedido.getIdComercial());
            ps.setDate(idx++, Date.valueOf(pedido.getFecha()));
            ps.setFloat(idx, pedido.getTotal());
            return ps;
        }, keyHolder);

        pedido.setId(keyHolder.getKey().intValue());

        log.info("Insertados {} registros.", rows);
    }

    /**
     * Devuelve lista con todos los Pedidos.
     */
    public List<Pedido> getAll() {
        List<Pedido> list = jdbcTemplate.query(
                "SELECT * FROM pedido",
                (rs, rowNum) -> new Pedido(
                        rs.getInt("id"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_comercial"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getFloat("total")
                )
        );

        log.info("Devueltos {} registros.", list.size());
        return list;
    }

    /**
     * Devuelve Optional de Pedido con el ID dado.
     */
    public Optional<Pedido> find(int id) {
        try {
            Pedido pedido = jdbcTemplate.queryForObject(
                    "SELECT * FROM pedido WHERE id = ?",
                    (rs, rowNum) -> new Pedido(
                            rs.getInt("id"),
                            rs.getInt("id_cliente"),
                            rs.getInt("id_comercial"),
                            rs.getDate("fecha").toLocalDate(),
                            rs.getFloat("total")
                    ),
                    id
            );
            return Optional.ofNullable(pedido);
        } catch (Exception e) {
            log.info("Pedido no encontrado.");
            return Optional.empty();
        }
    }

    /**
     * Actualiza Pedido con campos del bean Pedido según ID del mismo.
     */
    public void update(Pedido pedido) {
        int rows = jdbcTemplate.update("""
                UPDATE pedido SET 
                    id_cliente = ?, 
                    id_comercial = ?, 
                    fecha = ?, 
                    total = ? 
                WHERE id = ?
                """,
                pedido.getIdCliente(),
                pedido.getIdComercial(),
                Date.valueOf(pedido.getFecha()),
                pedido.getTotal(),
                pedido.getId()
        );

        log.info("Update de Pedido con {} registros actualizados.", rows);
    }

    /**
     * Borra Pedido con ID proporcionado.
     */
    public void delete(long id) {
        int rows = jdbcTemplate.update("DELETE FROM pedido WHERE id = ?", id);
        log.info("Delete de Pedido con {} registros eliminados.", rows);
    }
}

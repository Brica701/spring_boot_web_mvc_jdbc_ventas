package org.iesvdm.dao;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j // Habilita logging con log.info()
public class PedidoDAOImpl implements PedidoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(Pedido pedido) {
        String sqlInsert = """
                INSERT INTO pedido (total, fecha, id_cliente, id_comercial)
                VALUES (?,?,?,?) """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rows = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[]{"id"});
            ps.setDouble(1, pedido.getTotal());
            ps.setDate(2, new Date(pedido.getFecha().getTime()));
            ps.setInt(3, pedido.getId_cliente());
            ps.setInt(4, pedido.getId_comercial());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            pedido.setId(keyHolder.getKey().intValue());
        }

        log.info("Insertados {} registros.", rows);
    }

    @Override
    public List<Pedido> getAll() {
        List<Pedido> listPedido = jdbcTemplate.query(
                "SELECT * FROM pedido",
                (rs, rowNum) -> new Pedido(
                        rs.getInt("id"),
                        rs.getDouble("total"),
                        rs.getDate("fecha"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_comercial"))
        );

        log.info("Devueltos {} registros.", listPedido.size());
        return listPedido;
    }

    @Override
    public Optional<Pedido> find(int id) {
        List<Pedido> pedidos = jdbcTemplate.query(
                "SELECT * FROM pedido WHERE id = ?",
                (rs, rowNum) -> new Pedido(
                        rs.getInt("id"),
                        rs.getDouble("total"),
                        rs.getDate("fecha"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_comercial")),
                id
        );

        if (pedidos.isEmpty()) {
            log.info("Pedido no encontrado.");
            return Optional.empty();
        } else {
            return Optional.of(pedidos.get(0));
        }
    }

    @Override
    public void update(Pedido pedido) {
        int rows = jdbcTemplate.update("""
                UPDATE pedido SET 
                total = ?, 
                fecha = ?, 
                id_cliente = ?, 
                id_comercial = ?  
                WHERE id = ?
                """,
                pedido.getTotal(),
                new Date(pedido.getFecha().getTime()),
                pedido.getId_cliente(),
                pedido.getId_comercial(),
                pedido.getId()
        );

        log.info("Update de Pedido con {} registros actualizados.", rows);
    }

    @Override
    public void delete(long id) {
        int rows = jdbcTemplate.update("DELETE FROM pedido WHERE id = ?", id);
        log.info("Delete de Pedido con {} registros eliminados.", rows);
    }

    // Implementación del método para obtener pedidos de un comercial
    @Override
    public List<Pedido> findByComercialId(int comercialId) {
        String sql = """
            SELECT * FROM pedido WHERE id_comercial = ?
        """;

        List<Pedido> pedidos = jdbcTemplate.query(sql,
                (rs, rowNum) -> new Pedido(
                        rs.getInt("id"),
                        rs.getDouble("total"),
                        rs.getDate("fecha"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_comercial")
                ),
                comercialId
        );

        log.info("Devueltos {} pedidos del comercial con ID {}.", pedidos.size(), comercialId);
        return pedidos;
    }
}
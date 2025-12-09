package com.mycompany.proyectofinalcompras.modelodao;

import com.mycompany.proyectofinalcompras.Config.Conexion;
import com.mycompany.proyectofinalcompras.modelo.Pedido;
import com.mycompany.proyectofinalcompras.modelo.DetallePedido;
import com.mycompany.proyectofinalcompras.modelo.Producto; 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // --- SENTENCIAS SQL ---
    
    // 1. Insertar pedido
    String SQL_INSERT_PEDIDO = "INSERT INTO pedido (id_cli, fecha_ped, total, estado) VALUES (?, NOW(), ?, 'Pendiente')";

    // 2. Insertar detalle
    String SQL_INSERT_DETALLE = "INSERT INTO detalle_pedido (id_ped, id_prod, cantidad, precio) VALUES (?, ?, ?, ?)";
    
    // 3. Listar mis pedidos
    String SQL_LISTAR = "SELECT * FROM pedido WHERE id_cli = ?";

    // 4. NUEVO (CORREGIDO): Buscar detalle para el Modal
    // CAMBIOS REALIZADOS SEGÚN TU FOTO DE LA BASE DE DATOS:
    // - Tabla: producto
    // - Columnas: nombre, imagen, id_prod
    String SQL_DETALLE = "SELECT d.id_prod, p.nombre, p.imagen, d.precio, d.cantidad " +
                         "FROM detalle_pedido d " +
                         "INNER JOIN producto p ON d.id_prod = p.id_prod " + 
                         "WHERE d.id_ped = ?";

    // -----------------------------------------------------------------------------------
    // MÉTODO 1: GENERAR UN NUEVO PEDIDO
    // -----------------------------------------------------------------------------------
    public int GenerarPedido(Pedido pedido) {
        int idPedidoGenerado = 0;

        try {
            con = new Conexion().getConnection();
            con.setAutoCommit(false); // Iniciar Transacción

            // --- PASO 1: Insertar Encabezado ---
            ps = con.prepareStatement(SQL_INSERT_PEDIDO, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, pedido.getIdCliente());
            ps.setDouble(2, pedido.getTotal());
            ps.executeUpdate();

            // Obtener el ID generado
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idPedidoGenerado = rs.getInt(1);
            }
            rs.close();
            ps.close();

            // --- PASO 2: Insertar Detalles ---
            if (idPedidoGenerado > 0) {
                ps = con.prepareStatement(SQL_INSERT_DETALLE);

                for (DetallePedido detalle : pedido.getDetalles()) {
                    ps.setInt(1, idPedidoGenerado);             
                    ps.setInt(2, detalle.getProducto().getIdProd()); 
                    ps.setInt(3, detalle.getCantidad());         
                    ps.setDouble(4, detalle.getProducto().getPrecio()); 
                    
                    ps.executeUpdate();
                }

                con.commit(); 
            } else {
                con.rollback();
            }

        } catch (SQLException e) {
            System.err.println("Error SQL en GenerarPedido: " + e.getMessage());
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        } finally {
            cerrarConexiones();
        }
        return idPedidoGenerado;
    }

    // -----------------------------------------------------------------------------------
    // MÉTODO 2: LISTAR PEDIDOS DE UN CLIENTE
    // -----------------------------------------------------------------------------------
    public List<Pedido> listarPedidos(int idCliente) {
        List<Pedido> lista = new ArrayList<>();
        try {
            con = new Conexion().getConnection();
            ps = con.prepareStatement(SQL_LISTAR);
            ps.setInt(1, idCliente);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Pedido ped = new Pedido();
                ped.setIdPedido(rs.getInt("id_ped"));
                ped.setIdCliente(rs.getInt("id_cli"));
                ped.setFecha(rs.getString("fecha_ped")); 
                ped.setTotal(rs.getDouble("total"));
                ped.setEstado(rs.getString("estado"));
                
                lista.add(ped);
            }
        } catch (Exception e) {
            System.err.println("Error al listar pedidos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }

    // -----------------------------------------------------------------------------------
    // MÉTODO 3: BUSCAR DETALLE (CORREGIDO PARA TU BASE DE DATOS)
    // -----------------------------------------------------------------------------------
    public List<DetallePedido> buscarDetalle(int idPedido) {
        List<DetallePedido> lista = new ArrayList<>();
        try {
            con = new Conexion().getConnection();
            ps = con.prepareStatement(SQL_DETALLE);
            ps.setInt(1, idPedido);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                DetallePedido detalle = new DetallePedido();
                
                // Objeto Producto temporal para guardar nombre e imagen
                Producto p = new Producto();
                p.setIdProd(rs.getInt("id_prod")); // Columna id_prod
                
                // CORRECCIÓN AQUÍ: Usamos los nombres exactos de tu BD
                p.setNombre(rs.getString("nombre")); // Antes era "Nombres"
                p.setImagen(rs.getString("imagen")); // Antes era "Foto"
                
                p.setPrecio(rs.getDouble("precio"));   
                
                detalle.setProducto(p);
                detalle.setCantidad(rs.getInt("cantidad"));
                
                lista.add(detalle);
            }
        } catch (Exception e) {
            System.err.println("Error al buscar detalle: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }

    // Método auxiliar
    private void cerrarConexiones() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        } catch (SQLException ex) {
            System.err.println("Error Cerrando conexiones: " + ex.getMessage());
        }
    }
}
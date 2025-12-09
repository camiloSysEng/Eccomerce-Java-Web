package com.mycompany.proyectofinalcompras.modelodao;

import com.mycompany.proyectofinalcompras.Config.Conexion; 
import com.mycompany.proyectofinalcompras.modelo.Pedido;
import com.mycompany.proyectofinalcompras.modelo.DetallePedido;
import java.sql.*;
import java.util.List;

public class PedidoDAO {
    
    Connection con = null; 
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    String SQL_INSERT_PEDIDO = "INSERT INTO pedido (IdCliente, Fecha, Total) VALUES (?, NOW(), ?)";
    String SQL_INSERT_DETALLE = "INSERT INTO detalle_pedido (IdPedido, IdProducto, Cantidad, PrecioCompra) VALUES (?, ?, ?, ?)";

    public int GenerarPedido(Pedido pedido) {
        int idPedidoGenerado = 0; 
        
        try { 
            con = new Conexion().getConnection(); 
            con.setAutoCommit(false);
            
            ps = con.prepareStatement(SQL_INSERT_PEDIDO, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, pedido.getIdCliente());
            ps.setDouble(2, pedido.getTotal());
            ps.executeUpdate();
            
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idPedidoGenerado = rs.getInt(1);
            }
            rs.close();
            
            if (idPedidoGenerado > 0) {
                
                for (DetallePedido detalle : pedido.getDetalles()) {
                    ps = con.prepareStatement(SQL_INSERT_DETALLE); 
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
            System.err.println("Error al generar el pedido: " + e.getMessage());
            if (con != null) {
                try {
                    con.rollback(); 
                } catch (SQLException ex) {
                    System.err.println("Error al realizar Rollback: " + ex.getMessage());
                }
            }
        } finally { 
            if (con != null) {
                try {
                    con.setAutoCommit(true); 
                    con.close(); 
                } catch (SQLException ex) {
                    System.err.println("Error al cerrar conexión: " + ex.getMessage());
                }
            }
        }
        return idPedidoGenerado;
    }
}
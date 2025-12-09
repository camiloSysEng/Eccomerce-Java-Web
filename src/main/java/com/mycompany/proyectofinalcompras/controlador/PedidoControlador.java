package com.mycompany.proyectofinalcompras.controlador;

import com.mycompany.proyectofinalcompras.modelo.Cliente;
import com.mycompany.proyectofinalcompras.modelo.Pedido;
import com.mycompany.proyectofinalcompras.modelo.DetallePedido;
import com.mycompany.proyectofinalcompras.modelodao.PedidoDAO;
import com.mycompany.proyectofinalcompras.util.Carrito;
import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "PedidoControlador", urlPatterns = {"/PedidoControlador"})
public class PedidoControlador extends HttpServlet {

    PedidoDAO dao = new PedidoDAO();
    Carrito carritoUtil = new Carrito(); 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        Cliente cliente = (Cliente) session.getAttribute("usuarioLogueado");
        
        ArrayList<DetallePedido> listaCarrito = carritoUtil.ObtenerSesion(request);
        
        if (cliente == null) {
            request.setAttribute("mensajeError", "Debe iniciar sesión.");
            request.getRequestDispatcher("PagLogin.jsp").forward(request, response);
            return;
        }

        if (listaCarrito == null || listaCarrito.isEmpty()) {
            request.setAttribute("mensajeError", "Carrito vacío.");
            request.getRequestDispatcher("PagCarrito.jsp").forward(request, response);
            return;
        }
        
        try {
            Pedido pedido = new Pedido();
            pedido.setIdCliente(cliente.getIdCliente());
            
            double totalCompra = carritoUtil.ImporteTotal(listaCarrito);
            pedido.setTotal(totalCompra); 
            
            pedido.setDetalles(listaCarrito); 
            
            int idPedidoGenerado = dao.GenerarPedido(pedido); 

            if (idPedidoGenerado > 0) {
                session.removeAttribute("carrito");
                request.setAttribute("mensajeExito", "Pedido " + idPedidoGenerado + " procesado.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                request.setAttribute("mensajeError", "No se pudo procesar el pedido.");
                request.getRequestDispatcher("PagCarrito.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensajeError", "Error: " + e.getMessage());
            request.getRequestDispatcher("PagCarrito.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
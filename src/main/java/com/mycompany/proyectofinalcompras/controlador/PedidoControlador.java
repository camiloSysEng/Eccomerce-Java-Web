package com.mycompany.proyectofinalcompras.controlador;

import com.mycompany.proyectofinalcompras.modelo.Cliente;
import com.mycompany.proyectofinalcompras.modelo.Pedido;
import com.mycompany.proyectofinalcompras.modelo.DetallePedido;
import com.mycompany.proyectofinalcompras.modelodao.PedidoDAO;
import com.mycompany.proyectofinalcompras.util.Carrito;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        HttpSession session = request.getSession();
        Cliente cliente = (Cliente) session.getAttribute("usuario"); // Usamos "usuario" como en tu AuthControlador

        // Validación de sesión
        if (cliente == null) {
            request.setAttribute("mensajeError", "Debe iniciar sesión.");
            request.getRequestDispatcher("PagLogin.jsp").forward(request, response);
            return;
        }
        
        // SWITCH PARA IMITAR EL COMPORTAMIENTO DEL VIDEO
        switch (accion) {
            case "generarCompra":
                ArrayList<DetallePedido> listaCarrito = carritoUtil.ObtenerSesion(request);
                if (listaCarrito == null || listaCarrito.isEmpty()) {
                    request.getRequestDispatcher("PagCarrito.jsp").forward(request, response);
                    return;
                }
                
                Pedido pedido = new Pedido();
                pedido.setIdCliente(cliente.getIdCliente());
                pedido.setTotal(carritoUtil.ImporteTotal(listaCarrito));
                pedido.setDetalles(listaCarrito);
                pedido.setEstado("Pendiente"); // Estado por defecto

                int res = dao.GenerarPedido(pedido);

                if (res > 0) {
                    session.removeAttribute("carrito"); // Limpiar carrito
                    // TRUCO DEL VIDEO: Redirigir a la acción "MisPedidos" para ver la tabla
                    response.sendRedirect("PedidoControlador?accion=MisPedidos");
                } else {
                    request.getRequestDispatcher("PagCarrito.jsp").forward(request, response);
                }
                break;

            case "MisPedidos":
                List<Pedido> lista = dao.listarPedidos(cliente.getIdCliente());
                request.setAttribute("misPedidos", lista);
                request.getRequestDispatcher("MisPedidos.jsp").forward(request, response);
                break;
                
            // --- NUEVO CASO AGREGADO PARA EL MODAL ---
            case "Detalle":
                try {
                    int idPedido = Integer.parseInt(request.getParameter("id"));

                    // 1. Buscamos los detalles de ese pedido específico
                    // (Asegúrate de tener este método en tu PedidoDAO)
                    List<DetallePedido> detalles = dao.buscarDetalle(idPedido);
                    
                    request.setAttribute("listaDetalles", detalles);
                    request.setAttribute("montoTotal", request.getParameter("total")); // Pasamos el total para mostrarlo
                    request.setAttribute("numPedido", idPedido); // Para el título del modal

                    // 2. IMPORTANTE: Volvemos a cargar la lista de pedidos de fondo
                    // Esto es necesario para que la tabla detrás del modal no salga vacía
                    List<Pedido> listaPedidos = dao.listarPedidos(cliente.getIdCliente());
                    request.setAttribute("misPedidos", listaPedidos);

                    // 3. Enviamos de vuelta a la misma página
                    request.getRequestDispatcher("MisPedidos.jsp").forward(request, response);
                    
                } catch (NumberFormatException e) {
                    System.out.println("Error al parsear ID de pedido: " + e.getMessage());
                    // Si falla el ID, regresamos a la lista normal
                    response.sendRedirect("PedidoControlador?accion=MisPedidos");
                }
                break;

            default:
                request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
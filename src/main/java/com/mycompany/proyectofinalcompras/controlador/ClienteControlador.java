package com.mycompany.proyectofinalcompras.controlador;

import com.mycompany.proyectofinalcompras.modelo.Cliente;
import com.mycompany.proyectofinalcompras.modelodao.clienteDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ClienteControlador extends HttpServlet {

    private final String pagNuevo = "pagRegistrarCliente.jsp";
    private clienteDAO cliDao = new clienteDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion");

        switch (accion) {
            case "nuevo":
                Nuevo(request, response);
                break;
            case "guardar":
                Guardar(request, response);
                break;
        }

    }

    protected void Nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("cliente", new Cliente());
        request.getRequestDispatcher(pagNuevo).forward(request, response);

    }

    protected void Guardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Cliente obj = new Cliente();
        obj.setNombres(request.getParameter("nombres"));
        obj.setApellidos(request.getParameter("apellidos"));
        obj.setTelefono(request.getParameter("telefono"));
        obj.setCorreo(request.getParameter("correo"));
        obj.setPassword(request.getParameter("password"));

        if (cliDao.ExisteCorreo(obj.getCorreo().trim()) == false) {
            int result = cliDao.Guardar(obj);

            if (result > 0) {
                request.getSession().setAttribute("sucess", "Cuenta registrada");
                response.sendRedirect("ClienteControlador?accion=nuevo");
                return;
            } else {
                request.getSession().setAttribute("error", "No se pudo registrar cuenta");
            }
        }else{
           request.getSession().setAttribute("error", "El correo"+obj.getCorreo()+" "
           +" ya se encuentra registrado!");
        }

        request.setAttribute("cliente", new Cliente());
        request.getRequestDispatcher(pagNuevo).forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

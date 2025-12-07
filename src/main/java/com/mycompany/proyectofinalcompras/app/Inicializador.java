
package com.mycompany.proyectofinalcompras.app;

import com.mycompany.proyectofinalcompras.modelodao.ProductoDAO;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;


@WebListener
public class Inicializador implements ServletContextListener{
    
    private ProductoDAO prodDAO = new ProductoDAO();
    
    @Override
    public void contextInitialized(ServletContextEvent sce){
        sce.getServletContext().setAttribute("productos", prodDAO.ListarTodos());
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce){
    
    }
}

    
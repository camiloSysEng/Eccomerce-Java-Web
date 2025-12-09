<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mis Pedidos</title>
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
        
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        
        <link href="imagenes/css/estilos.css" rel="stylesheet" type="text/css"/>
        
        <style>
            .modal-header { background-color: #1a2537; color: white; }
            .table th, .table td { vertical-align: middle; }
            .btn-info { color: white; font-weight: bold; border: none; }
            .navbar-brand img { max-height: 40px; }
        </style>
    </head>
    <body>

        <nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #808080;"> 
            <div class="container-fluid">
                
                <a class="navbar-brand" href="index.jsp">
                    <img src="imagenes/img/recursos/logo.png" alt="Store Logo">
                </a>
                
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent">
                    <span class="navbar-toggler-icon"></span>
                </button>
                
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link text-white" href="index.jsp">
                                <i class="fas fa-home"></i> Catalogo
                            </a>
                        </li>
                        
                        <li class="nav-item">
                            <a class="nav-link text-white" href="CarritoControlador?accion=listar">
                                <i class="fas fa-shopping-cart"></i> (${contador != null ? contador : 0}) Carrito
                            </a>
                        </li>
                        
                        <li class="nav-item">
                            <a class="nav-link active fw-bold" href="PedidoControlador?accion=MisPedidos">
                                <i class="fas fa-clipboard-list"></i> Mis Pedidos
                            </a>
                        </li>
                    </ul>
                    
                    <div class="d-flex align-items-center">
                        <a href="#" class="btn btn-light me-2 fw-bold text-uppercase" style="color: black;">
                             ${usuario.nombres}
                        </a>
                        <a href="AuthControlador?accion=Salir" class="btn btn-dark border-secondary">
                            <i class="fas fa-sign-out-alt"></i> Cerrar Sesión
                        </a>
                    </div>
                </div>
            </div>
        </nav>

        <div class="container mt-4">
            <h2 style="color: white; text-shadow: 2px 2px 4px black;">Mis Pedidos</h2>
            
            <div class="card" style="background-color: rgba(0, 0, 0, 0.65); border: none;">
                <div class="card-body">
                    <table class="table table-hover table-dark">
                        <thead>
                            <tr>
                                <th># Pedido</th>
                                <th>Fecha</th>
                                <th>Total</th>
                                <th>Estado</th>
                                <th>Detalle</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="p" items="${misPedidos}">
                                <tr>
                                    <td>${p.idPedido}</td>
                                    <td>${p.fecha}</td>
                                    <td>S/. ${p.total}</td>
                                    <td>${p.estado}</td>
                                    <td>
                                        <a href="PedidoControlador?accion=Detalle&id=${p.idPedido}&total=${p.total}" class="btn btn-info btn-sm">Ver</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="modal fade" id="myModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg"> 
                <div class="modal-content" style="color: black;">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">::: Pedido #${numPedido} :::</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <table class="table">
                            <thead class="table-dark">
                                <tr>
                                    <th>Imagen</th>
                                    <th>Producto</th>
                                    <th>Precio (S/)</th>
                                    <th>Cantidad</th>
                                    <th>Importe (S/)</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="d" items="${listaDetalles}">
                                    <tr>
                                        <td>
                                            <img src="imagenes/img/productos/${d.producto.imagen}" height="50" width="50" style="object-fit: contain;">
                                        </td> 
                                        <td>${d.producto.nombre}</td>
                                        <td>${d.producto.precio}</td>
                                        <td>${d.cantidad}</td>
                                        <td>${d.producto.precio * d.cantidad}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div class="text-end">
                            <h4>Total Pagado: S/. ${montoTotal}</h4>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

        <c:if test="${not empty listaDetalles}">
            <script>
                $(document).ready(function() {
                    var myModal = new bootstrap.Modal(document.getElementById('myModal'));
                    myModal.show();
                });
            </script>
        </c:if>

    </body>
</html>
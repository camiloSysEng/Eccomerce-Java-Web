<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Catalogo</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href="imagenes/css/estilos.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <jsp:include page="componentes/Navegacion.jsp"/>

        <div class="container-fluid mt-2" >
            
            <c:if test="${not empty mensajeError}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${mensajeError}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <c:if test="${not empty mensajeExito}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${mensajeExito}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <h5>Mi Carrito</h5>
            <hr />

            <div class="row">
                <div class="col-sm-9">
                    <div class="card">
                        <div class="card-body"> 
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th>Imagen</th>
                                        <th>Producto</th>
                                        <th>Precio</th>
                                        <th>Cantidad</th>
                                        <th>Importe</th>
                                        <th>Acción</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${carrito}" var="item" varStatus="loop">
                                        <tr>
                                            <td>
                                                <img src="imagenes/img/productos/${item.producto.imagen}" width="50" height="60" alt="${item.producto.nombre}"/>
                                            </td>
                                            <td>${item.producto.nombre}</td>
                                            <td>cop <fmt:formatNumber value="${item.producto.precio}" type="number" maxFractionDigits="0"/></td>
                                            <td>${item.cantidad}</td>
                                            <td>cop <fmt:formatNumber value="${item.Importe()}" type="number" maxFractionDigits="0" /> </td>
                                            <td>
                                                <a href="CarritoControlador?accion=eliminar&indice=${loop.index}" title="Eliminar" class="btn btn-danger btn-sm">
                                                    <i class="fa fa-trash-alt"></i>
                                                </a>
                                            </td>
                                        </tr>   
                                    </c:forEach>
                                    <c:if test="${!(carrito != null && carrito.size()>0)}">
                                        <tr class="text-center">
                                            <td colspan="6">Carrito vacio</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="card">
                        <div class="card-body">
                            <div class="row">
                                <h5>RESUMEN COMPRA</h5>
                                <hr />
                                <div class="d-flex justify-content-between mb-4">
                                    <p class="mb-2">Total</p>
                                    <p class="mb-2">cop <fmt:formatNumber value="${total}" type="number" maxFractionDigits="0" /></p>
                                </div>

                                <c:choose>
                                    <%-- CASO 1: USUARIO LOGUEADO --%>
                                    <c:when test="${not empty sessionScope.usuarioLogueado}">
                                        <a href="PedidoControlador" class="btn btn-success btn-block btn-lg w-100">
                                            <div class="d-flex justify-content-between justify-content-center">
                                                <span><i class="fa fa-credit-card me-2"></i>GENERAR COMPRA</span>
                                            </div>
                                        </a>
                                    </c:when>

                                    <%-- CASO 2: NO LOGUEADO --%>
                                    <c:otherwise>
                                        <a href="PagLogin.jsp" class="btn btn-warning btn-block btn-lg w-100">
                                            <div class="d-flex justify-content-between justify-content-center">
                                                <span>INICIAR SESIÓN</span>
                                            </div>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                                </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.min.js" integrity="sha384-G/EV+4j2dNv+tEPo3++6LCgdCROaejBqfUeNjuKAiuXbjrxilcCdDz6ZAVfHWe1Y" crossorigin="anonymous"></script>
    </body>
</html>
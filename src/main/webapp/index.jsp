<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Catalogo</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    </head>
    <body>
        <jsp:include page="componentes/Navegacion.jsp"/>


        <div class="container-fluid mt-2" >
            <h5>Catalogo de productos</h5>
            <hr />

            <div class="row">
                <c:forEach items="${productos}" var="item">
                    <div class="col-sm-3 mt-1">
                        <form action="CarritoControlador" method="get">
                            <div class="card">
                                <img src="imagenes/img/productos/${item.imagen}" width="100%" alt="${item.nombre}"/>
                                <div class="card-body">
                                    <p class="fw-bold">${item.nombre}</p>
                                    <input type="hidden" name="accion" value="agregar">
                                    <input type="hidden" name="id" value="${item.idProd}">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <button type="submit" class="btn-sm btn-primary">
                                            <i class="fa fa-shopping-cart"></i> Agregar al carrito
                                        </button>
                                        <small class="fw-bold">${item.precio} </small>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </c:forEach>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.min.js" integrity="sha384-G/EV+4j2dNv+tEPo3++6LCgdCROaejBqfUeNjuKAiuXbjrxilcCdDz6ZAVfHWe1Y" crossorigin="anonymous"></script>
    </body>
</html>

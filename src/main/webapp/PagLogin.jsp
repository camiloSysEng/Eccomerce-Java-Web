<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href="imagenes/css/estilos.css" rel="stylesheet" type="text/css"/>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    </head>
    <body>
        <jsp:include page="componentes/Navegacion.jsp"/>
        <jsp:include page ="componentes/Mensaje.jsp"/>
        <div class="container-fluid mt-3" >
            <div class="row">
                <div class="col-sm-12">
                    <div class="card form-registro">
                        <div class="card-body">
                            <h5>Login</h5>
                            <hr />
                            <form action="AuthControlador" method="post">
                                <div class="mb-3 text-center">
                                    <img src="imagenes/img/recursos/login.jpg" 
                                         style="width: 200px;" alt="Login"/>

                                </div>

                                <div class ="row">
                                    <div class ="col-sm-12">
                                        <div class ="mb-3">
                                            <label>Correo Electronico: <span class="obligatorio">(*)</span> </label>
                                            <input type="email" class="form-control" 
                                                   name = "correo" placeholder ="Ingrese Su Correo Electronico" required=""/>
                                        </div>       
                                    </div>                                                           
                                </div>

                                <div class ="row">
                                    <div class ="col-sm-12">
                                        <div class ="mb-3">
                                            <label>Contraseña : <span class="obligatorio">(*)</span> </label>
                                            <input type="password" class="form-control" required=""
                                                   name = "password" placeholder ="Ingrese Su Contraseña"/>
                                        </div>       
                                    </div>                                                           
                                </div>

                                <input type="hidden" name="accion" value="autenticarse" />
                                <button type = "submit" class="btn btn-primary">Iniciar Sesion</button>
                            </form>
                        </div>
                    </div>
                </div>

                <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.min.js" integrity="sha384-G/EV+4j2dNv+tEPo3++6LCgdCROaejBqfUeNjuKAiuXbjrxilcCdDz6ZAVfHWe1Y" crossorigin="anonymous"></script>
                </body>
                </html>

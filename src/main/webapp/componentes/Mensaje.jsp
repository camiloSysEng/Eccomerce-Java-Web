

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${sessionScope.sucess != null}">
    <script>
    Swal.fire({
        title: "!Exito!",
        text: "${sessionScope.sucess}",
        icon: "sucess"
        });
    </script>   
</c:if> 

<c:if test="${sessionScope.error != null}">
    <script>
    Swal.fire({
        title: "!Advertencia!",
        text: "${sessionScope.error}",
        icon: "error"
        });
    </script>   
</c:if> 
<c:remove var ="sucess" scope="session" />
<c:remove var ="error" scope="session" />
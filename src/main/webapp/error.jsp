<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <jsp:include page="style.jsp"/>
    <title>Ошибка</title>
</head>
<body>


<div class="container">

    <jsp:include page="header.jsp"/>

    <div class="row">
        <div class="col-8"><h3>Ошибка. ${requestScope.message}</h3></div>
        <div class="col-4">

        </div>
    </div>
</div>

</body>
</html>
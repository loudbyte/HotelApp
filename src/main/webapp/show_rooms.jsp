<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title>Rooms</title>
    <style>
        .wrapper {
            width: 20em;
            height: 20em;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .wrapper img {
            max-width: 100%;
            max-height: 100%;
            object-fit: contain;
            border: calc(1em / 12) solid black;
            border-radius: calc(11em / 12);
        }
    </style>
</head>
<body>
<div class="container marketing">
        <jsp:include page="header.jsp"/>
        <div class="row">
            <div class="col-lg-4">
                <div class="wrapper"><img src="${requestScope.image_url_standard}" class="rounded-circle" alt="" width="300" height="300"></div>
                <h2>Стандарт</h2>
                <p>Стандартные комнаты по доступной цене!</p>
                <p><a class="btn btn-secondary" href="${pageContext.request.contextPath}/controller/show_rooms_standard" role="button">Подробнее... &raquo;</a></p>
            </div>
            <div class="col-lg-4">
                <div class="wrapper"><img src="${requestScope.image_url_suite}" class="rounded-circle" alt="" width="300" height="300"></div>
                <h2>Люкс</h2>
                <p>Комнаты люкс для тех, кто любит комфорт!</p>
                <p><a class="btn btn-secondary" href="${pageContext.request.contextPath}/controller/show_rooms_suite" role="button">Подробнее... &raquo;</a></p>
            </div>
            <div class="col-lg-4">
                <div class="wrapper"><img src="${requestScope.image_url_delux}" class="rounded-circle" alt="" width="300" height="300"></div>
                <h2>Делюкс</h2>
                <p>Комнаты класса делюкс - элитнейшие номера!</p>
                <p><a class="btn btn-secondary" href="${pageContext.request.contextPath}/controller/show_rooms_delux" role="button">Подробнее... &raquo;</a></p>
            </div>
        </div>
</div>
</body>
</html>

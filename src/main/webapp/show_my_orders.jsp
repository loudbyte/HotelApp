<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>My orders</title>
    <jsp:include page="style.jsp"/>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <div class="row">
        <div class="col-9">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Номер заказа</th>
                    <th scope="col">Дата заказа</td>
                    <th scope="col">Состояние</td>
                    <th scope="col"></td>
                    <th scope="col"></td>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="myOrder" items="${requestScope.my_orders}">
                    <c:if test="${myOrder.statusId != 4}">
                    <tr>
                        <td>${myOrder.id}</td>
                        <td>${myOrder.date}</td>
                        <td>${myOrder.statusRu}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller/show_order_detail" method="post">
                                <input type="hidden" name="order_id" value="${myOrder.id}">
                                <button type="submit"
                                        class="btn btn-warning">Детали</button>
                            </form>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller/cancel_order" method="post">
                                <input type="hidden" name="order_id" value="${myOrder.id}">
                                <button type="submit"
                                        class="btn btn-danger">Отменить</button>
                            </form>
                        </td>
                    </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col">
            <p>
            <a href="${pageContext.request.contextPath}/controller/cabinet" type="button" class="btn btn-secondary">Кабинет</a>
            </p>
        </div>
    </div>
</div>
</body>
</html>

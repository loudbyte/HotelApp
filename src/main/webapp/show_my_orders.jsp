<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="orderDAO" class="com.epam.hotel.dao.impl.OrderMainDAOImpl"/>
<c:set value="${orderDAO.getAllByPersonId(sessionScope.person.id)}" var="myOrderList"/>
<jsp:useBean id="calculatePrice" class="com.epam.hotel.businesslogic.CalculatePrice"/>
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
                    <th scope="col">Цена</td>
                    <th scope="col"></td>
                    <th scope="col"></td>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="myOrder" items="${myOrderList}">
                    <c:if test="${myOrder.statusId != 4}">
                    <tr>
                        <td>${myOrder.id}</td>
                        <td>${myOrder.date}</td>
                        <td>${myOrder.statusRu}</td>
                        <td>${calculatePrice.calculateOrderMain(myOrder.id)}$</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller/show_order_room_detail" method="post">
                                <input type="hidden" name="order_main_id" value="${myOrder.id}">
                                <button type="submit"
                                        class="btn btn-warning">Детали</button>
                            </form>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller/cancel_order" method="post">
                                <input type="hidden" name="order_main_id" value="${myOrder.id}">
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

        </div>
    </div>
</div>
</body>
</html>

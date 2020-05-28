<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="orderDAO" class="com.epam.hotel.dao.impl.OrderMainDAOImpl"/>
<jsp:useBean id="orderRoomDetailDAO" class="com.epam.hotel.dao.impl.OrderRoomDetailDAOImpl"/>
<c:set var="orderList" value="${orderDAO.all}"/>
<jsp:useBean id="personDAO" class="com.epam.hotel.dao.impl.PersonDAOImpl"/>
<c:set var="personList" value="${personDAO.all}"/>
<jsp:useBean id="calculatePrice" class="com.epam.hotel.businesslogic.CalculatePrice"/>
<html>
<head>
    <title>All orders</title>
    <jsp:include page="style.jsp"/>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <div class="row">
        <div class="col-9">
            <table class="table table-sm">
                <thead>
                <tr>
                    <th scope="col">Номер заказа</th>
                    <th scope="col">Имя заказчика</th>
                    <th scope="col">ИИН заказчика</th>
                    <th scope="col">Дата заказа</th>
                    <th scope="col">Состояние</th>
                    <th scope="col">Цена</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:set var="person_list" value="${personList}"/>
                <c:forEach var="order" items="${orderList}">
                        <tr>
                            <td>${order.id}</td>
                            <td>
                                <c:forEach var="person" items="${personList}">
                                    <c:if test="${person.id == order.personId}">
                                        ${person.firstName}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach var="person" items="${personList}">
                                    <c:if test="${person.id == order.personId}">
                                        ${person.iin}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>${order.date}</td>
                            <td>${order.statusRu}</td>
                            <td>${calculatePrice.calculateOrderMain(order.id)}$</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/controller/show_order_room_detail" method="post">
                                    <input type="hidden" name="order_main_id" value="${order.id}">
                                    <button type="submit"
                                            class="btn btn-sm  btn-warning">Детали</button>
                                </form>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/controller/edit_order_button" method="post">
                                      <input type="hidden" name="order_main_id" value="${order.id}">
                                      <input type="hidden" name="person_id" value="${order.personId}">
                                      <input type="hidden" name="status_id" value="${order.statusId}">
                                      <input type="hidden" name="date" value="${order.date}">
                                      <button type="submit"
                                              class="btn btn-sm btn-warning">Редактировать</button>
                                </form>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/controller/delete_order" method="post">
                                    <input type="hidden" name="order_main_id" value="${order.id}">
                                    <button type="submit"
                                            class="btn btn-sm btn-danger">Удалить</button>
                                </form>
                            </td>
                        </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col">
            <c:if test="${sessionScope.role == 'ADMIN'}">
                <jsp:include page="admin_right_panel.jsp" />
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
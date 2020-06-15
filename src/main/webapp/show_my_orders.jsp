<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="orderDAO" class="com.epam.hotel.dao.impl.OrderMainDAOImpl"/>
<c:set value="${orderDAO.getAllByPersonId(sessionScope.person.id)}" var="myOrderList"/>
<jsp:useBean id="calculatePrice" class="com.epam.hotel.businesslogic.CalculatePrice"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="language"/>
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
                    <th scope="col"><fmt:message key="order.number"/></th>
                    <th scope="col"><fmt:message key="date"/></th>
                    <th scope="col"><fmt:message key="status"/></th>
                    <th scope="col"><fmt:message key="price"/></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="myOrder" items="${myOrderList}">
                    <c:if test="${myOrder.statusId != 4}">
                    <tr>
                        <td>${myOrder.id}</td>
                        <td>${myOrder.date}</td>
                        <td>
                            <c:choose>
                                <c:when test="${'ru'.equals(sessionScope.local)}">${myOrder.statusRu}</c:when>
                                <c:otherwise>${myOrder.statusEn}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>${calculatePrice.calculateOrderMain(myOrder.id)}$</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller/show_order_room_detail" method="post">
                                <input type="hidden" name="order_main_id" value="${myOrder.id}">
                                <button type="submit"
                                        class="btn btn-warning"><fmt:message key="details"/></button>
                            </form>
                        </td>
                        <td>
                        <c:choose>
                            <c:when test="${myOrder.statusId != 6}">
                                <form action="${pageContext.request.contextPath}/controller/pay_order" method="post">
                                    <input type="hidden" name="order_main_id" value="${myOrder.id}">
                                    <button type="submit"
                                            class="btn btn btn-warning"><fmt:message key="pay"/></button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <button title='<fmt:message key="paid"/>' type="button" class="btn btn btn-dark"><fmt:message key="pay"/></button>
                            </c:otherwise>
                        </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${myOrder.statusId != 6}">
                                    <form action="${pageContext.request.contextPath}/controller/cancel_order" method="post">
                                        <input type="hidden" name="order_main_id" value="${myOrder.id}">
                                        <button type="submit"
                                                class="btn btn-danger"><fmt:message key="cancel"/></button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <button title='<fmt:message key="paid"/>' type="button" class="btn btn btn-dark"><fmt:message key="cancel"/></button>
                                </c:otherwise>
                            </c:choose>
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
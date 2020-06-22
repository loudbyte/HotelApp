<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="orderDAO" class="com.epam.hotel.dao.impl.OrderMainDAOImpl"/>
<c:set value="${orderDAO.getAllByPersonId(sessionScope.person.id)}" var="orderList"/>
<jsp:useBean id="calculatePrice" class="com.epam.hotel.businesslogic.CalculatePrice"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="orders"/></title>
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
                    <th scope="col"><fmt:message key="order_number"/></th>
                    <th scope="col"><fmt:message key="date"/></th>
                    <th scope="col"><fmt:message key="status"/></th>
                    <th scope="col"><fmt:message key="price"/></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="order" items="${orderList}">
                    <c:if test="${order.status != 4}">
                    <tr>
                        <td>${order.id}</td>
                        <td>${order.date}</td>
                        <td>
                            <c:forEach var="counter" begin="1" end="6">
                                <c:if test="${order.status == counter}"><fmt:message key="status_${counter}"/></c:if>
                            </c:forEach>
                        </td>
                        <td>${calculatePrice.calculateOrderMain(order.id)}$</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller/show_order_room_detail" method="post">
                                <input type="hidden" name="order_main_id" value="${order.id}">
                                <button type="submit"
                                        class="btn btn-warning"><fmt:message key="details"/></button>
                            </form>
                        </td>
                        <td>
                        <c:choose>
                            <c:when test="${order.status != 6}">
                                <form action="${pageContext.request.contextPath}/controller/pay_order" method="post">
                                    <input type="hidden" name="order_main_id" value="${order.id}">
                                    <button type="submit"
                                            class="btn btn btn-warning"><fmt:message key="pay"/></button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <button title='<fmt:message key="payment_complete"/>' type="button" class="btn btn btn-dark"><fmt:message key="pay"/></button>
                            </c:otherwise>
                        </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${order.status != 6}">
                                    <form action="${pageContext.request.contextPath}/controller/cancel_order" method="post">
                                        <input type="hidden" name="order_main_id" value="${order.id}">
                                        <button type="submit"
                                                class="btn btn-danger"><fmt:message key="cancel"/></button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <button title='<fmt:message key="payment_complete"/>' type="button" class="btn btn btn-dark"><fmt:message key="cancel"/></button>
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
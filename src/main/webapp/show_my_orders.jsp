<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="orderDAO" class="com.epam.hotel.dao.impl.OrderMainDAOImpl"/>
<c:set value="${orderDAO.getAllByPersonId(sessionScope.person.id)}" var="orderList"/>
<jsp:useBean id="calculatePrice" class="com.epam.hotel.payment.CalculatePrice"/>
<jsp:useBean id="orderStatusDAO" class="com.epam.hotel.dao.impl.OrderStatusDAOImpl"/>
<jsp:useBean id="languageDAO" class="com.epam.hotel.dao.impl.LanguageDAOImpl"/>

<c:set var="languageMap" value="${languageDAO.languageMap}"/>
<c:set var="orderStatusList" value="${orderStatusDAO.all}"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="title.orders"/></title>
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
                    <th scope="col"><fmt:message key="page.order_number"/></th>
                    <th scope="col"><fmt:message key="page.date"/></th>
                    <th scope="col"><fmt:message key="page.status"/></th>
                    <th scope="col"><fmt:message key="page.price"/></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="order" items="${orderList}">
                    <c:if test="${order.orderStatusId != 4}">
                    <tr>
                        <td>${order.id}</td>
                        <td>${order.date}</td>
                        <td>
                            <c:forEach var="orderStatus" items="${orderStatusList}">
                                <c:if test="${orderStatus.id == order.orderStatusId}">
                                    <c:forEach items="${languageMap}" var="language">
                                        <c:if test="${language.value.equals(sessionScope.locale)}">
                                            ${orderStatus.orderStatusNameMap.get(language.key)}
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>${calculatePrice.calculateOrderMain(order.id)}$</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller/show_order_room_detail" method="post">
                                <input type="hidden" name="order_main_id" value="${order.id}">
                                <button type="submit"
                                        class="btn btn-warning"><fmt:message key="page.details"/></button>
                            </form>
                        </td>
                        <td>
                        <c:choose>
                            <c:when test="${order.orderStatusId != 6}">
                                <form action="${pageContext.request.contextPath}/pay_order.jsp" method="post">
                                    <input type="hidden" name="order_main_id" value="${order.id}">
                                    <button type="submit"
                                            class="btn btn btn-warning"><fmt:message key="page.pay"/></button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <button title='<fmt:message key="page.payment_complete"/>' type="button" class="btn btn btn-dark"><fmt:message key="page.pay"/></button>
                            </c:otherwise>
                        </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${order.orderStatusId != 6}">
                                    <form action="${pageContext.request.contextPath}/controller/cancel_order" method="post">
                                        <input type="hidden" name="order_main_id" value="${order.id}">
                                        <button type="submit"
                                                class="btn btn-danger"><fmt:message key="page.cancel"/></button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <button title='<fmt:message key="page.payment_complete"/>' type="button" class="btn btn btn-dark"><fmt:message key="page.cancel"/></button>
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
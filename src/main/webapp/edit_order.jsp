<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="orderStatusDAO" class="com.epam.hotel.dao.impl.OrderStatusDAOImpl"/>
<jsp:useBean id="languageDAO" class="com.epam.hotel.dao.impl.LanguageDAOImpl"/>

<c:set var="languageMap" value="${languageDAO.languageMap}"/>
<c:set var="orderStatusList" value="${orderStatusDAO.all}"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="title.edit"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <h3><fmt:message key="page.edit"/> <fmt:message key="page.order_number"/> - ${param.order_main_id}</h3>
    <div class="row">
        <div class="col-sm">
            <form action="${pageContext.request.contextPath}/controller/edit_order" method="post">
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="person_id"><fmt:message key="page.client"/> ID</label>
                        <input type="text" class="form-control" id="person_id" name="person_id" value="${param.person_id}">
                    </div>

                    <div class="form-group col-md-5">
                        <label for="date"><fmt:message key="page.date"/></label>
                        <input type="text" class="form-control" id="date" name="date" value="${param.date}">
                    </div>
                </div>
                <div class="form-group col-md-5">
                    <h5><fmt:message key="page.status"/>:</h5>
                    <ul>
                        <c:forEach var="orderStatus" items="${orderStatusList}">
                            <li>
                                <input type="radio" name="status" value="${orderStatus.id}" id="${orderStatus.id}" <c:if test="${param.status == orderStatus.id}">checked</c:if> />
                                <label for="${orderStatus.id}">
                                    <strong>
                                        <c:forEach items="${languageMap}" var="language">
                                            <c:if test="${language.value.equals(sessionScope.locale)}">
                                                ${orderStatus.orderStatusNameMap.get(language.key)}
                                            </c:if>
                                        </c:forEach>
                                    </strong>
                                </label>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <br/>
                <input type="hidden" name="order_main_id" value="${param.order_main_id}" >
                <div class="form-row">
                    <button type="submit" style="width: 100px" class="btn btn-primary"><fmt:message key="page.save"/></button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
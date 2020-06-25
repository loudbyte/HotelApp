<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="languageDAO" class="com.epam.hotel.dao.impl.LanguageDAOImpl"/>
<c:set var="languageMap" value="${languageDAO.languageMap}"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="title.new_order_status"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <h3><fmt:message key="page.new_order_status"/></h3>
    <div class="row">
        <div class="col-sm">
            <form action="${pageContext.request.contextPath}/controller/create_order_status" method="post">
                <c:forEach var="language" items="${languageDAO.languageMap}">
                    <div class="form-row">
                        <div class="form-group col-md-5">
                            <label for="order_status_name"><fmt:message key="page.name"/> ${language.value}</label>
                            <input type="text" class="form-control" id="order_status_name" name="order_status_name${language.key}"/>
                        </div>
                    </div>
                </c:forEach>
                <br/>
                <div class="form-row">
                    <button type="submit" style="width: 100px" class="btn btn-primary"><fmt:message key="page.save"/></button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="title.pay"/></title>
</head>
<body>

<div class="container">

    <jsp:include page="header.jsp"/>

    <div class="row">
        <div class="col-9"><h1><fmt:message key="page.payment_complete"/></h1></div>
        <div class="col">
            <p>
            <div class="btn-group" role="group" aria-label="Basic example">
                <a href="${pageContext.request.contextPath}/controller/show_rooms" type="button" class="btn btn-secondary"><fmt:message key="page.show_rooms"/></a>
            </div>
            </p>
        </div>
    </div>
</div>

</body>
</html>
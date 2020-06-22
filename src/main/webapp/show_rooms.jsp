<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="show_rooms"/></title>
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
                <h2><fmt:message key="standard"/></h2>
                <p><fmt:message key="standard.desc"/></p>
                <p><a class="btn btn-secondary" href="${pageContext.request.contextPath}/controller/show_rooms_standard" role="button"><fmt:message key="show_rooms"/> &raquo;</a></p>
            </div>
            <div class="col-lg-4">
                <div class="wrapper"><img src="${requestScope.image_url_suite}" class="rounded-circle" alt="" width="300" height="300"></div>
                <h2><fmt:message key="suite"/></h2>
                <p><fmt:message key="suite.desc"/></p>
                <p><a class="btn btn-secondary" href="${pageContext.request.contextPath}/controller/show_rooms_suite" role="button"><fmt:message key="show_rooms"/> &raquo;</a></p>
            </div>
            <div class="col-lg-4">
                <div class="wrapper"><img src="${requestScope.image_url_deluxe}" class="rounded-circle" alt="" width="300" height="300"></div>
                <h2><fmt:message key="deluxe"/></h2>
                <p><fmt:message key="deluxe.desc"/></p>
                <p><a class="btn btn-secondary" href="${pageContext.request.contextPath}/controller/show_rooms_deluxe" role="button"><fmt:message key="show_rooms"/> &raquo;</a></p>
            </div>
        </div>
</div>
</body>
</html>
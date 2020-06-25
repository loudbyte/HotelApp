<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html lang="en">
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message  key="title.error"/></title>
</head>
<body>


<div class="container">

    <jsp:include page="header.jsp"/>

    <div class="row">
        <div class="col-8"><h3><fmt:message key="page.error"/>. <fmt:message key="${sessionScope.message}"/></h3></div>
        <div class="col-4">

        </div>
    </div>
</div>

</body>
</html>
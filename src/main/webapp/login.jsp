<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html lang="en">
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="title.login"/></title>
</head>
<body>
<div class="container">
<jsp:include page="header.jsp"/>
    <div class="row">
        <div class="col-sm">
            <form action="${pageContext.request.contextPath}/controller/login" method="post">
                <div class="form-group">
                    <label for="email"><fmt:message key="page.email"/></label>
                    <input type="email" class="form-control" id="email" aria-describedby="emailHelp" name="email">
                </div>
                <div class="form-group">
                    <label for="password"><fmt:message key="page.password"/></label>
                    <input type="password" class="form-control" id="password" name="password">
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary"><fmt:message key="page.login"/></button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>

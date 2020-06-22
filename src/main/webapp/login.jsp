<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html lang="en">
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="login"/></title>
</head>
<body>
<div class="container">
<jsp:include page="header.jsp"/>
    <div class="row">
        <div class="col-sm">
            <form action="${pageContext.request.contextPath}/controller/login" method="post">
                <div class="form-group">
                    <label for="exampleInputEmail1"><fmt:message key="email"/></label>
                    <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" name="email">
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword1"><fmt:message key="password"/></label>
                    <input type="password" class="form-control" id="exampleInputPassword1" name="password">
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary"><fmt:message key="login"/></button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>

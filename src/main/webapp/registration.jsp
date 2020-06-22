<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="registration"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
        <div class="row">
            <div class="col-sm">
                <form action="${pageContext.request.contextPath}/controller/registration_person" method="post">
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="inputFirstName"><fmt:message key="first_name"/></label>
                            <input type="text" class="form-control" id="inputFirstName" name="first_name">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="inputLastName"><fmt:message key="last_name"/></label>
                            <input type="text" class="form-control" id="inputLastName" name="last_name">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="inputBirthday"><fmt:message key="birthday"/></label>
                            <input type="date" class="form-control" id="inputBirthday" placeholder="" name="birthday">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="inputPhone"><fmt:message key="phone"/></label>
                            <input type="text" class="form-control" id="inputPhone" placeholder="+77001234567" name="phone">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="inputEmail"><fmt:message key="email"/></label>
                            <input type="text" class="form-control" id="inputEmail" placeholder="example@gmail.com" name="email">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="inputPassword"><fmt:message key="password"/></label>
                            <input type="password" class="form-control" id="inputPassword" placeholder="" name="password">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="inputIin"><fmt:message key="iin"/></label>
                            <input type="text" class="form-control" id="inputIin" name="iin">
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary"><fmt:message key="registration"/></button>
                </form>
            </div>
        </div>
</div>
</body>
</html>
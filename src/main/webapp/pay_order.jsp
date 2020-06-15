<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="pay"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <div class="row">
        <div class="col-sm">
            <h2><fmt:message key="user.info"/>:</h2>
            <table class="table"  style="width: auto">
                <tr>
                    <td class="lead"><fmt:message key="first.name"/>:</td>
                    <td class="lead">${sessionScope.person.firstName}</td>
                </tr>
                <tr>
                    <td class="lead"><fmt:message key="last.name"/>:</td>
                    <td class="lead">${sessionScope.person.lastName}</td>
                </tr>
                <tr>
                    <td class="lead"><fmt:message key="iin"/>:</td>
                    <td class="lead">${sessionScope.person.iin}</td>
                </tr>
                <tr>
                    <td class="lead"><fmt:message key="birthday"/>:</td>
                    <td class="lead">${sessionScope.person.birthday}</td>
                </tr>

            </table>
            <form action="${pageContext.request.contextPath}/controller/payment" method="post">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="cardNumber"><fmt:message key="input.card"/>:</label>
                        <input type="text" class="form-control" id="cardNumber" placeholder="1234123412341234"
                               name="card_number">
                    </div>
                </div>
                <br/>
                <div class="form-row">
                    <input type="hidden" value="${param.order_main_id}" name="order_main_id">
                    <button type="submit" class="btn btn-primary"><fmt:message key="pay"/></button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
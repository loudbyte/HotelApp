<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="edit"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <h3><fmt:message key="edit"/> <fmt:message key="order.number"/> - ${requestScope.order_main_id}</h3>
    <div class="row">
        <div class="col-sm">
            <form action="${pageContext.request.contextPath}/controller/edit_order" method="post">
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="person_id"><fmt:message key="client"/> ID</label>
                        <input type="text" class="form-control" id="person_id" name="person_id" value="${requestScope.person_id}">
                    </div>
                    <div class="form-group col-md-5">
                        <label for="status_id"><fmt:message key="status"/> ID</label>
                        <input type="number" class="form-control" id="status_id" name="status_id" value="${requestScope.status_id}"
                               placeholder="1 New, 2 In progress, 3 Done, 4 Cancelled, 5 Denial 6 Paid">
                    </div>
                    <div class="form-group col-md-5">
                        <label for="date"><fmt:message key="date"/></label>
                        <input type="text" class="form-control" id="date" name="date" value="${requestScope.date}">
                    </div>
                </div>
                <br/>
                <input type="hidden" name="order_main_id" value="${requestScope.order_main_id}" >
                <div class="form-row">
                    <button type="submit" style="width: 100px" class="btn btn-primary"><fmt:message key="save"/></button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>

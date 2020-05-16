<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title>Edit order</title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <h3>Редактирование заказа - ${requestScope.order_id}</h3>
    <div class="row">
        <div class="col-sm">
            <form action="${pageContext.request.contextPath}/controller/edit_order" method="post">
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="person_id">ID клиента</label>
                        <input type="text" class="form-control" id="person_id" name="person_id" value="${requestScope.person_id}">
                    </div>
                    <div class="form-group col-md-5">
                        <label for="status_id">Статус заказа</label>
                        <input type="text" class="form-control" id="status_id" name="status_id" value="${requestScope.status_id}"
                               placeholder="1 - New, 2 - In progress, 3 - Done, 4 - Cancelled, 4 -Denial">
                    </div>
                    <div class="form-group col-md-5">
                        <label for="date">Дата заказа</label>
                        <input type="text" class="form-control" id="date" name="date" value="${requestScope.date}">
                    </div>
                </div>
                <br/>
                <input type="hidden" name="order_id" value="${requestScope.order_id}" >
                <div class="form-row">
                    <button type="submit" style="width: 100px" class="btn btn-primary">Сохранить</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>

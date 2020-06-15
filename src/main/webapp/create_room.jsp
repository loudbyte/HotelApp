<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="new.room"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <div class="row">
        <div class="col-sm">
            <form action="${pageContext.request.contextPath}/controller/create_room" method="post">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="roomNumber"><fmt:message key="room.number"/></label>
                        <input type="number" class="form-control" id="roomNumber" name="room_number">
                    </div>
                    <div class="form-group col-md-6">
                        <label for="capacity"><fmt:message key="capacity"/></label>
                        <input type="number" class="form-control" id="capacity" name="capacity">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="roomClassId"><fmt:message key="class"/> ID</label>
                        <input type="number" class="form-control" id="roomClassId" placeholder='<fmt:message key="deluxe"/> - 1, <fmt:message key="suite"/> - 2, <fmt:message key="standard"/> - 3' name="room_class_id">
                    </div>
                    <div class="form-group col-md-6">
                        <label for="price"><fmt:message key="price"/></label>
                        <input type="number" class="form-control" id="price" placeholder="" name="price">
                    </div>
                </div>
                <div class="form-row">
                    <div class="custom-control custom-checkbox my-2 mr-sm-5">
                        <input type="checkbox" class="custom-control-input" id="availability" name="availability" checked>
                        <label class="custom-control-label" for="availability"><fmt:message key="available"/></label>
                    </div>
                </div>
                <br/>
                <div class="form-row">
                    <button type="submit" class="btn btn-primary"><fmt:message key="save"/></button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
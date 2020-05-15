<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title>Create room</title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <div class="row">
        <div class="col-sm">
            <form action="${pageContext.request.contextPath}/controller/create_room" method="post">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="roomNumber">Номер комнаты</label>
                        <input type="number" class="form-control" id="roomNumber" name="room_number">
                    </div>
                    <div class="form-group col-md-6">
                        <label for="capacity">Вместимость</label>
                        <input type="number" class="form-control" id="capacity" name="capacity">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="roomClassId">Класс ID</label>
                        <input type="number" class="form-control" id="roomClassId" placeholder="Delux - 1, Suite - 2, Standard - 3" name="room_class_id">
                    </div>
                    <div class="form-group col-md-6">
                        <label for="price">Цена</label>
                        <input type="number" class="form-control" id="price" placeholder="" name="price">
                    </div>
                </div>
                <div class="form-row">
                    <div class="custom-control custom-checkbox my-2 mr-sm-5">
                        <input type="checkbox" class="custom-control-input" id="availability" name="availability">
                        <label class="custom-control-label" for="availability">Доступность</label>
                    </div>
                </div>
                <br/>
                <div class="form-row">
                    <button type="submit" class="btn btn-primary">Сохранить</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>

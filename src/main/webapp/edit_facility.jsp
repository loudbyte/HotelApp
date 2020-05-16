<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title>Edit facility</title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <h3>Редактирование услуги - ${param.get("facility_id")}</h3>
    <div class="row">
        <div class="col-sm">
            <form action="${pageContext.request.contextPath}/controller/edit_facility" method="post">
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="facility_name">Название услуги</label>
                        <input type="text" class="form-control" id="facility_name" name="facility_name" value="${param.get("facility_name")}">
                    </div>
                    <div class="form-group col-md-5">
                        <label for="facility_price">Цена услуги</label>
                        <input type="text" class="form-control" id="facility_price" name="facility_price" value="${param.get("facility_price")}">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="facility_description">Описание услуги</label>
                        <input type="text" class="form-control" id="facility_description" name="facility_description" value="${param.get("facility_description")}">
                    </div>
                </div>
                <br/>
                <input type="hidden" name="facility_id" value="${param.get("facility_id")}" >
                <div class="form-row">
                    <button type="submit" style="width: 100px" class="btn btn-primary">Сохранить</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>

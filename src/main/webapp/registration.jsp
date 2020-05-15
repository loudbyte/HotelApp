<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title>Registration</title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
        <div class="row">
            <div class="col-sm">
                <form action="${pageContext.request.contextPath}/controller/registration_person" method="post">
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="inputFirstName">Имя</label>
                            <input type="text" class="form-control" id="inputFirstName" name="first_name">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="inputLastName">Фамилия</label>
                            <input type="text" class="form-control" id="inputLastName" name="last_name">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="inputBirthday">Дата рождения</label>
                            <input type="date" class="form-control" id="inputBirthday" placeholder="" name="birthday">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="inputPhone">Телефон</label>
                            <input type="text" class="form-control" id="inputPhone" placeholder="+77001234567" name="phone">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="inputEmail">Электронный адрес</label>
                            <input type="text" class="form-control" id="inputEmail" placeholder="example@gmail.com" name="email">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="inputPassword">Пароль</label>
                            <input type="password" class="form-control" id="inputPassword" placeholder="" name="password">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="inputIin">ИИН</label>
                            <input type="text" class="form-control" id="inputIin" name="iin">
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
                </form>
            </div>
        </div>
</div>
</body>
</html>

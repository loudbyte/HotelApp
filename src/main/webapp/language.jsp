<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="languageDAO" class="com.epam.hotel.dao.impl.LanguageDAOImpl"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="languages"/></title>
    <jsp:include page="style.jsp"/>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <div class="row">
        <div class="col-9">
            <table class="table table-sm">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col"><fmt:message key="languages"/></th>
                    <th scope="col">
                    </td>
                    <th scope="col">
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="languageMap" items="${languageDAO.languageMap}">
                    <tr>
                        <td>${languageMap.key}</td>
                        <td>${languageMap.value}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller/delete_language" method="post">
                                <input type="hidden" name="language_id" value="${languageMap.key}">
                                <button type="submit"
                                        class="btn btn-danger"><fmt:message key="delete_language"/></button>
                            </form>
                        </td>
                        <form action="${pageContext.request.contextPath}/controller/edit_language" method="post">
                            <td>
                                <input type="text" name="locale" placeholder="<fmt:message key="enter_locale"/> ">
                                <input type="hidden" name="language_id" value="${languageMap.key}">
                                <button type="submit"
                                        class="btn btn-warning"><fmt:message key="edit_language"/></button>
                            </td>
                        </form>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="row">
            <form action="${pageContext.request.contextPath}/controller/create_language" method="post">
                <h4><fmt:message key="add_language"/>:</h4>
                <input type="text" name="locale" placeholder="<fmt:message key="enter_locale"/> ">
                <button type="submit"
                        class="btn btn-info"><fmt:message key="add_language"/></button>
            </form>
        </div>
    </div>
</div>
</body>
</html>

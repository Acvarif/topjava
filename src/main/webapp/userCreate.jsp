<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <title>Add new user</title>
    <style>
        table  {
            border: 1px solid black;
            text-align: left;
        }
    </style>
</head>
<body>

<form method="POST" action='users?action=create'>
    <table cellpadding="10">
        <tr>
            <td>Name :</td>
            <td><input type="text" name="name" value="<c:out value="${users.name}" />" /></td>
        </tr>
        <tr>
            <td>Email :</td>
            <td><input type="email" name="email" value="<c:out value="${user.email}" />" /></td>
        </tr>
        <tr>
            <td>Password :</td>
            <td><input type="text" name="password" value="<c:out value="${user.password}" />" /></td>
        </tr>
        <tr>
            <td>Calories :</td>
            <td><input type="text" name="password" value="<c:out value="${user.caloriesPerDay}" />" /></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit" /></td>
        </tr>
    </table>
</form>
</body>
</html>
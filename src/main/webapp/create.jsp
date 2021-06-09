<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <title>Add new meal</title>
    <style>
        table  {
            border: 1px solid black;
            text-align: left;
        }
    </style>
</head>
<body>

<form method="POST" action='meals?action=create'>
    <table cellpadding="10">
        <tr>
            <td>Date :</td>
            <td><input type="datetime-local" name="date" value="<c:out value="${meal.dateTime}" />" /></td>
        </tr>
        <tr>
            <td>Description :</td>
            <td><input type="text" name="description" value="<c:out value="${meal.description}" />" /></td>
        </tr>
        <tr>
            <td>Calories :</td>
            <td><input type="text" name="calories" value="<c:out value="${meal.calories}" />" /></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit" /></td>
        </tr>
    </table>
</form>
</body>
</html>
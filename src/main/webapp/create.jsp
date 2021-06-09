<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <title>Add new meal</title>
</head>
<body>

<form method="POST" action='meals?action=create'>
    Date : <input type="datetime-local" name="date" value="<c:out value="${meal.dateTime}" />" /> <br />
    Description : <input type="text" name="description" value="<c:out value="${meal.description}" />" /> <br />
    Calories : <input type="text" name="calories" value="<c:out value="${meal.calories}" />" /> <br />
    <input type="submit" value="Submit" />
</form>
</body>
</html>
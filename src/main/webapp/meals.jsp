<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <title>Meals</title>
    <style>
        table, th, td {
            border: 1px solid black;
            width: 50%;
            padding: 10px;
            text-align: left;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
    <table >
        <thead>
        <tr>
            <th>
                Date
            </th>
            <th>
                Description
            </th>
            <th>
                Calories
            </th>
            <th>

            </th>
            <th>

            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${meals}" var="meal">
            <tr style="color: ${meal.excess ? 'red' : 'green'}">
                <td>
                    ${meal.dateTime}
                </td>
                <td>
                    ${meal.description}
                </td>
                <td>
                    ${meal.calories}
                </td>
                <td>

                </td>
                <td>

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</body>
</html>
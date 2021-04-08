<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Nuovo Utente</title>
</head>
<body>
    <div align="center">
        <c:if test="${user != null}">
            <form action="updateUser" method="post">
        </c:if>
        <c:if test="${user == null}">
            <form action="insertUser" method="post">
        </c:if>
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                    <c:if test="${user != null}">
                        Modifica Utente
                    </c:if>
                    <c:if test="${user == null}">
                        Nuovo Utente
                    </c:if>
                </h2>
            </caption>
            <c:if test="${user != null}">
                <input type="hidden" name="id" value="<c:out value='${user.id}' />" />
            </c:if>
            <tr>
                <th>Nome: </th>
                <td>
                    <input type="text" name="name" value="<c:out value='${user.name}' />" required />
                </td>
            </tr>
            <tr>
                <th>Cognome: </th>
                <td>
                    <input type="text" name="surname" value="<c:out value='${user.surname}' />" required />
                </td>
            </tr>
            <tr>
                <th>Data di Nascita: </th>
                <td>
                    <input type="date" name="date_of_birth" value="<c:out value='${user.dateOfBirth}' />" required />
                </td>
            </tr>
            <tr>
                <th>Codice Fiscale: </th>
                <td>
                    <input type="text" name="fiscal_code" maxlength="16" style="text-transform:uppercase" value="<c:out value='${user.fiscalCode}' />" required />
                </td>
            </tr>
            <tr>
                <th>Username: </th>
                <td>
                    <input type="text" name="username" value="<c:out value='${user.username}' />" required />
                </td>
            </tr>
            <tr>
                <th>Password: </th>
                <td>
                    <input type="text" name="password" value="<c:out value='${user.password}' />" required />
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Inserisci" />
                </td>
            </tr>
        </table>
        </form>
    </div>
</body>
</html>
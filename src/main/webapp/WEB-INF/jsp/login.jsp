<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>

<head>
    <title>Login Page</title>
</head>


<body>

<h3>Please login</h3>

<form:form action="/authenticateTheUser" method='POST'>

    <p>
        User name: <input type="text" name="username" />
    </p>

    <p>
        Password: <input type="password" name="password" />
    </p>

    <input type="submit" value="Login" />

</form:form>

</body>


</html>
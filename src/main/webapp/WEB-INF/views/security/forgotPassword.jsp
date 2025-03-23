<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Forgot Password</title>
    <!-- Local Bootstrap CSS -->
    <link href="<c:url value='/resources/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(to right, #6a11cb, #2575fc);
            height: 100vh;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: Arial, sans-serif;
        }
        .content-box {
            background: rgba(255, 255, 255, 0.85); /* Semi-transparent white */
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body class="d-flex align-items-center justify-content-center vh-100">

<!-- Forgot Password Form -->
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card shadow-lg">
                <div class="card-header text-center bg-primary text-white">
                    <h4>Forgot Password</h4>
                </div>
                <div class="card-body">
                    <c:if test="${not empty message}">
                        <div class="alert alert-success text-center">${message}</div>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger text-center">${error}</div>
                    </c:if>

                    <form:form action="/forgotPassword" method="post" modelAttribute="appUser">
                        <div class="mb-3">
                            <label class="form-label">Enter your email</label>
                            <form:input path="email" class="form-control" placeholder="Enter your email"/>
                            <form:errors path="email" class="text-danger"/>
                        </div>
                        <div class="text-center">
                            <button type="submit" class="btn btn-primary w-100">Request Reset Link</button>
                        </div>
                    </form:form>

                    <div class="text-center mt-3">
                        <a href="/login-form">Back to Login</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Local Bootstrap JS -->
<script src="<c:url value='/resources/bootstrap/js/bootstrap.bundle.min.js'/>"></script>

</body>
</html>

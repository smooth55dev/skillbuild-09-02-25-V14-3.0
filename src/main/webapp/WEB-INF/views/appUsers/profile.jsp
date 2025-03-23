<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Profile</title>
    <link href="<c:url value='/resources/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background: linear-gradient(to right, #6a11cb, #2575fc);
            margin: 0;
        }
        .content-box {
            background: rgba(255, 255, 255, 0.85);
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .select{
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: auto;
        }

    </style>
</head>

<!-- Main body !-->
<body class="d-flex align-items-center justify-content-center vh-100">

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card shadow-lg">
                <div class="card-header text-center bg-primary text-white">
                    <h2>User Profile</h2>
                </div>
                <div class="card-body">
                    <form:form action="${pageContext.request.contextPath}/editProfile" modelAttribute="user" method="post">
                        <!-- Hidden ID Field -->
                        <form:hidden path="id"/>
                        <div class="mb-3">
                            <label>Name:</label>
                            <form:input path="name" class="form-control" required="true"/><br/>
                        </div>

                        <div class="mb-3">
                            <label>Email:</label>
                            <form:input class="form-control" path="email" required="true"/><br/>
                        </div>

                        <div class="mb-3">
                            <label>Confirm Password To Authorise Changes:</label>
                            <form:password class="form-control" path="password"/><br/>
                            <input type="hidden" name="_password" value="1"/>
                        </div>

                        <div class="mb-3">
                            <input type="text" name="university" value="${user.university}">
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Save Changes</button>
                        <br><br>
                        <a href="/dashboard" class="btn btn-primary w-100">Return to Dashboard</a>

                    </form:form>
                </div>
            </div>
        </div>
    </div>
    <h3>Badges Earned</h3>
    <c:forEach items="${badges}" var="badge">
        <div style="display:inline-block; margin:10px;">
            <img src="${badge.badge.imageUrl}" alt="${badge.badge.name}" width="50" height="50"/><br/>
            <strong>${badge.badge.name}</strong><br/>
            <small>${badge.badge.description}</small>
        </div>
    </c:forEach>

</div>

<!-- Local Bootstrap JS -->
<script src="<c:url value='/resources/bootstrap/js/bootstrap.bundle.min.js'/>"></script>
</body>
</html>

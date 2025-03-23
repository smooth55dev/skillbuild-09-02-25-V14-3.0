<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add App User</title>
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
            background: rgba(255, 255, 255, 0.85);
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body class="d-flex align-items-center justify-content-center vh-100">

<!-- Add User Form -->
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card shadow-lg">
                <div class="card-header text-center bg-primary text-white">
                    <h4>Add App User</h4>
                </div>
                <div class="card-body">
                    <form:form action="/addAppUser" method="post" modelAttribute="user">
                    <div class="mb-3">
                            <label class="form-label text-white">Name</label>
                            <form:input path="name" class="form-control" placeholder="Enter your name"/>
                            <form:errors path="name" class="text-danger"/>
                        </div>
                        <div class="mb-3">
                            <label class="form-label text-white">Password</label>
                            <form:password path="password" class="form-control" placeholder="Enter your password"/>
                            <form:errors path="password" class="text-danger"/>
                        </div>
                        <div class="mb-3">
                            <label class="form-label text-white">Email</label>
                            <form:input path="email" class="form-control" placeholder="Enter your email"/>
                            <form:errors path="email" class="text-danger"/>
                        </div>
                        <div class="mb-3">
                            <label class="form-label text-white">University</label>
                            <form:input path="university" class="form-control" placeholder="Enter your university"/>
                            <form:errors path="university" class="text-danger"/>
                        </div>
                        <div class="text-center">
                            <button type="submit" class="btn btn-primary w-100">Submit</button>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Local Bootstrap JS -->
<script src="<c:url value='/resources/bootstrap/js/bootstrap.bundle.min.js'/>"></script>

</body>
</html>

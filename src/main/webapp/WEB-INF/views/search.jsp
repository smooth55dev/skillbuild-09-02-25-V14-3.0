<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Courses</title>
    <link href="<c:url value='/resources/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(to right, #6a11cb, #2575fc);
            height: 100vh;
            margin: 0;
            font-family: Arial, sans-serif;
            display: flex;
            flex-direction: column;
        }

        .container-fluid {
            height: 85vh;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }

        .navbar {
            background: rgba(255, 255, 255, 0.9);
            padding: 5px 0; /* Reduce padding to decrease height */
            min-height: 50px; /* Adjust the minimum height */
        }
        .navbar-nav .nav-link,
        .navbar-nav form {
            display: flex;
            align-items: center; /* Center items vertically */
            height: 100%; /* Ensure they take full navbar height */
            padding: 5px 0; /* Adjust padding for alignment */
        }
      



        .search-container {
            background: rgba(255, 255, 255, 0.85);
            padding: 25px;
            border-radius: 15px;
            box-shadow: 0 6px 15px rgba(0, 0, 0, 0.4);
            max-width: 550px;
            width: 100%;
            backdrop-filter: blur(10px);
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
            font-size: 24px;
            font-weight: bold;
        }

        .form-control, .btn {
            margin-top: 12px;
            border-radius: 8px;
        }

        .btn-primary {
            width: 100%;
            background: linear-gradient(to right, #ff6b6b, #ff4757);
            border: none;
            transition: 0.3s;
            font-size: 16px;
            font-weight: bold;
            padding: 12px;
        }

        .btn-primary:hover {
            background: linear-gradient(to right, #ff4757, #ff2e63);
            transform: scale(1.05);
        }
    </style>
</head>
<body>

<!-- Navigation Bar -->
<nav class="navbar navbar-expand-lg navbar-light">
    <div class="container">
        <a class="navbar-brand" href="#">MyLogo</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/dashboard">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Track Start Time</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/trackCompletion">Track Completion Time</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/courses/searchPage">Search & Filter Courses</a></li>
                <li class="nav-item"><a class="nav-link" href="#">User Profile</a></li>
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/logout" method="post" style="display:inline;">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <button class="nav-link btn btn-link" type="submit">Logout</button>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Main Content -->
<div class="container-fluid">
    <div class="search-container">
        <h2>Search Courses</h2>
        <form action="${pageContext.request.contextPath}/courses/search" method="get">
            <input type="hidden" name="_csrf" value="${_csrf.token}">

            <div class="form-group">
                <label for="keyword">Keyword</label>
                <input type="text" name="keyword" id="keyword" placeholder="Search courses..." class="form-control" value="${param.keyword}">
            </div>

            <div class="form-group">
                <label for="category">Category</label>
                <select name="category" id="category" class="form-control">
                    <option value="">All Categories</option>
                    <option value="AI" ${param.category == 'AI' ? 'selected' : ''}>Artificial Intelligence</option>
                    <option value="Cloud" ${param.category == 'Cloud' ? 'selected' : ''}>Cloud Computing</option>
                    <option value="Security" ${param.category == 'Security' ? 'selected' : ''}>Security</option>
                </select>
            </div>

            <div class="form-group">
                <label for="skillLevel">Skill Level</label>
                <select name="skillLevel" id="skillLevel" class="form-control">
                    <option value="">All Levels</option>
                    <option value="Beginner" ${param.skillLevel == 'Beginner' ? 'selected' : ''}>Beginner</option>
                    <option value="Intermediate" ${param.skillLevel == 'Intermediate' ? 'selected' : ''}>Intermediate</option>
                    <option value="Advanced" ${param.skillLevel == 'Advanced' ? 'selected' : ''}>Advanced</option>
                </select>
            </div>

            <button type="submit" class="btn btn-primary">Search</button>
        </form>
    </div>
</div>

<!-- Local Bootstrap JS -->
<script src="<c:url value='/resources/bootstrap/js/bootstrap.bundle.min.js'/>"></script>
</body>
</html>

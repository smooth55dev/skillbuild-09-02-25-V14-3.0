<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <!-- Local Bootstrap CSS -->
    <link href="<c:url value='/resources/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(to right, #6a11cb, #2575fc);
            height: 100vh;
            margin: 0;
            font-family: Arial, sans-serif;
            overflow: hidden; /* Prevent unnecessary scrolling */
            display: flex;
            flex-direction: column;
        }

        .container-fluid {
            height: 85vh; /* Adjust content height to fit screen */
            display: flex;
            flex-direction: column;
            justify-content: center;
        }

        .row {
            height: 100%;
            display: flex;
            align-items: stretch;
            justify-content: space-evenly;
            flex-wrap: wrap;
        }

        .content-box {
            background: rgba(255, 255, 255, 0.85);
            padding: 15px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            height: auto;
            min-height: 250px; /* Ensures it doesn't shrink too much */
            overflow: auto; /* Enables scrolling inside the box if needed */
            cursor: pointer;
        }

        .navbar {
            background: rgba(255, 255, 255, 0.9);
        }

        .table {
            margin-bottom: 0; /* Prevents extra space */
        }

        .collapsible-header {
            cursor: pointer;
            text-align: center;
            font-weight: bold;
            padding: 10px;
            background-color: #f8f9fa;
            border-radius: 10px;
        }

        /* Ensure collapsible sections are initially hidden on small screens */
        @media (max-width: 768px) {
            .collapse {
                display: none;
            }
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
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Track Start Time</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/trackCompletion">Track Completion Time</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/courses/searchPage">Search & Filter Courses</a></li>



                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/editProfile">User Profile</a></li>
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
<div class="container-fluid mt-4">
    <div class="row">
        <!-- Available Courses -->
        <div class="col-lg-4 col-md-6">
            <div class="collapsible-header" data-target="#availableCourses">Available Courses</div>
            <div id="availableCourses" class="content-box collapse">
                <p>Debug: Courses Size = ${courses.size()}</p>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Course Name</th>
                        <th>Description</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="course" items="${courses}">
                        <tr>
                            <td><a href="${course.url}" target="_blank">${course.name}</a></td>
                            <td>${course.description}</td>
                            <td><a href="${course.url}" class="btn btn-primary btn-sm" target="_blank">Go</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Started Courses -->
        <div class="col-lg-4 col-md-6">
            <div class="collapsible-header" data-target="#startedCourses">Started Courses</div>
            <div id="startedCourses" class="content-box collapse">
                <div class="text-center">
                    <p>List of started courses will be displayed here.</p>
                    <p>Minimum table contents</p>
                    <p>With name of person logged in, course, course started time, status, completed button </p>
                </div>
            </div>
        </div>

        <!-- Completed Courses -->
        <div class="col-lg-4 col-md-6">
            <div class="collapsible-header" data-target="#completedCourses">Completed Courses</div>
            <div id="completedCourses" class="content-box collapse">
                <div class="text-center">
                    <p>List of completed courses will be displayed here.</p>
                    <p>With name of person logged in, course, course completion time, button to quizzes</p>

                </div>
            </div>
        </div>
    </div>
</div>

<!-- Local Bootstrap JS -->
<script src="<c:url value='/resources/bootstrap/js/bootstrap.bundle.min.js'/>"></script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        // Select all collapsible headers
        document.querySelectorAll(".collapsible-header").forEach(function(header) {
            header.addEventListener("click", function() {
                let targetId = this.getAttribute("data-target");
                let targetElement = document.querySelector(targetId);

                if (targetElement) {
                    // Toggle collapse
                    if (targetElement.style.display === "none" || targetElement.style.display === "") {
                        targetElement.style.display = "block";
                    } else {
                        targetElement.style.display = "none";
                    }
                }
            });
        });

        // Collapse all sections initially on smaller screens
        function handleResize() {
            if (window.innerWidth < 768) {
                document.querySelectorAll(".content-box").forEach(function(box) {
                    box.style.display = "none";
                });
            } else {
                document.querySelectorAll(".content-box").forEach(function(box) {
                    box.style.display = "block";
                });
            }
        }

        // Run on load and on resize
        handleResize();
        window.addEventListener("resize", handleResize);
    });
</script>

</body>
</html>

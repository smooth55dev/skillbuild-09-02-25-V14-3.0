<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Results</title>

    <!-- Corrected link to Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background: linear-gradient(to right, #7745aa, #175ee4);
            font-family: Arial, sans-serif;
            color: white;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            background: rgba(255, 255, 255, 0.3);
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
            max-width: 800px;
            width: 100%;
        }

        h2 {
            text-align: center;
            color: white;
            margin-bottom: 20px;
        }

        .table {
            margin-top: 20px;
            background: white;
            border-radius: 10px;
            overflow: hidden;
        }

        .table th {
            background-color: #ff6b6b;
            color: white;
            text-align: center;
        }

        .table td {
            color: black;
            text-align: center;
        }

        .btn-primary {
            background-color: #ff6b6b;
            border: none;
            transition: 0.3s;
        }

        .btn-primary:hover {
            background-color: #ff4757;
        }

        .btn-secondary {
            margin-top: 20px;
            display: block;
            text-align: center;
            background-color: #444;
            color: white;
            padding: 10px;
            border-radius: 5px;
            text-decoration: none;
            transition: 0.3s;
        }

        .btn-secondary:hover {
            background-color: #222;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Search Results</h2>

    <!-- Debug: Display the number of courses -->
    <p style="text-align: center; color: white;">Debug: Course List Size = <strong>${fn:length(courses)}</strong></p>

    <c:choose>
        <c:when test="${empty courses}">
            <p style="text-align: center;">No courses found.</p>
        </c:when>
        <c:otherwise>
            <!-- Display the table of courses if they exist -->
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Course Name</th>
                    <th>Description</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <!-- Loop through each course and display it -->
                <c:forEach var="course" items="${courses}">
                    <tr>
                        <td><a href="${course.url}" target="_blank">${course.name}</a></td>
                        <td>${course.description}</td>
                        <td><a href="${course.url}" class="btn btn-primary btn-sm" target="_blank">Go</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

    <!-- Back button to return to the search page -->
    <a href="${pageContext.request.contextPath}/courses/searchPage" class="btn btn-secondary">Back to Search</a>
</div>
</body>
</html>

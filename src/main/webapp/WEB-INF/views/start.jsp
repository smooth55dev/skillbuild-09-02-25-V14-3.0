<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Start Page - SkillBuild</title>
	<link href="<c:url value='/resources/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">
	<script src="<c:url value='/resources/s/all.min.js'/>"></script>
	<style>
		body {
			background: linear-gradient(to right, #6a11cb, #2575fc) !important;
			overflow: hidden; /* remove auto to prevent double scrollbars */
		}

		.content {
			position: relative;
			z-index: 2;
			opacity: 1;
			max-height: 100vh;
			overflow-y: auto; /* ONLY content scrolls, NOT body */
			padding-bottom: 20px;
		}

		.navbar {
				flex-wrap: nowrap !important; /* Prevent navbar wrapping */
				padding: 0.5rem 1rem !important; /* Reduce padding for smaller navbar height */
			}

			.navbar-icons {
				flex-shrink: 0 !important;
				display: flex;
				align-items: center;
				gap: 10px; /* Adds spacing between elements */
			}

			.navbar-icons img {
				height: 40px !important; /* Reduce icon size slightly for better alignment */
				cursor: pointer;
			}

			.navbar-toggler {
				margin-left: auto !important; /* Force toggler to the right always */
				padding: 0.5rem !important;   /* Smaller toggler padding to reduce navbar height */
			}

			/* Ensure space between robot icon and navbar toggler */
			.navbar-icons + .navbar-toggler {
				margin-left: 15px !important;
			}

			/* Prevent navbar-brand from shrinking or overflowing */
		.navbar-brand {
			white-space: nowrap; /* prevent text from breaking */
			overflow: hidden;
			text-overflow: ellipsis; /* Add ellipsis if it gets too small */
			max-width: 100%; /* ensure it never goes beyond navbar width */
		}


		.course-box, .info-box {
			height: calc(80vh - 30px);
			margin-bottom: 30px;
		}
		.thinking-text {
			font-weight: bold;
			font-size: 1.2rem;
			color: #FF4500;
			animation: shakeText 0.5s infinite alternate ease-in-out;
		}
		.info-box .card-body {
			display: flex;
			flex-direction: column;
			padding: 1rem;
			overflow: hidden;
		}
		#responseContainer {
			flex-grow: 1;
			overflow-y: auto;
		}
		#icon-container {
			flex-shrink: 0;
		}
		#info-content {
			display: flex;
			flex-direction: column;
			align-items: center;      /* horizontal centering */
			justify-content: center;  /* vertical centering */
			text-align: center;       /* text alignment */
		}

		@keyframes shakeText {
			0% { transform: translateX(-3px); }
			100% { transform: translateX(3px); }
		}
		@keyframes pulsate {
			0% { transform: scale(1); }
			50% { transform: scale(1.5); }
			100% { transform: scale(1); }
		}
		.pulsate {
			animation: pulsate 1.5s infinite;
		}
	</style>
	<script>
		function showAboutInfo() {
			document.getElementById("info-content").innerHTML = `
                <div class='container-fluid'>
                    <div class='row mb-3'>
                        <div class='col-12 p-3 bg-light text-center'>
                            <h4>This is a project given to us by Software Engineering Department</h4>
                            <p>The Object is to learn about the Agile methodologies</p>
                            <p>It is a group project consisting of 6 developer students</p>
                        </div>
                    </div>
                    <div class='row'>
                        <div class='col-md-6 p-3 bg-light text-center' style='margin-right: 15px;'>
                            <h5>Sprint 1</h5>
                             <table class='table table-bordered table-sm text-center'>
                                <thead class='table-light'>
                                    <tr>
                                        <th>Student</th>
                                        <th>User Story</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr><td>Omar</td><td>Account creation and log in system</td></tr>
                                    <tr><td>Prisha</td><td>View Dashboard</td></tr>
                                    <tr><td>Hargun</td><td>Search and Filter Courses</td></tr>
                                    <tr><td>Pavan</td><td>Record Start time</td></tr>
                                    <tr><td>Sharmin</td><td>Record Completion</td></tr>
                                    <tr><td>Catalin</td><td>User Profile Managment</td></tr>                                    
                                </tbody>
                            </table>
                        </div>
                        <div class='col-md-5 p-3 bg-light text-center'>
                            <h5>Sprint 2</h5>
                            <table class='table table-bordered table-sm text-center'>
                                <thead class='table-light'>
                                    <tr>
                                        <th>Student</th>
                                        <th>User Story</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr><td>Omar</td><td>Home Page with Ai Powered Assistance</td></tr>
                                    <tr><td>Prisha</td><td>Rating and Reviews</td></tr>
                                    <tr><td>Hargun</td><td>Add Friends and Leader Board</td></tr>
                                    <tr><td>Pavan</td><td>TBC</td></tr>
                                    <tr><td>Sharmin</td><td>Course Process Bar</td></tr>
                                    <tr><td>Catalin</td><td>Add Tags and Badges</td></tr>                                   
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>`
		}

	</script>
</head>
<body>
<div class="container mt-4 content">
	<!-- Navigation Bar -->
	<nav class="navbar navbar-expand-lg navbar-light bg-light text-center">
		<div class="navbar-icons">
			<a href="<c:url value='/' />">
				<img src="<c:url value='/images/logo.png' />" alt="Logo" height="40" class="me-2">
			</a>
		</div>
		<span class="navbar-brand">Our SkillBuild Project Leicester University</span>
		<div class="navbar-icons">
			<img src="/images/robot.png" alt="Robot Icon" class="chat-icon">
			<span>AI Assistant</span>
		</div>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav ms-auto">
				<li class="nav-item"><a class="nav-link" href="#" onclick="showAboutInfo()">About</a></li>
				<li class="nav-item"><a class="nav-link" href="/dashboard">Dashboard</a></li>
				<li class="nav-item"><a class="nav-link" href="/login-form">Login</a></li>
			</ul>
		</div>
	</nav>

	<!-- Main Grid Layout -->
	<div class="row mt-4">
		<!-- Courses Grid -->
		<div class="col-md-3">
			<div class="card course-box">
				<div class="card-header">Courses</div>
				<div class="card-body">
					<div class="mt-2" id="courseList">
						<h6 style="color: green">Click a Course to find out what it could cover</h6>
						<div class="row">
							<c:forEach var="course" items="${courses}">
								<div class="col-12">
									<a href="#" class="btn btn-light w-100 mb-2 course-link"
									   data-course-name="${course.name}">
										<c:out value="${course.name}" />
									</a>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Information Grid (Chat Responses) -->
		<div class="col-md-9">
			<div class="card info-box">
				<div class="card-header">Information</div>
				<div class="card-body" id="info-content">
					<h4>A learning group project by Leicester University</h4>
					<p>The object is to learn about Agile Methodologies.</p>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="<c:url value='/resources/bootstrap/js/bootstrap.bundle.min.js'/>"></script>
<script src="<c:url value='/resources/bootstrap/js/bootstrap.bundle.min.js'/>"></script>
</body>
</html>

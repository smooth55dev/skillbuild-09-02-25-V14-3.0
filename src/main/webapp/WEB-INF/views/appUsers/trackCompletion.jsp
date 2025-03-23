<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <!-- Local Bootstrap CSS -->
    <link href="<c:url value='/resources/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
            margin-bottom: 0;
            /* Prevents extra space */
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

        h2 {
            text-align: center;
            background: white;
            color: black;
            padding: 10px;
            border-radius: 10px;
        }
        .btn-complete {
            background: #3b82f6;
            color: white;
            border: none;
            padding: 8px 12px;
            border-radius: 5px;
            cursor: pointer;
        }

        .btn-complete:hover {
            background: #2a62c0;
        }

        th, td {
            padding: 10px;
            text-align: center;
        }

        /* Quiz Modal */
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.4);
            padding-top: 60px;
        }

        .modal-content {
            background-color: #fefefe;
            margin: 5% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 50%;
        }

        .close {
            color: red;
            float: right;
            position: absolute;
            right: 10px;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }


/*delete this after*/
        label {
            color: black !important;
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
                <li class="nav-item"><a class="nav-link" href="dashboard">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Track Start Time</a></li>
                <!-- <li class="nav-item"><a class="nav-link" href="trackCompletionorginal.jsp">Track Completion Time</a></li>  Fixed Link -->
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


<div class="container">
    <h2>Ongoing Courses</h2>
    <table class="table">
        <thead>
        <tr>
            <th>Course Name</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody id="ongoingCourses">
        <tr><td>Artificial Intelligence</td><td><button class="btn-complete" onclick="openQuiz('Artificial Intelligence')">Complete</button></td></tr>
        <tr><td>Capstone</td><td><button class="btn-complete" onclick="openQuiz('Capstone')">Complete</button></td></tr>
        <tr><td>Data Science</td><td><button class="btn-complete" onclick="openQuiz('Data Science')">Complete</button></td></tr>
        <tr><td>IBM Cloud</td><td><button class="btn-complete" onclick="openQuiz('IBM Cloud')">Complete</button></td></tr>
        <tr><td>IBM Engineering</td><td><button class="btn-complete" onclick="openQuiz('IBM Engineering')">Complete</button></td></tr>
        </tbody>
    </table>

    <h2>Completed Courses</h2>
    <table class="table">
        <thead>
        <tr>
            <th>Course Name</th>
            <th>Completion Time</th>
        </tr>
        </thead>
        <tbody id="completedCourses"></tbody>
    </table>
</div>

<!-- Quiz Modal -->
<div id="quizModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <h2>Quiz</h2>
        <p id="quizQuestion"></p>
        <div id="quizOptions"></div>
        <button onclick="submitQuiz()">Submit</button>
    </div>
</div>

<script>
    let selectedCourse = "";


    function openQuiz(courseName) {
        selectedCourse = courseName;
        let questionData = {
            "Artificial Intelligence": {
                question: "What is AI?",
                options: ["A programming language", "A simulation of human intelligence", "A type of hardware"],
                answer: "A simulation of human intelligence"
            },
            "Capstone": {
                question: "What is a capstone project?",
                options: ["A beginner project", "A final year project", "A short-term internship"],
                answer: "A final year project"
            },
            "Data Science": {
                question: "What does a data scientist do?",
                options: ["Analyze data", "Cook food", "Build houses"],
                answer: "Analyze data"
            },
            "IBM Cloud": {
                question: "IBM Cloud provides?",
                options: ["Cloud storage only", "Cloud computing services", "Mobile apps"],
                answer: "Cloud computing services"
            },
            "IBM Engineering": {
                question: "IBM Engineering focuses on?",
                options: ["AI only", "Software & hardware solutions", "Banking"],
                answer: "Software & hardware solutions"
            },

            "IBM Automation": {
                question: "What is IBM Cloud Pak for Automation designed for?",
                options: ["Enhancing business workflows using AI powered automation", "running cyber security audits", "Banking"],
                answer: "Enhancing business workflows using AI powered automation"
            },

            "IBM Security": {
                question: "What is IBM's security platform for threat detection and response?",
                options: ["IBM Cloud Foundry", "IBM QRadar", "IBM PowerAI"],
                answer: "IBM QRadar"
            },

            "IBM Z": {
                question: "Why do enterprises, especially in banking and finance, use IBM Z systems?",
                options: ["They provide high-performance mainframe computing with strong security and reliability", "They are used mainly for gaming applications", "They replace cloud computing entirely"],
                answer: "They provide high-performance mainframe computing with strong security and reliability"
            },

            "Power Systems": {
                question: "IBM Power Systems are optimized for which type of workload?",
                options: ["Enterprise AI and cloud computing", " Basic web browsing", "Mobile gaming applications"],
                answer: "Enterprise AI and cloud computing"
            },

            "Quantum Computing": {
                question: "What fundamental unit does IBM Quantum Computing use instead of classical bits?",
                options: ["Qubits", " Bytes", "Processors"],
                answer: "Qubits"
            }






        };

        let quiz = questionData[courseName];

        if (quiz) {
            $("#quizQuestion").text(quiz.question);

            // Fixing options rendering with labels
            let optionsHtml = "";
            console.log("Selected course:", courseName);
            console.log("Quiz object:", quiz);
            console.log("Quiz options:", quiz ? quiz.options : "Quiz not found!");

            quiz.options.forEach(option => {
                optionsHtml += "<label><input type='radio' name='quizAnswer' value='" + option + "'> " + option + "</label><br>";
            });

            $("#quizOptions").html(optionsHtml);
            $("#quizModal").show();

        } else {
            alert("No quiz available for this course.");
        }
    }


    function submitQuiz() {
        let selectedAnswer = $("input[name='quizAnswer']:checked").val();
        let correctAnswer = {
            "Artificial Intelligence": "A simulation of human intelligence",
            "Capstone": "A final year project",
            "Data Science": "Analyze data",
            "IBM Cloud": "Cloud computing services",
            "IBM Engineering": "Software & hardware solutions",
            "IBM Automation": "Enhancing business workflows using AI powered automation",
            "IBM Security": "IBM QRadar",
            "IBM Z":"They provide high-performance mainframe computing with strong security and reliability",
            "Quantum Computing":"Qubits"


        }[selectedCourse];

        if (selectedAnswer === correctAnswer) {
            alert("Quiz Passed! Course marked as completed.");
            let row = $("#ongoingCourses tr").filter(function () {
                return $(this).find("td:first").text() === selectedCourse;
            });

            if (row.length) {
                // Remove the course from Ongoing Courses
                row.remove();

                // Get current timestamp for completion time
                let completionTime = new Date().toLocaleString();

                // Add the course to the Completed Courses table
                $("#completedCourses").append(
                    "<tr><td>" + selectedCourse + "</td><td>" + completionTime + "</td></tr>"
                );
            }
            $("#quizModal").hide();
        } else {
            alert("Wrong answer! Try again.");
        }
    }

    $(".close").click(function () {
        $("#quizModal").hide();
    });
</script>

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



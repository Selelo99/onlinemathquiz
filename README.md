# TimedQuiz — Online Mathematics Quiz System

A full-stack timed online mathematics quiz application built with **HTML, CSS, JavaScript, Java Spring Boot, Spring Security, and MySQL**.

The system allows students to select a mathematics chapter, provide their student details, complete a timed quiz, receive their results, and review their answers. Administrators can securely log in and view student quiz attempts.

---

## Table of Contents

* [Overview](#overview)
* [Features](#features)
* [Technology Stack](#technology-stack)
* [System Architecture](#system-architecture)
* [Project Structure](#project-structure)
* [Quiz Chapters](#quiz-chapters)
* [Application Flow](#application-flow)
* [Database](#database)
* [Backend API](#backend-api)
* [Authentication](#authentication)
* [Frontend State Management](#frontend-state-management)
* [Configuration](#configuration)
* [Running the Project Locally](#running-the-project-locally)
* [Deployment](#deployment)
* [Security](#security)
* [Future Improvements](#future-improvements)
* [Author](#author)

---

# Overview

**TimedQuiz** is a web-based mathematics assessment system designed to provide students with timed online quizzes while allowing administrators to monitor student performance.

The application consists of three main components:

1. **Frontend** — HTML, CSS and JavaScript
2. **Backend** — Java Spring Boot REST API
3. **Database** — MySQL

The frontend communicates with the Spring Boot backend through REST API requests. The backend handles business logic, authentication, quiz attempts and database operations.

---

# Features

## Student Features

* Select a mathematics chapter
* View the selected quiz before starting
* Enter:

  * Name
  * Surname
  * Student Number
* Complete a timed quiz
* Navigate between questions
* Track quiz progress
* Submit the quiz
* Automatically calculate:

  * Score
  * Percentage
  * Pass/Fail status
* Review submitted answers
* Store quiz attempts in the database

## Administrator Features

* Secure administrator login
* View student quiz attempts
* Search/view attempts by student number
* View:

  * Student information
  * Quiz chapter
  * Quiz title
  * Attempt number
  * Score
  * Percentage
  * Status
  * Submission date/time
* Manage quiz attempt records

---

# Technology Stack

## Frontend

* HTML5
* CSS3
* Vanilla JavaScript
* Browser Local Storage
* Fetch API

## Backend

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* Spring Security
* Maven

## Database

* MySQL
* MySQL Workbench for database administration

## Deployment

* GitHub / GitHub Pages — Frontend
* Railway — Spring Boot Backend
* Railway MySQL — Production Database

---

# System Architecture

```text
                    USER
                     │
                     ▼
             ┌───────────────┐
             │ GitHub Pages  │
             │   Frontend    │
             │ HTML/CSS/JS   │
             └───────┬───────┘
                     │
                     │ HTTPS REST API
                     ▼
             ┌───────────────┐
             │    Railway    │
             │ Spring Boot   │
             │     API       │
             └───────┬───────┘
                     │
                     │ JPA / Hibernate
                     ▼
             ┌───────────────┐
             │    MySQL      │
             │   Database    │
             └───────────────┘
```

The browser communicates with the Spring Boot REST API.

The Spring Boot application communicates with MySQL.

The database stores persistent student and quiz-attempt information.

---

# Project Structure

A recommended production structure is:

```text
timedquiz/
│
├── frontend/
│   │
│   ├── index.html
│   ├── about.html
│   ├── instructions.html
│   ├── quiz-select.html
│   ├── student.html
│   ├── quiz.html
│   ├── result.html
│   ├── review.html
│   ├── admin.html
│   │
│   ├── css/
│   │   └── style.css
│   │
│   ├── js/
│   │   ├── quiz-config.js
│   │   ├── questions.js
│   │   ├── questions-ch4.js
│   │   ├── quiz.js
│   │   ├── student.js
│   │   ├── result.js
│   │   ├── review.js
│   │   ├── admin-login.js
│   │   └── admin.js
│   │
│   └── images/
│
├── backend/
│   │
│   ├── pom.xml
│   │
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/
│           │       └── timedquiz/
│           │           └── timedquiz/
│           │               ├── config/
│           │               ├── controller/
│           │               ├── dto/
│           │               ├── entity/
│           │               ├── repository/
│           │               └── service/
│           │
│           └── resources/
│               └── application.properties
│
├── .gitignore
└── README.md
```

---

# Quiz Chapters

## Chapter 3 — Algebra Basics

The Chapter 3 question bank focuses on algebra fundamentals and equations.

```text
Mathematics — Chapter 3

Questions: 20
Duration: 30 minutes
```

## Chapter 4 — Straight Line Graphs

The Chapter 4 question bank focuses on straight-line graphs and related mathematical concepts.

```text
Mathematics — Chapter 4

Questions: 20
Duration: 30 minutes
```

Additional chapters can be added by creating new question banks and updating the quiz configuration.

---

# Application Flow

## Student Flow

```text
Landing Page
     │
     ▼
Choose Quiz
     │
     ├── Chapter 3
     │
     └── Chapter 4
     │
     ▼
Student Details
     │
     ├── Name
     ├── Surname
     └── Student Number
     │
     ▼
Timed Quiz
     │
     ▼
Submit Quiz
     │
     ▼
Calculate Result
     │
     ├── Score
     ├── Percentage
     └── PASS / FAIL
     │
     ▼
Review Answers
     │
     ▼
Save Attempt
     │
     ▼
MySQL Database
```

---

# Database

The application uses MySQL for persistent data storage.

The primary database is:

```text
timedquiz_db
```

The Spring Boot application uses JPA/Hibernate to communicate with the database.

## Student Data

A student is identified using a student number.

Example:

```text
Student
-------------------------
studentNumber
name
surname
```

## Quiz Attempt Data

A quiz attempt contains information such as:

```text
QuizAttempt
-------------------------
id
student
attemptNumber
chapter
quizTitle
score
totalQuestions
percentage
status
submittedAt
```

Example:

```text
Student Number: 123456789
Name: John
Surname: Smith

Chapter: 3
Quiz Title: Mathematics — Chapter 3
Score: 16 / 20
Percentage: 80%
Status: PASS
Attempt Number: 1
```

---

# Backend API

The backend exposes REST endpoints under:

```text
/api
```

## Authentication

### Login

```http
POST /api/auth/login
```

Used by administrators to authenticate.

Request:

```json
{
  "username": "admin",
  "password": "********"
}
```

Successful authentication creates an authenticated Spring Security session.

---

## Student Attempts

### Save Attempt

```http
POST /api/attempts
```

Saves a completed quiz attempt.

---

### Get All Attempts

```http
GET /api/attempts
```

Returns quiz attempts available to the administrator.

---

### Get Student Attempts

```http
GET /api/attempts/students/{studentNumber}
```

Returns attempts associated with a particular student number.

Example:

```text
GET /api/attempts/students/123456789
```

---

### Delete All Attempts

```http
DELETE /api/attempts
```

Deletes stored quiz attempts.

This endpoint should be protected and restricted to administrators in production.

---

# Authentication

The administration system uses **Spring Security**.

The administrator authentication flow is:

```text
Admin Login
     │
     ▼
POST /api/auth/login
     │
     ▼
Spring Security
     │
     ▼
Authentication
     │
     ▼
Authenticated Session
     │
     ▼
Admin Dashboard
```

Administrator credentials should **never be hard-coded into frontend JavaScript**.

---

# Frontend State Management

The frontend uses browser `localStorage` to maintain temporary information while the student moves between pages.

Examples include:

```text
selectedQuizChapter
selectedQuizTitle
studentName
studentSurname
studentNumber
quizScore
quizTotal
quizPercentage
quizAnswers
quizQuestions
lastAttemptId
lastAttemptNumber
```

For example, when Chapter 3 is selected:

```javascript
localStorage.setItem("selectedQuizChapter", "3");

localStorage.setItem(
    "selectedQuizTitle",
    "Mathematics — Chapter 3"
);
```

When Chapter 4 is selected:

```javascript
localStorage.setItem("selectedQuizChapter", "4");

localStorage.setItem(
    "selectedQuizTitle",
    "Mathematics — Chapter 4"
);
```

Local Storage is used for **temporary frontend state**.

MySQL is used for **persistent application data**.

---

# Configuration

## Local Development

The backend requires a MySQL database.

Example development configuration:

```properties
spring.application.name=timedquiz

server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/timedquiz_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Africa/Johannesburg

spring.datasource.username=root
spring.datasource.password=YOUR_LOCAL_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Important

Do **not** commit real database passwords to GitHub.

For production, use environment variables.

---

# Running the Project Locally

## Requirements

Install:

* Java 17
* Maven
* MySQL
* MySQL Workbench
* Git
* A modern web browser

---

## 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/timedquiz.git
```

Enter the project:

```bash
cd timedquiz
```

---

## 2. Create the Database

Open MySQL Workbench and create:

```sql
CREATE DATABASE timedquiz_db;
```

---

## 3. Configure the Backend

Update the Spring Boot database configuration with your local MySQL credentials.

Never commit your actual password.

---

## 4. Start Spring Boot

From the backend directory:

```bash
cd backend
```

Run:

```bash
mvn spring-boot:run
```

The API should become available at:

```text
http://localhost:8080
```

---

## 5. Start the Frontend

Open the frontend using a local development server.

For example, VS Code Live Server may provide:

```text
http://127.0.0.1:5500
```

The frontend must be configured to communicate with the Spring Boot API.

---

# Deployment

The production application is intended to use:

```text
GitHub Pages
       │
       │ Frontend
       ▼
HTML/CSS/JavaScript

       │
       │ HTTPS API
       ▼

Railway
       │
       ├── Spring Boot
       │
       └── MySQL
```

## Frontend

The static frontend can be hosted using GitHub Pages.

The final public website can have an address similar to:

```text
https://YOUR_USERNAME.github.io/timedquiz/
```

---

## Backend

The Spring Boot backend can be deployed to a cloud platform such as Railway.

Example production API:

```text
https://your-timedquiz-backend.example.com
```

The actual URL depends on the deployment provider.

---

## Database

The production MySQL database should be hosted separately from the local development database.

The production Spring Boot application should receive its database credentials through environment variables.

---

# Environment Variables

Production secrets should be stored as environment variables rather than committed to Git.

Typical configuration includes:

```text
MYSQL_URL
MYSQL_USERNAME
MYSQL_PASSWORD
```

Additional application secrets should also be configured as environment variables where appropriate.

Never commit:

```text
.env
real passwords
database credentials
API secrets
private keys
```

to the public repository.

---

# CORS

Because the frontend and backend may use different domains in production, the Spring Boot backend must allow requests from the production frontend domain.

For example:

```text
Frontend:
https://YOUR_USERNAME.github.io

Backend:
https://your-backend.example.com
```

The production CORS configuration should explicitly allow the trusted frontend origin.

Avoid using:

```text
*
```

for credentialed authentication requests.

---

# Security

The following security practices should be applied before production deployment:

* Do not commit passwords to GitHub
* Do not expose MySQL directly to the public internet
* Use HTTPS
* Protect administrator endpoints
* Restrict CORS to the production frontend
* Use Spring Security for authentication
* Store passwords securely
* Validate API input
* Restrict destructive endpoints such as DELETE
* Do not rely solely on `sessionStorage` or `localStorage` to authenticate administrators
* Keep production secrets in environment variables

---

# Development vs Production

## Development

```text
Browser
   │
   ▼
127.0.0.1:5500
   │
   ▼
localhost:8080
   │
   ▼
localhost:3306
   │
   ▼
MySQL
```

## Production

```text
Browser
   │
   ▼
GitHub Pages
   │
   │ HTTPS
   ▼
Spring Boot API
   │
   ▼
Cloud MySQL
```

---

# Future Improvements

Potential future improvements include:

* Student authentication
* Admin dashboard statistics
* Quiz creation through the admin dashboard
* Question management
* Multiple-choice question management
* Student performance graphs
* Chapter performance analysis
* Export results to CSV/PDF
* Email result notifications
* Attempt filtering and sorting
* Pagination for large numbers of attempts
* Role-based access control
* Password reset functionality
* Database migrations using Flyway or Liquibase
* Automated testing
* CI/CD using GitHub Actions
* Custom domain
* Responsive mobile-first interface

---

# Author

**Selelo Serumula**

Final Year Computer Science Student
Tshwane University of Technology
eMalahleni, Mpumalanga, South Africa

---

# Project Status

```text
Status: Completed / Deployment Ready
```

The application has been developed as a full-stack web application using:

```text
HTML
CSS
JavaScript
      +
Spring Boot
      +
Spring Security
      +
JPA / Hibernate
      +
MySQL
```
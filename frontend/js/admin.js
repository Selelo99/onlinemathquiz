
/* =========================================================
   ADMIN DASHBOARD
   ========================================================= */

const API_BASE_URL =
    "http://localhost:8080/api";


/* =========================================================
   ELEMENTS
   ========================================================= */

const studentsTable =
    document.getElementById("students-table");

const totalStudents =
    document.getElementById("total-students");

const totalAttempts =
    document.getElementById("total-attempts");

const passedAttempts =
    document.getElementById("passed-attempts");

const failedAttempts =
    document.getElementById("failed-attempts");

const clearAttemptsButton =
    document.getElementById("clear-attempts");


/* =========================================================
   LOAD ALL ATTEMPTS FROM SPRING BOOT
   ========================================================= */

async function loadAttempts() {

    try {

        const response =
            await fetch(`${API_BASE_URL}/attempts`, {
                method: "GET",
                headers: {
                    "Accept": "application/json"
                }
            });


        if (!response.ok) {

            throw new Error(
                `Server returned ${response.status}`
            );
        }


        const attempts =
            await response.json();


        console.log(
            "Attempts received from backend:",
            attempts
        );


        if (!Array.isArray(attempts)) {

            throw new Error(
                "Invalid attempt data received from server."
            );
        }


        updateStatistics(attempts);

        displayStudents(attempts);

    }
    catch (error) {

        console.error(
            "Could not load attempts:",
            error
        );


        studentsTable.innerHTML = `

            <tr>

                <td
                    colspan="6"
                    class="no-attempts">

                    Could not load student attempts.

                    <br><br>

                    ${escapeHTML(error.message)}

                    <br><br>

                    Make sure Spring Boot is running on:

                    <br>

                    http://localhost:8080

                </td>

            </tr>

        `;

    }
}


/* =========================================================
   GROUP STUDENTS
   ========================================================= */


   function groupStudents(attempts) {

    const students = {};

    attempts.forEach(attempt => {

        const studentNumber =
            attempt.studentNumber ||
            attempt.student?.studentNumber ||
            "";

        const name =
            attempt.name ||
            attempt.student?.name ||
            "Unknown";

        const surname =
            attempt.surname ||
            attempt.student?.surname ||
            "Unknown";

        const key =
            studentNumber ||
            `${name.toLowerCase()}_${surname.toLowerCase()}`;

        if (!students[key]) {

            students[key] = {

                studentNumber: studentNumber,

                name: name,

                surname: surname,

                attempts: []

            };
        }

        students[key].attempts.push(attempt);

    });

    return Object.values(students);
}


/* =========================================================
   STATISTICS
   ========================================================= */

function updateStatistics(attempts) {

    const students =
        groupStudents(attempts);


    let passed = 0;

    let failed = 0;


    attempts.forEach(attempt => {

        const percentage =
            Number(
                attempt.percentage
            ) || 0;


        if (percentage >= 50) {

            passed++;

        }
        else {

            failed++;

        }

    });


    totalStudents.textContent =
        students.length;


    totalAttempts.textContent =
        attempts.length;


    passedAttempts.textContent =
        passed;


    failedAttempts.textContent =
        failed;
}


/* =========================================================
   DISPLAY STUDENTS
   ========================================================= */

function displayStudents(attempts) {

    studentsTable.innerHTML = "";


    const students =
        groupStudents(attempts);


    if (students.length === 0) {

        studentsTable.innerHTML = `

            <tr>

                <td
                    colspan="6"
                    class="no-attempts">

                    No student attempts found.

                </td>

            </tr>

        `;

        window.adminStudents = [];

        return;
    }


    students.forEach(
        (student, index) => {

            const studentAttempts =
                student.attempts;


            /*
             * Sort latest attempt first
             */

            const sortedAttempts =
                [...studentAttempts].sort(
                    (a, b) => {

                        if (
                            a.submittedAt &&
                            b.submittedAt
                        ) {

                            return new Date(
                                b.submittedAt
                            ) -
                            new Date(
                                a.submittedAt
                            );

                        }


                        return (
                            Number(
                                b.attemptNumber
                            ) || 0
                        ) -
                        (
                            Number(
                                a.attemptNumber
                            ) || 0
                        );

                    }
                );


            const latestAttempt =
                sortedAttempts[0];


            const latestPercentage =
                Number(
                    latestAttempt.percentage
                ) || 0;


            const latestStatus =
                latestPercentage >= 50
                    ? "PASS"
                    : "FAIL";


            const statusClass =
                latestPercentage >= 50
                    ? "pass"
                    : "fail";


            const row =
                document.createElement("tr");


            row.innerHTML = `

                <td>
                    ${index + 1}
                </td>


                <td>

                    <strong>
                        ${escapeHTML(
                            student.name
                        )}
                        ${escapeHTML(
                            student.surname
                        )}
                    </strong>

                    <br>

                    <small>
                        Student No:
                        ${escapeHTML(
                            student.studentNumber
                        )}
                    </small>

                </td>


                <td>

                    <span class="attempt-count">

                        ${studentAttempts.length}

                    </span>

                </td>


                <td>

                    <strong>

                        ${latestPercentage.toFixed(1)}%

                    </strong>

                </td>


                <td>

                    <span
                        class="attempt-status ${statusClass}">

                        ${latestStatus}

                    </span>

                </td>


                <td>

                    <button
                        class="view-attempts-btn"
                        data-index="${index}">

                        View

                    </button>

                </td>

            `;


            const viewButton =
                row.querySelector(
                    ".view-attempts-btn"
                );


            viewButton.addEventListener(
                "click",
                function() {

                    viewStudent(index);

                }
            );


            studentsTable.appendChild(row);

        }
    );


    /*
     * Store students for View button.
     */

    window.adminStudents =
        students;
}


/* =========================================================
   VIEW STUDENT
   ========================================================= */

function viewStudent(index) {

    const students =
        window.adminStudents || [];

    const student =
        students[index];

    if (!student) {

        alert("Student information could not be found.");

        return;
    }

    console.log(
        "Selected student:",
        student
    );

    /*
     * Make absolutely sure the student number exists.
     */

    if (
        !student.studentNumber ||
        String(student.studentNumber).trim() === ""
    ) {

        alert(
            "This student does not have a student number."
        );

        console.error(
            "Student without student number:",
            student
        );

        return;
    }

    /*
     * Save the complete student.
     */

    localStorage.setItem(
        "selectedAdminStudent",
        JSON.stringify(student)
    );

    /*
     * Also save the number separately.
     * This gives us a reliable fallback.
     */

    localStorage.setItem(
        "selectedAdminStudentNumber",
        String(student.studentNumber).trim()
    );

    /*
     * Open history page.
     */

    window.location.href = "student-attempts.html";
}


/* =========================================================
   CLEAR ALL ATTEMPTS
   ========================================================= */

if (clearAttemptsButton) {

    clearAttemptsButton.addEventListener("click",
        async function() {

            const confirmed =
                confirm(
                    "Are you sure you want to delete all student attempts?"
                );


            if (!confirmed) {

                return;
            }


            try {

                const response =
                    await fetch(`${API_BASE_URL}/attempts`, {
                            method: "DELETE",
                            credentials: "include",
                            headers: {
                                "Accept": "application/json"
                            }
                        }
                    );


                const data =
                    await response.json();


                if (!response.ok) {

                    throw new Error(
                        data.message ||
                        "Could not delete attempts."
                    );

                }


                alert(
                    "All quiz attempts have been deleted."
                );


                loadAttempts();

            }
            catch (error) {

                console.error(
                    "Delete error:",
                    error
                );


                alert(
                    "Could not delete attempts.\n\n" +
                    error.message
                );

            }

        }
    );

}


/* =========================================================
   SECURITY
   ========================================================= */

function escapeHTML(value) {

    const div =
        document.createElement("div");


    div.textContent =
        String(value ?? "");


    return div.innerHTML;
}


/* =========================================================
   START
   ========================================================= */

loadAttempts();

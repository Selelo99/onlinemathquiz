/* =========================================================
   STUDENT ATTEMPT HISTORY
   ========================================================= */

const API_BASE_URL =
    "http://localhost:8080/api";


/* =========================================================
   ELEMENTS
   ========================================================= */

const studentName =
    document.getElementById("student-name");

const attemptCount =
    document.getElementById("attempt-count");

const attemptsTable =
    document.getElementById(
        "student-attempts-table"
    );


/* =========================================================
   GET SELECTED STUDENT
   ========================================================= */

const selectedStudentJSON =
    localStorage.getItem(
        "selectedAdminStudent"
    );


/* =========================================================
   GET STUDENT NUMBER
   ========================================================= */

let selectedStudent = null;

let studentNumber = "";


/* =========================================================
   PARSE STUDENT
   ========================================================= */

if (selectedStudentJSON) {

    try {

        selectedStudent =
            JSON.parse(
                selectedStudentJSON
            );

    }
    catch (error) {

        console.error(
            "Could not parse selected student:",
            error
        );

    }
}


/* =========================================================
   GET NUMBER
   ========================================================= */

/*
 * First try the complete student object.
 */

if (
    selectedStudent &&
    selectedStudent.studentNumber
) {

    studentNumber =
        String(
            selectedStudent.studentNumber
        ).trim();
}


/*
 * If not available, use the separate
 * localStorage value.
 */

if (!studentNumber) {

    studentNumber =
        String(
            localStorage.getItem(
                "selectedAdminStudentNumber"
            ) || ""
        ).trim();
}


/* =========================================================
   DEBUG
   ========================================================= */

console.log(
    "Selected student:",
    selectedStudent
);

console.log(
    "Selected student number:",
    studentNumber
);


/* =========================================================
   VALIDATE
   ========================================================= */

if (!studentNumber) {

    alert(
        "No student number was selected."
    );

    window.location.href =
        "admin.html";

}


/* =========================================================
   DISPLAY STUDENT
   ========================================================= */

function displayStudent() {

    if (!selectedStudent) {

        studentName.textContent =
            `Student ${studentNumber}`;

        return;
    }


    const name =
        selectedStudent.name || "";

    const surname =
        selectedStudent.surname || "";


    const fullName =
        `${name} ${surname}`.trim();


    studentName.textContent =
        fullName ||
        `Student ${studentNumber}`;
}


/* =========================================================
   LOAD STUDENT ATTEMPTS
   ========================================================= */

async function loadStudentHistory() {

    try {

        displayStudent();

        /*
         * IMPORTANT:
         *
         * Backend endpoint:
         *
         * GET
         * /api/attempts/students/{studentNumber}
         */

        const url = `${API_BASE_URL}/attempts/students/` + `${encodeURIComponent(studentNumber)}`;


        console.log("Fetching:", url);

        const response =
            await fetch(url, {

                method: "GET",
            });


        /*
         * Read response.
         */

        const text = await response.text();

        console.log("HTTP status:", response.status);

        console.log("Response:", text);


        let data = null;


        if (text) {

            try {

                data =
                    JSON.parse(text);

            }
            catch (error) {

                console.error("Invalid JSON:", text);

                throw new Error(
                    "Server returned invalid JSON."
                );

            }

        }


        /*
         * Check HTTP status.
         */

        if (!response.ok) {

            throw new Error(data && data.message ? data.message : `Server returned ${response.status}`);

        }

        /*
         * Backend should return a List.
         */

        if (!Array.isArray(data)) {

            throw new Error(
                "Server did not return a list of attempts."
            );

        }


        console.log(
            "Student attempts:",
            data
        );


        displayAttempts(data);

    }
    catch (error) {

        console.error(
            "Error loading student history:",
            error
        );


        showError(
            error.message
        );

    }
}


/* =========================================================
   DISPLAY ATTEMPTS
   ========================================================= */

function displayAttempts(attempts) {

    attemptsTable.innerHTML = "";


    attemptCount.textContent = `${attempts.length} Attempt` + `${attempts.length === 1 ? "" : "s"}`;


    if (attempts.length === 0) {

        showTableMessage(
            "This student has no quiz attempts."
        );

        return;
    }


    /*
     * Sort newest first.
     */

    const sortedAttempts =
        [...attempts].sort(
            (a, b) => {

                if(a.submittedAt && b.submittedAt){

                    return (new Date(b.submittedAt) - new Date( a.submittedAt));
                }

                return (Number(b.attemptNumber) || 0) - (Number(a.attemptNumber) || 0);

            }
        );


    /*
     * Create rows.
     */

    sortedAttempts.forEach(
        attempt => {

            const attemptNumber =
                attempt.attemptNumber ?? "—";


            const chapter =
                attempt.chapter != null
                    ? `Chapter ${attempt.chapter}`
                    : "—";


            const score =
                attempt.score != null
                    ? attempt.score
                    : 0;


            const totalQuestions =
                attempt.totalQuestions != null
                    ? attempt.totalQuestions
                    : 0;


            const percentage =
                Number(
                    attempt.percentage
                ) || 0;


            const status =
                attempt.status
                    ? String(
                        attempt.status
                    ).toUpperCase()
                    : (
                        percentage >= 50
                            ? "PASS"
                            : "FAIL"
                    );


            const statusClass =
                status === "PASS"
                    ? "pass"
                    : "fail";


            const row = document.createElement("tr");

            row.innerHTML = `

                <td>
                    <strong>
                        Attempt ${escapeHTML(attemptNumber)}
                    </strong>
                </td>

                <td>
                    ${escapeHTML(chapter)}
                </td>

                <td>
                    ${escapeHTML(score)} / ${escapeHTML(totalQuestions)}
                </td>

                <td>
                    <strong>
                        ${percentage.toFixed(1)}%
                    </strong>
                </td>

                <td>

                    <span
                        class="attempt-status ${statusClass}">

                        ${escapeHTML(status)}

                    </span>

                </td>

            `;


            attemptsTable.appendChild(
                row
            );

        }
    );
}


/* =========================================================
   TABLE MESSAGE
   ========================================================= */

function showTableMessage(message) {

    attemptsTable.innerHTML = `

        <tr>

            <td
                colspan="5"
                class="no-attempts">

                ${escapeHTML(message)}

            </td>

        </tr>

    `;
}


/* =========================================================
   ERROR
   ========================================================= */

function showError(message) {

    studentName.textContent =
        "Unable to load attempts";


    attemptCount.textContent =
        "0 Attempts";


    attemptsTable.innerHTML = `

        <tr>

            <td colspan="5" class="no-attempts">

                <strong>Could not load student attempts.</strong>

                <br><br>

                ${escapeHTML(message)}

                <br><br>

                Student Number: <strong>${escapeHTML(studentNumber)}</strong>

                <br><br>

                API:

                <br>

                ${escapeHTML(`${API_BASE_URL}/attempts/students/${studentNumber}`)}

            </td>

        </tr>

    `;
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

if (studentNumber) {

    loadStudentHistory();

}
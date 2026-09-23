
/* =========================================================
   STUDENT DETAILS
   ========================================================= */


/* =========================================================
   ELEMENTS
   ========================================================= */

const studentForm = document.getElementById("student-form");
const formError = document.getElementById("form-error");
const selectedQuizElement = document.getElementById("selected-quiz");


/* =========================================================
   GET SELECTED QUIZ
   ========================================================= */

const selectedQuizChapter = localStorage.getItem("selectedQuizChapter");
const selectedQuizTitle = localStorage.getItem("selectedQuizTitle");


/* =========================================================
   DISPLAY SELECTED QUIZ
   ========================================================= */

if (selectedQuizTitle) {

    selectedQuizElement.textContent = selectedQuizTitle;
}
else if(selectedQuizChapter) {

    selectedQuizElement.textContent = `Mathematics - Chapter ${selectedQuizChapter}`;
}
else{

    selectedQuizElement.textContent = "No quiz selected";
}


/* =========================================================
   STUDENT FORM SUBMISSION
   ========================================================= */

studentForm.addEventListener("submit",
    function(event) {

        event.preventDefault();

        /* -------------------------------------------------
           GET STUDENT DETAILS
        ------------------------------------------------- */
        const name = document.getElementById("name").value.trim();
        const surname = document.getElementById("surname").value.trim();
        const studentNumber = document.getElementById("student-number").value.trim();

        /* -------------------------------------------------
           VALIDATION
        ------------------------------------------------- */
        if (!name || !surname || !studentNumber){

            formError.textContent = "Please complete all fields.";

            return;
        }


        /* -------------------------------------------------
           MAKE SURE A QUIZ WAS SELECTED
        ------------------------------------------------- */
        if (!selectedQuizChapter) {

            formError.textContent = "Please select a quiz chapter first.";

            return;
        }


        /* -------------------------------------------------
           SAVE STUDENT INFORMATION
        ------------------------------------------------- */
        localStorage.setItem("studentName", name);
        localStorage.setItem("studentSurname", surname);
        localStorage.setItem("studentNumber",studentNumber);

        /* -------------------------------------------------
           CLEAR ERROR
        ------------------------------------------------- */

        formError.textContent = "";

        /* -------------------------------------------------
           START QUIZ
        ------------------------------------------------- */
        window.location.href = "quiz.html";
    }
);
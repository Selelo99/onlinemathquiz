/* =========================
   GET REVIEW ELEMENT
========================= */

const reviewList = document.getElementById("review-container");


/* =========================
   CHECK REVIEW ELEMENT
========================= */

if (!reviewList) {

    console.error("ERROR: review-container was not found in the HTML.");
}


/* =========================
   GET SAVED QUIZ DATA
========================= */

const savedAnswers =
    localStorage.getItem("quizAnswers");

const savedQuestions =
    localStorage.getItem("quizQuestions");

const savedScore =
    localStorage.getItem("quizScore");

const savedTotal =
    localStorage.getItem("quizTotal");


/* =========================
   CHECK SAVED DATA
========================= */

if (!savedAnswers || !savedQuestions){

    showNoAttempt();
} 
else{

    displayReview();
}


/* =========================
   NO ATTEMPT
========================= */
function showNoAttempt() {

    if (!reviewList) {
        return;
    }

    reviewList.innerHTML = `

        <div class="no-review">

            <h2>No Quiz Attempt Found</h2>

            <p>You need to complete a quiz before reviewing your answers.</p>

            <a href="quiz-select.html" class="btn btn-primary">Start Quiz</a>

        </div>
    `;

}


/* =========================
   DISPLAY REVIEW
========================= */

function displayReview() {

    /* =========================
       READ SAVED QUESTIONS
    ========================= */

    let quizQuestions;

    let userAnswers;


    try {

        quizQuestions =
            JSON.parse(savedQuestions);

        userAnswers =
            JSON.parse(savedAnswers);

    }

    catch (error) {

        console.error("Error loading saved quiz data:", error);

        showNoAttempt();

        return;
    }


    /* =========================
       VALIDATE DATA
    ========================= */

    if (!Array.isArray(quizQuestions) || !Array.isArray(userAnswers)){

        showNoAttempt();

        return;
    }


    /* =========================
       VARIABLES
    ========================= */
    let correct = 0;
    let incorrect = 0;
    let unanswered = 0;


    /* =========================
       CLEAR REVIEW
    ========================= */
    reviewList.innerHTML = "";


    /* =========================
       CREATE REVIEW CARDS
    ========================= */
    quizQuestions.forEach(
        (question, index) => {

            const studentAnswer =
                userAnswers[index];


            const correctAnswer =
                Number(question.answer);


            /* =========================
               DETERMINE STATUS
            ========================= */
            let status;

            let statusText;


            if (studentAnswer === null || studentAnswer === undefined) {

                status = "unanswered";

                statusText = "Unanswered";

                unanswered++;
            }
            else if(Number(studentAnswer) ===correctAnswer) {

                status = "correct";

                statusText = "Correct";

                correct++;
            }
            else {

                status = "incorrect";

                statusText = "Incorrect";

                incorrect++;
            }


            /* =========================
               GET STUDENT ANSWER
            ========================= */
            let studentAnswerText;


            if (studentAnswer === null || studentAnswer === undefined) {

                studentAnswerText = "Not answered";
            }
            else{

                studentAnswerText = question.options[Number(studentAnswer)];
            }


            /* =========================
               GET CORRECT ANSWER
            ========================= */
            const correctAnswerText = question.options[correctAnswer];


            /* =========================
               CREATE CARD
            ========================= */

            const card =
                document.createElement("article");


            card.className =
                `review-card ${status}`;


            card.innerHTML = `

                <div class="review-card-header">
                    <span class="review-question-number">Question ${index + 1}</span>
                    <span class="review-result ${status}">${statusText}</span>
                </div>

                <h2 class="review-question">${question.question}</h2>

                <div class="review-answer student-answer ${status}">
                    <span>Your Answer</span>
                    <strong>${studentAnswerText}</strong>
                </div>

                <div class="review-answer correct-answer">
                    <span>Correct Answer</span>
                    <strong>${correctAnswerText}</strong>
                </div>
            `;

            reviewList.appendChild(card);

        }
    );


    /* =========================
       SUMMARY
    ========================= */

    const total =
        quizQuestions.length;


    const score =
        savedScore !== null
            ? Number(savedScore)
            : correct;


    const summary =
        document.createElement("div");


    summary.className =
        "review-summary";


    summary.innerHTML = `

        <div class="summary-item">
            <strong>${score}/${total}</strong>
            <span>Score</span>
        </div>

        <div class="summary-item correct-summary">
            <strong>${correct}</strong>
            <span>Correct</span>
        </div>

        <div class="summary-item incorrect-summary">
            <strong>${incorrect}</strong>
            <span>Incorrect</span>
        </div>

        <div class="summary-item unanswered-summary">
            <strong>${unanswered}</strong>
            <span>Unanswered</span>
        </div>
    `;


    reviewList.prepend(summary);

}
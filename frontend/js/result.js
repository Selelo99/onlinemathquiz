/* =========================
   GET SAVED RESULTS
========================= */
const score = Number(localStorage.getItem("quizScore"));
const total = Number(localStorage.getItem("quizTotal"));
const percentage = Number(localStorage.getItem("quizPercentage"));

/* =========================
   CALCULATE RESULTS
========================= */
const correct = score;
const incorrect = total - correct;


/* =========================
   GET HTML ELEMENTS
========================= */
const scoreElement = document.getElementById("score");
const percentageElement = document.getElementById("percentage");
const correctElement = document.getElementById("correct");
const incorrectElement = document.getElementById("incorrect");
const totalElement = document.getElementById("total");
const statusElement = document.getElementById("result-status");


/* =========================
   DISPLAY RESULTS
========================= */

scoreElement.textContent = `${correct}/${total}`;

percentageElement.textContent = `${percentage.toFixed(0)}%`;

correctElement.textContent = correct;

incorrectElement.textContent = incorrect;

totalElement.textContent = total;


/* =========================
   PASS / FAIL
========================= */
const PASS_MARK = 50;

if (percentage >= PASS_MARK) {

    statusElement.textContent = "PASSED";

    statusElement.classList.add("passed");

} 
else {

    statusElement.textContent = "FAILED";

    statusElement.classList.add("failed");
}


/* =========================
   TRY AGAIN
========================= */

const retryButton = document.getElementById("retry-btn");

retryButton.addEventListener("click", () => {
    window.location.href = "quiz.html";
});


/* =========================
   REVIEW ANSWERS
========================= */
const reviewButton = document.getElementById("review-btn");

reviewButton.addEventListener("click", () => {
    window.location.href = "review.html";
});
/* =========================================================
   GET QUIZ ELEMENTS
========================================================= */

const questionNumber =
    document.getElementById("question-number");

const questionText =
    document.getElementById("question-text");

const optionsContainer =
    document.getElementById("options");

const nextButton =
    document.getElementById("next-btn");

const previousButton =
    document.getElementById("previous-btn");

const submitButton =
    document.getElementById("submit-btn");

const timerElement =
    document.getElementById("timer");

const progressBar =
    document.getElementById("progress-bar");

const quizTitleElement =
    document.getElementById("quiz-title");


/* =========================================================
   GET SELECTED CHAPTER
========================================================= */
const selectedChapter = Number(localStorage.getItem("selectedQuizChapter"));


/* =========================================================
   VALIDATE CHAPTER
========================================================= */
if (!selectedChapter) {

    alert("No quiz chapter has been selected.");

    window.location.href = "index.html";
}


/* =========================================================
   SELECT QUESTION BANK
========================================================= */

let questionBank;

if (selectedChapter === 4) {

    questionBank = chapter4Questions;
}
else{

    questionBank = questions;
}


/* =========================================================
   CHECK QUESTION BANK
========================================================= */
if (!questionBank || !Array.isArray(questionBank) || questionBank.length === 0){

    alert("No questions are available for this quiz.");

    window.location.href = "index.html";
}


/* =========================================================
   SELECT QUESTIONS
========================================================= */
const quizQuestions = questionBank.slice(0, quizSettings.numberOfQuestions);


/* =========================================================
   QUIZ VARIABLES
========================================================= */
let currentQuestion = 0;

let userAnswers = new Array(quizQuestions.length).fill(null);


/* =========================================================
   TIMER
========================================================= */
let timeLeft = quizSettings.duration * 60;

let timer;


/* =========================================================
   PREVENT DOUBLE SUBMISSION
========================================================= */
let quizSubmitted = false;


/* =========================================================
   DISPLAY QUIZ TITLE
========================================================= */
const selectedQuizTitle = localStorage.getItem( "selectedQuizTitle");

if (quizTitleElement) {

    quizTitleElement.textContent = selectedQuizTitle || quizSettings.title;
}


/* =========================================================
   DISPLAY QUESTION
========================================================= */
function displayQuestion() {

    if (!quizQuestions || quizQuestions.length === 0){

        return;
    }

    const question = quizQuestions[currentQuestion];


    /* =====================================================
       QUESTION NUMBER
    ===================================================== */
    if (questionNumber){

        questionNumber.textContent = `Question ${currentQuestion + 1} of ${quizQuestions.length}`;
    }


    /* =====================================================
       QUESTION TEXT
    ===================================================== */
    if (questionText) {

        questionText.textContent = question.question;
    }


    /* =====================================================
       CLEAR OPTIONS
    ===================================================== */

    if (optionsContainer) {

        optionsContainer.innerHTML = "";
    }


    /* =====================================================
       CREATE OPTIONS
    ===================================================== */
    question.options.forEach(
        (option, index) => {

            const optionLabel =
                document.createElement("label");

            optionLabel.classList.add(
                "option"
            );


            optionLabel.innerHTML = `
                <input type="radio" name="answer" value="${index}">
                <span>${option}</span>
            `;

            optionsContainer.appendChild(optionLabel);


            /* =============================================
               RESTORE PREVIOUS ANSWER
            ============================================= */
            if(userAnswers[currentQuestion] === index){

                const radio = optionLabel.querySelector( "input");

                radio.checked = true;
            }
        }
    );


    /* =====================================================
       UPDATE PROGRESS
    ===================================================== */
    updateProgress();


    /* =====================================================
       NEXT / SUBMIT BUTTON
    ===================================================== */
    if(currentQuestion === quizQuestions.length - 1){

        if (nextButton){

            nextButton.style.display = "none";
        }


        if(submitButton){

            submitButton.style.display = "inline-block";
        }

    }
    else{

        if(nextButton) {

            nextButton.style.display = "inline-block";
        }

        if (submitButton) {

            submitButton.style.display = "none";
        }
    }


    /* =====================================================
       PREVIOUS BUTTON
    ===================================================== */

    if (previousButton) {

        if (currentQuestion === 0) {

            previousButton.style.display = "none";
        }
        else {

            previousButton.style.display = "inline-block";
        }
    }
}


/* =========================================================
   SAVE CURRENT ANSWER
========================================================= */
function saveCurrentAnswer() {

    const selectedOption = document.querySelector( 'input[name="answer"]:checked');

    if (selectedOption) {

        userAnswers[currentQuestion] = Number(selectedOption.value);
    }
}


/* =========================================================
   PREVIOUS QUESTION
========================================================= */
if (previousButton) {

    previousButton.addEventListener("click",
        function () {

            saveCurrentAnswer();

            if (currentQuestion > 0) {

                currentQuestion--;

                displayQuestion();
            }
        }
    );
}


/* =========================================================
   NEXT QUESTION
========================================================= */
if (nextButton) {

    nextButton.addEventListener("click",
        function () {

            saveCurrentAnswer();

            if (currentQuestion < quizQuestions.length - 1) {

                currentQuestion++;

                displayQuestion();
            }
        }
    );
}


/* =========================================================
   SUBMIT QUIZ
========================================================= */
if (submitButton) {

    submitButton.addEventListener("click",
        function () {

            saveCurrentAnswer();

            finishQuiz();
        }
    );
}


/* =========================================================
   FINISH QUIZ
========================================================= */
async function finishQuiz() {

    /* =====================================================
       PREVENT DOUBLE SUBMISSION
    ===================================================== */
    if (quizSubmitted) {

        return;
    }

    quizSubmitted = true;

    /* =====================================================
       STOP TIMER
    ===================================================== */

    clearInterval(timer);

    /* =====================================================
       CALCULATE SCORE
    ===================================================== */

    let score = 0;

    quizQuestions.forEach(
        (question, index) => {

            if (userAnswers[index] !== null && Number(userAnswers[index]) === Number( question.answer)) {

                score++;
            }
        }
    );


    /* =====================================================
       TOTAL QUESTIONS
    ===================================================== */
    const total = quizQuestions.length;


    /* =====================================================
       PERCENTAGE
    ===================================================== */
    const percentage = total > 0 ? (score / total) * 100 : 0;

    /* =====================================================
       STATUS
    ===================================================== */
    const status = percentage >= 50 ? "PASS" : "FAIL";

    /* =====================================================
       STUDENT NUMBER
    ===================================================== */

    const studentName = localStorage.getItem("studentName");


    const studentSurname = localStorage.getItem("studentSurname");

    const studentNumber = localStorage.getItem("studentNumber");

    if (!studentName || !studentSurname || !studentNumber){

        quizSubmitted = false;

        alert("Student information is missing. Please return to the student details page.");

        window.location.href = "student.html";

        return;
    }


    /* =====================================================
       QUIZ TITLE
    ===================================================== */
    const quizTitle = localStorage.getItem("selectedQuizTitle") || quizSettings.title;


    /* =====================================================
       BUILD ANSWERS
    ===================================================== */
    const answers =
        quizQuestions.map(
            (question, index) => {

                const selectedIndex = userAnswers[index];

                let selectedAnswer = null;

                if (selectedIndex !== null &&selectedIndex !== undefined){

                    selectedAnswer = question.options[selectedIndex];
                }

                const correctAnswer = question.options[question.answer];

                return {

                    questionNumber: index + 1,
                    questionText: question.question,
                    selectedAnswer: selectedAnswer,
                    correctAnswer: correctAnswer,
                    correct: selectedIndex !== null && Number(selectedIndex) === Number(question.answer)
                };
            }
        );


    /* =====================================================
       BUILD REQUEST
    ===================================================== */

    const quizData = {

        name: studentName,
        surname: studentSurname,
        studentNumber: studentNumber,
        chapter: selectedChapter,
        quizTitle: quizTitle,
        totalQuestions: total,
        score: score,
        percentage: percentage,
        status: status,
        answers: answers
    };


    console.log("Sending quiz data:", quizData);


    /* =====================================================
       SEND TO SPRING BOOT
    ===================================================== */
   try{

        const response = await fetch("http://onlinemathquiz-production.up.railway.app/api/attempts",
            {
                method: "POST",

                headers: {"Content-Type": "application/json"},

                body: JSON.stringify(quizData)
            }
        );

        const responseText = await response.text();

        let responseData;

        try {

            responseData = JSON.parse( responseText);
        }
        catch{

            responseData = responseText;
        }


        if (!response.ok) {

            console.error("Server returned error:", responseData);

            throw new Error(typeof responseData === "string" ? responseData : responseData.message || `Server returned ${response.status}`);
        }


        console.log("Quiz attempt saved successfully:", responseData);


        // =====================================================
        // SAVE RESULT
        // =====================================================
        localStorage.setItem(
            "quizScore",
            score
        );


        localStorage.setItem(
            "quizTotal",
            total
        );


        localStorage.setItem(
            "quizPercentage",
            percentage
        );


        localStorage.setItem(
            "quizAnswers",
            JSON.stringify(
                userAnswers
            )
        );


        localStorage.setItem(
            "quizQuestions",
            JSON.stringify(
                quizQuestions
            )
        );


        if (
            responseData &&
            responseData.id
        ) {

            localStorage.setItem(
                "lastAttemptId",
                responseData.id
            );
        }


        if (
            responseData &&
            responseData.attemptNumber
        ) {

            localStorage.setItem(
                "lastAttemptNumber",
                responseData.attemptNumber
            );
        }


        // =====================================================
        // GO TO RESULT
        // =====================================================

        window.location.href = "result.html";

    }
    catch (error) {

        console.error("Could not save quiz attempt:", error);

        quizSubmitted = false;


        alert("Could not save quiz attempt.\n\n" + error.message);
    }
}


/* =========================================================
   TIMER
========================================================= */

function updateTimer() {

    if (!timerElement) {

        return;
    }


    const minutes = Math.floor(timeLeft / 60);

    const seconds = timeLeft % 60;

    timerElement.textContent = `${String(minutes).padStart(2, "0")}:${String(seconds).padStart(2, "0")}`;


    /* =====================================================
       TIME FINISHED
    ===================================================== */
    if (timeLeft <= 0) {

        clearInterval(timer);

        finishQuiz();

        return;
    }


    timeLeft--;
}


/* =========================================================
   START TIMER
========================================================= */
timer = setInterval(updateTimer, 1000);


/* =========================================================
   UPDATE PROGRESS
========================================================= */
function updateProgress() {

    if (!progressBar || quizQuestions.length === 0){

        return;
    }


    const progress =((currentQuestion + 1) / quizQuestions.length) * 100;

    progressBar.style.width = `${progress}%`;
}


/* =========================================================
   BACK
========================================================= */

function backh() {

    window.history.back();
}


/* =========================================================
   START QUIZ
========================================================= */

displayQuestion();

updateTimer();
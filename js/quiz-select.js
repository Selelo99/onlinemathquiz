function selectQuiz(chapter) {

    if (chapter === 3) {

        localStorage.setItem("selectedQuizChapter", "3");

        localStorage.setItem("selectedQuizTitle","Mathematics — Chapter 3");
    }


    if (chapter === 4) {

        localStorage.setItem("selectedQuizChapter", "4");

        localStorage.setItem("selectedQuizTitle","Mathematics — Chapter 4");
    }


    /*
     * Go to student information
     */

    window.location.href = "student.html";

}
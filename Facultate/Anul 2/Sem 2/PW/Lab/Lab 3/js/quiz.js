const quizData = [
    {
        exercise: "Full Planche",
        question: "Ce grup muscular este cel mai solicitat in Full Planche?",
        options: ["Biceps", "Umeri si piept", "Picioare", "Spate"],
        correct: 1
    },
    {
        exercise: "Front Lever",
        question: "Cat timp trebuie sa tii un Front Lever pentru a fi considerat complet?",
        options: ["1 secunda", "3 secunde", "5 secunde", "10 secunde"],
        correct: 3
    },
    {
        exercise: "One Arm Handstand",
        question: "Care este prerequisitul principal pentru One Arm Handstand?",
        options: ["Muscle Up", "Handstand", "Front Lever", "Planche"],
        correct: 1
    },
    {
        exercise: "Iron Cross",
        question: "Pe ce aparat se executa Iron Cross?",
        options: ["Bara fixa", "Paralele", "Inele", "Sol"],
        correct: 2
    }
];

let currentQuiz = 0;
let score = 0;
let answered = false;

function showQuiz(index) {
    const data = quizData[index];
    answered = false;

    document.getElementById("quiz-exercise").textContent = data.exercise;
    document.getElementById("quiz-question").textContent = data.question;

    const optionsContainer = document.getElementById("quiz-options");
    optionsContainer.innerHTML = "";

    data.options.forEach((option, i) => {
        const btn = document.createElement("button");
        btn.textContent = option;
        btn.classList.add("quiz-option");
        btn.addEventListener("click", () => selectAnswer(i));
        optionsContainer.appendChild(btn);
    });

    document.getElementById("quiz-feedback").textContent = "";
    document.getElementById("quiz-next").style.display = "none";
    document.getElementById("quiz-progress").textContent = `Intrebarea ${index + 1} din ${quizData.length}`;
}

function selectAnswer(index) {

    answered = true;

    const correct = quizData[currentQuiz].correct;
    const buttons = document.querySelectorAll(".quiz-option");
    const feedback = document.getElementById("quiz-feedback");

    buttons.forEach((btn, i) => {
        btn.disabled = true;
        if (i === correct) btn.classList.add("correct");
        else if (i === index) btn.classList.add("wrong");
    });

    if (index === correct) {
        score++;
        feedback.textContent = "corect fra";
        feedback.style.color = "green";
    } else {
        feedback.textContent = `nu e bine bro, asa e: ${quizData[currentQuiz].options[correct]}`;
        feedback.style.color = "red";
    }

    document.getElementById("quiz-next").style.display = "inline-block";
}

function nextQuestion() {
    currentQuiz++;

    if (currentQuiz < quizData.length) {
        showQuiz(currentQuiz);
    } else {
        showResult();
    }
}

function showResult() {
    const container = document.getElementById("quiz-container");
    container.innerHTML = `
        <h3>Quiz terminat!</h3>
        <p>Scor final: <strong>${score} / ${quizData.length}</strong></p>
        <p>${getResultMessage()}</p>
        <button onclick="restartQuiz()">Incearca din nou</button>
    `;
}

function getResultMessage() {
    if (score === quizData.length) return "esti regele";
    if (score >= quizData.length / 2) return "nu prea le ai";
    return "nu le ai deloc";
}

function restartQuiz() {
    currentQuiz = 0;
    score = 0;

    document.getElementById("quiz-container").innerHTML = `
        <p id="quiz-progress"></p>
        <h3 id="quiz-exercise"></h3>
        <p id="quiz-question"></p>
        <div id="quiz-options"></div>
        <p id="quiz-feedback"></p>
        <button id="quiz-next" onclick="nextQuestion()" style="display:none">Urmatoarea intrebare</button>
    `;

    showQuiz(0);
}

showQuiz(0);
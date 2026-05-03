$(function () {

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
            correct: 1
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

    const $container = $("#quiz-container");

    function showQuiz(index) {
        const data = quizData[index];

        $("#quiz-exercise").text(data.exercise);
        $("#quiz-question").text(data.question);

        const $options = $("#quiz-options").empty();

        $.each(data.options, function (i, option) {
            $("<button>")
                .text(option)
                .addClass("quiz-option")
                .attr("data-index", i)
                .appendTo($options);
        });

        $("#quiz-feedback").text("").css("color", "");
        $("#quiz-next").hide();
        $("#quiz-progress").text(`Intrebarea ${index + 1} din ${quizData.length}`);
    }

    $container.on("click", ".quiz-option", function () {

        const selected = $(this).data("index");
        const correct = quizData[currentQuiz].correct;

        $(".quiz-option").prop("disabled", true);

        $(".quiz-option").each(function (i) {
            if (i === correct) $(this).addClass("correct");
            else if (i === selected) $(this).addClass("wrong");
        });

        if (selected === correct) {
            score++;
            $("#quiz-feedback").text("corect fra").css("color", "green");
        } else {
            $("#quiz-feedback")
                .text(`nu e bine bro, asa e: ${quizData[currentQuiz].options[correct]}`)
                .css("color", "red");
        }

        $("#quiz-next").show();
    });

    window.nextQuestion = function () {
        currentQuiz++;

        if (currentQuiz < quizData.length) {
            showQuiz(currentQuiz);
        } else {
            showResult();
        }
    };

    function showResult() {
        $container.html(`
            <h3>Quiz terminat!</h3>
            <p>Scor final: <strong>${score} / ${quizData.length}</strong></p>
            <p>${getResultMessage()}</p>
            <button id="restart-btn">Incearca din nou</button>
        `);
    }

    function getResultMessage() {
        if (score === quizData.length) return "esti regele";
        if (score >= quizData.length / 2) return "nu prea le ai";
        return "nu le ai deloc";
    }

    $container.on("click", "#restart-btn", function () {
        currentQuiz = 0;
        score = 0;

        $container.html(`
            <p id="quiz-progress"></p>
            <h3 id="quiz-exercise"></h3>
            <p id="quiz-question"></p>
            <div id="quiz-options"></div>
            <p id="quiz-feedback"></p>
            <button id="quiz-next" style="display:none">Urmatoarea intrebare</button>
        `);

        showQuiz(0);
    });

    $container.on("click", "#quiz-next", function () {
        nextQuestion();
    });

    showQuiz(0);

});
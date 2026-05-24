<?php
require_once '../backend/login/check_auth.php';
check_auth();
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calisthenics Core - Home</title>
    <link rel="stylesheet" href="../styling/base.css">
<!--    <link rel="stylesheet" href="../styling/verticalNav.css">-->
    <link rel ="stylesheet" href="../styling/horizontalNav.css">
    <link rel="stylesheet" href="../styling/responsive.css">
    <link rel="stylesheet" href="../styling/animation.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
<header>
    <h1>Calisthenics Core</h1>
    <img src="../assets/logo.png" alt="Logo Calisthenics" title="Calisthenics" id="logo">
</header>
<nav>
    <ul class="menu">
        <li><a href="home.php" target="_self">Home</a></li>
        <?php if (isset($_SESSION['user_id'])): ?>
            <li>
                <a href="../backend/login/logout.php">Log out</a>
            </li>
        <?php else: ?>
        <li>
            <a href="#">Login</a>
            <div class="dropdown">
                <a href="../login/login.php">Autentificare</a>
                <a href="../login/creare_cont.php">Creare cont</a>
            </div>
        </li>
        <?php endif; ?>


        <li><a href="../programe/programe.php" target="_self">Programe</a></li>
        <li><a href="../formular/formular.php" target="_self">Formular</a></li>
    </ul>
</nav>
<section>
    <h2>Despre noi</h2>
    <div class="carduri">
        <div class="card">
            <div class="card-icon">💪</div>
            <h3>Forta</h3>
            <p>Dezvolta-ti forta prin exercitii de calisthenics progresive, de la basic la avansat.</p>
        </div>
        <div class="card">
            <div class="card-icon">🧘</div>
            <h3>Mobilitate</h3>
            <p>Imbunatateste flexibilitatea si mobilitatea articulatiilor prin miscare controlata.</p>
        </div>
        <div class="card">
            <div class="card-icon">🏃</div>
            <h3>Rezistenta</h3>
            <p>Creste rezistenta cardiovasculara si musculara cu antrenamente complete.</p>
        </div>
    </div>
    <div id="slider">
        <a id="slide-link" href="" target="_blank">
            <p id="slide-text"></p>
        </a>
        <img id="slide-image" src="" alt="" width="300" />

        <br />

        <button class = "slider-button" id = "prev-slide-btn">⬅️</button>
        <button class = "slider-button" id = "next-slide-btn">➡️</button>
    </div>

    <h3>Quiz Calisthenics</h3>
    <div id="quiz-container">
        <p id="quiz-progress"></p>
        <h3 id="quiz-exercise"></h3>
        <p id="quiz-question"></p>
        <div id="quiz-options"></div>
        <p id="quiz-feedback"></p>
        <button id="quiz-next" style="display:none">Urmatoarea intrebare</button>
    </div>
</section>
<footer>
    <div>
        <p>
            <span title="Copyright">© 2026 Calisthenics Core</span>
            Contact: <a href="mailto:manudaniel@calisthenicspro.ro" target="_self">manudaniel@calisthenicspro.ro</a>
        </p>
    </div>
</footer>
<script src="../js/dropdown.js"></script>
<script src="../js/carousel.js"></script>
<script type="module" src="../js/quiz.js"></script>
</body>
</html>

<?php
session_start();
?>

<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calisthenics Core - Login</title>
    <link rel="stylesheet" href="../styling/base.css">
<!--    <link rel="stylesheet" href="../styling/verticalNav.css">-->
    <link rel ="stylesheet" href="../styling/horizontalNav.css">
    <link rel="stylesheet" href="../styling/responsive.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

</head>

<body>

<header>
    <h1>Calisthenics Core</h1>
    <img src="../assets/logo.png" alt="Logo Calisthenics" title="Calisthenics" id="logo">
</header>


<nav>
    <ul class="menu">
        <li><a href="../home/home.php" target="_self">Home</a></li>
        <?php if (isset($_SESSION['user_id'])): ?>
        <li>
            <a href="../backend/login/logout.php">Log out</a>
        </li>
        <?php else: ?>
        <li>
            <a href="#">Login</a>
            <div class="dropdown">
                <a href="login.php">Autentificare</a>
                <a href="../login/creare_cont.php">Creare cont</a>
            </div>
        </li>
        <?php endif; ?>


        <li><a href="../programe/programe.php" target="_self">Programe</a></li>
        <li><a href="../formular/formular.php" target="_self">Formular</a></li>
    </ul>
</nav>


<section>
    <h2>Autentificare utilizator</h2>

    <form id = "loginForm" name="loginForm" novalidate action = "../backend/login/login.php" method = "POST">
        <fieldset style = "width:18vw;text-align:center;">
            <legend><b>Login</b></legend>
            <div>
                <label for="username">Nume utilizator:</label>
                <input type="text" name="username" id="username" size="30" maxlength="20">
            </div>
            <div>
                <label for="password">Parola:</label>
                <input type="password" name="password" id="password" size="30" maxlength="20">
            </div>
            <div class="captcha-box">
                <label>Verificare anti-robot:</label>
                <br>
                <img src="../backend/captcha/captcha.php" alt="CAPTCHA" style="border: 1px solid #333; margin-bottom: 5px;">
                <br>
                <label>
                    <input type="text" name="captcha_input" placeholder="Introdu codul din imagine" required>
                </label>
            </div>
            <input type="submit" value="Login" style = "width:268px;">
            <div class="remember-me">
                <input type="checkbox" name="remember" id="remember">
                <label for="remember">Remember Me</label>
            </div>
        </fieldset>
        <p>Nu ai un cont? <a href="creare_cont.php">Creeaza unul!</a></p>
    </form>


</section>


<footer>
    <div>
        <p>
            <span title="Copyright">© 2026 Calisthenics Core</span>
            Contact: <a href="mailto:manudaniel@calisthenicspro.ro" target="_self">manudaniel@calisthenicspro.ro</a>
        </p>
    </div>
</footer>
<script src="../js/validate_login.js"></script>
<script src="../js/dropdown.js"></script>
</body>
</html>
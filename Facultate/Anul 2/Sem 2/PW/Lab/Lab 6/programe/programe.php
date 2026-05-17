<?php
session_start();
?>

<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Programe Calisthenics</title>
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
                <a href="../login/login.php">Autentificare</a>
                <a href="../login/creare_cont.php">Creare cont</a>
            </div>
        </li>
        <?php endif; ?>


        <li><a href="programe.php" target="_self">Programe</a></li>
        <li><a href="../formular/formular.php" target="_self">Formular</a></li>
    </ul>
</nav>


<section>
    <h2>Programe saptamanale</h2>

    <div class="table-container">
        <table id="program-table"></table>
    </div>
    <div class="table-container">
        <table id="program-table-vertical"></table>
    </div>

    <h3>Tipuri de programe</h3>
    <ol type="A" start="1" class = "collapsible">
        <li>Full Body
            <ol>
                <li>Flotari</li>
                <li>Genuflexiuni</li>
            </ol>
        </li>
        <li>Upper Body
            <ol>
                <li>Tractiuni</li>
                <li>Dips</li>
            </ol>
        </li>
        <li>Lower Body
            <ol>
                <li>Genoflexiuni</li>
                <li>Fandari</li>
            </ol>
        </li>
    </ol>


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
<script type = "module" src="../js/table.js" ></script>
<script src = "../js/collapsible.js"></script>
</body>
</html>
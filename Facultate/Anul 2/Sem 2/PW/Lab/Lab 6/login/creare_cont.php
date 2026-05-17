<?php
session_start();
?>
<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calisthenics Core - Creare cont</title>
    <link rel="stylesheet" href="../styling/base.css">
    <link rel="stylesheet" href="../styling/verticalNav.css">
<!--    <link rel ="stylesheet" href="../styling/horizontalNav.css">-->
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
    <h2>Creare cont utilizator</h2>

    <form id = "formularCont" name="formularCont" action="#" method="POST" novalidate>
        <fieldset>
            <legend><b>Date cont</b></legend>

            <div>
                <label for="username">Nume utilizator:</label>
                <input type="text" id="username" name="username" size="30" maxlength="20" required>
            </div>

            <div>
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" size="30" required>
            </div>

            <div>
                <label for="parola">Parola:</label>
                <input type="password" id="parola" name="parola" size="30" maxlength="20" required>
            </div>

            <div>
                <label for="confirmare">Confirmare parola:</label>
                <input type="password" id="confirmare" name="confirmare" size="30" maxlength="20" required>
            </div>

            <div>
                <label for="varsta">Varsta:</label>
                <input type="number" id="varsta" name="varsta" min="1" max="100" step="1" required>
            </div>

            <div>
                <label for="data_nasterii">Data Nasterii:</label>
                <input type="date" id="data_nasterii" name="data_nasterii" required>
                <small class="field-hint" id="birth-hint">Completeaza varsta pentru a restrange intervalul acceptat.</small>
            </div>

            <div>
                <label for="judet">Judet:</label>
                <select id="judet" name="judet" required>
                    <option value="">Alege judetul</option>
                    <option value="Cluj">Cluj</option>
                    <option value="Bucuresti">Bucuresti</option>
                    <option value="Iasi">Iasi</option>
                </select>
                <small class="field-hint">Utilizatori din judet: <span id="judet-users">-</span></small>
            </div>

            <div>
                <label for="oras">Oras:</label>
                <select id="oras" name="oras" required disabled>
                    <option value="">Alege mai intai judetul</option>
                </select>
            </div>

            <div>
                <span>Nivel pregatire:</span>
                <input type="radio" id="incepator" name="nivel" value="incepator" checked>
                <label for="incepator">Incepator</label>

                <input type="radio" id="intermediar" name="nivel" value="intermediar">
                <label for="intermediar">Intermediar</label>

                <input type="radio" id="avansat" name="nivel" value="avansat">
                <label for="avansat">Avansat</label>
            </div>

            <div>
                <span>Obiective:</span>
                <input type="checkbox" id="forta" name="forta" value="forta">
                <label for="forta">Forta</label>

                <input type="checkbox" id="rezistenta" name="rezistenta" value="rezistenta">
                <label for="rezistenta">Rezistenta</label>

                <input type="checkbox" id="mobilitate" name="mobilitate" value="mobilitate">
                <label for="mobilitate">Mobilitate</label>
            </div>

            <div>
                <label for="tara">Tara:</label>
                <input type="text" id="tara" value="Romania" readonly>
            </div>

            <div>
                <label for="codpromo">Cod promo:</label>
                <input type="text" id="codpromo" value="Cod promo indisponibil" disabled>
            </div>

            <div>
                <label for="program">Tip program dorit:</label>
                <select id="program" name="program" multiple size="3" required>
                    <option value="fullbody" selected>Full Body</option>
                    <option value="upper">Upper Body</option>
                    <option value="lower">Lower Body</option>
                </select>
            </div>

            <div>
                <label for="descriere">Descriere obiective:</label>
                <textarea id="descriere" name="descriere" cols="40" rows="4" required></textarea>
            </div>

            <div>
                <input type="submit" value="Creeaza cont">
            </div>
        </fieldset>
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
<script src="../js/register_data.js"></script>
<script src="../js/validate_register.js"></script>
<script src="../js/dropdown.js"></script>
</body>
</html>
<?php
require_once '../backend/login/check_auth.php';
check_auth();
if ($_SESSION["role"] != "ADMIN") {
    header("Location: ../home/home.php");
    exit();
}
require_once '../backend/config/db.php';
/**@var $pdo */
$stmt = $pdo->prepare("SELECT * FROM planuri_antrenament WHERE user_id = ? ORDER BY data_creare DESC LIMIT 1");
$stmt->execute([$_SESSION['user_id']]);
$last_plan = $stmt->fetch(PDO::FETCH_ASSOC);
if ($last_plan === false) {
    $last_plan = array();
}
?>

<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calisthenics Core - Formular</title>
    <link rel="stylesheet" href="../styling/base.css">
    <link rel="stylesheet" href="../styling/horizontalNav.css">
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


        <li><a href="../programe/programe.php" target="_self">Programe</a></li>
        <li><a href="../formular/formular.php" target="_self">Formular</a></li>
    </ul>
</nav>

<section>
    <h2>Formular rapid de antrenament</h2>
    <p>Completeaza cele 8 campuri pentru un plan scurt si clar.</p>

    <form id = "formular" name="formular" action="../backend/formular/procesare_formular.php" method="post" novalidate enctype="multipart/form-data">
        <fieldset>
            <legend><b>Profil de baza</b></legend>

            <div>
                <label for="nume">Nume complet:</label>
                <input type="text" id="nume" name="nume" maxlength="40"
                       value="<?php echo isset($last_plan['nume_complet']) ? htmlspecialchars($last_plan['nume_complet']) : ''; ?>" required>
            </div>

            <div>
                <label for="email">Email:</label>
                <input type="email" id="email" name="email"
                       value="<?php echo isset($last_plan['email']) ? htmlspecialchars($last_plan['email']) : ''; ?>" required>
            </div>

            <div>
                <label for="varsta">Varsta:</label>
                <input type="number" id="varsta" name="varsta" min="1" max="100"
                       value="<?php echo isset($last_plan['varsta']) ? htmlspecialchars($last_plan['varsta']) : ''; ?>" required>
            </div>

            <div>
                <label for="nivel">Nivel pregatire:</label>
                <select id="nivel" name="nivel" required>
                    <option value="">Alege nivelul</option>
                    <option value="incepator" <?php echo (isset($last_plan['nivel_pregatire']) && $last_plan['nivel_pregatire'] == 'incepator') ? 'selected' : ''; ?>>Incepator</option>
                    <option value="intermediar" <?php echo (isset($last_plan['nivel_pregatire']) && $last_plan['nivel_pregatire'] == 'intermediar') ? 'selected' : ''; ?>>Intermediar</option>
                    <option value="avansat" <?php echo (isset($last_plan['nivel_pregatire']) && $last_plan['nivel_pregatire'] == 'avansat') ? 'selected' : ''; ?>>Avansat</option>
                </select>
            </div>

            <div>
                <label for="obiectiv">Obiectiv principal:</label>
                <select id="obiectiv" name="obiectiv" required>
                    <option value="">Alege obiectivul</option>
                    <option value="forta" <?php echo (isset($last_plan['obiectiv_principal']) && $last_plan['obiectiv_principal'] == 'forta') ? 'selected' : ''; ?>>Forta</option>
                    <option value="masa" <?php echo (isset($last_plan['obiectiv_principal']) && $last_plan['obiectiv_principal'] == 'masa') ? 'selected' : ''; ?>>Masa musculara</option>
                    <option value="mobilitate" <?php echo (isset($last_plan['obiectiv_principal']) && $last_plan['obiectiv_principal'] == 'mobilitate') ? 'selected' : ''; ?>>Mobilitate</option>
                    <option value="rezistenta" <?php echo (isset($last_plan['obiectiv_principal']) && $last_plan['obiectiv_principal'] == 'rezistenta') ? 'selected' : ''; ?>>Rezistenta</option>
                </select>
            </div>

            <div>
                <label for="data-start">Data inceperii:</label>
                <input type="date" id="data-start" name="data-start"
                       value="<?php echo isset($last_plan['data_inceperii']) ? htmlspecialchars($last_plan['data_inceperii']) : ''; ?>" required>
            </div>

            <div>
                <label for="observatii">Observatii:</label>
                <textarea id="observatii" name="observatii" rows="4" cols="32" maxlength="250" required><?php echo isset($last_plan['observatii']) ? htmlspecialchars($last_plan['observatii']) : ''; ?></textarea>
            </div>

            <div style="margin-top: 15px; padding: 10px; background: rgba(255,255,255,0.1); border-radius: 8px;">
                <label for="fisier_antrenament"><b>Atasează plan antrenament (PDF/JPG):</b></label>
                <input type="file" id="fisier_antrenament" name="fisier_antrenament">
            </div>

            <div style="margin-top: 20px;">
                <input type="submit" value="Trimite formularul" style="width: 100%; padding: 10px; cursor: pointer;">
            </div>

        </fieldset>
    </form>
    <div id = "widget-tema" style = "display:none;border: 1px solid black;border-radius:20px;margin-top:10px;">
        <p id = "widget-paragraf"></p>
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

<script src="../js/validate_formular.js"></script>
<script src="../js/dropdown.js"></script>
</body>
</html>

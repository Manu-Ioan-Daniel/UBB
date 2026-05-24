<?php
session_start();
require_once __DIR__ . '/../backend/programe/edit_form_data.php';
?>
<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editare programe - Calisthenics Core</title>
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
        <li><a href="programe.php" target="_self">Programe</a></li>
        <li><a href="edit.php" target="_self">Editare</a></li>
        <li><a href="edit_jquery.php" target="_self">Editare jQ</a></li>
        <li><a href="../formular/formular.php" target="_self">Formular</a></li>
    </ul>
</nav>

<section>
    <h2>Editare programe saptamanale (Cerinta 5)</h2>

    <?php if ($editError !== null): ?>
        <p class="edit-message edit-message-error"><?php echo htmlspecialchars($editError); ?></p>
    <?php else: ?>
        <form id="edit-entity-form" class="edit-entity-form" novalidate>
            <fieldset>
                <legend>Entitate programe_saptamanale</legend>

                <div>
                    <label for="edit-entity-id">Id (cheie):</label>
                    <select id="edit-entity-id" name="id" required>
                        <option value="">-- selecteaza id --</option>
                        <?php foreach ($editIds as $editId): ?>
                            <option value="<?php echo (int) $editId; ?>"><?php echo (int) $editId; ?></option>
                        <?php endforeach; ?>
                    </select>
                </div>

                <div>
                    <label for="edit-ziua">Ziua:</label>
                    <input type="text" id="edit-ziua" name="ziua" maxlength="20" disabled required>
                </div>

                <div>
                    <label for="edit-exercitiu">Exercitiu:</label>
                    <input type="text" id="edit-exercitiu" name="exercitiu" maxlength="80" disabled required>
                </div>

                <div>
                    <label for="edit-seturi">Seturi:</label>
                    <input type="number" id="edit-seturi" name="seturi" min="1" disabled required>
                </div>

                <div>
                    <label for="edit-repetari">Repetari:</label>
                    <input type="number" id="edit-repetari" name="repetari" min="1" disabled required>
                </div>

                <div>
                    <label for="edit-focus">Focus:</label>
                    <input type="text" id="edit-focus" name="focus" maxlength="50" disabled required>
                </div>

                <div class="edit-form-actions">
                    <button type="submit" id="edit-entity-save" disabled>Save</button>
                </div>

                <p id="edit-entity-message" class="edit-message" aria-live="polite"></p>
            </fieldset>
        </form>
    <?php endif; ?>
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
<script src="../js/edit-entity.js"></script>
</body>
</html>

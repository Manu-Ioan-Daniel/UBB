<?php
session_start();
require_once __DIR__ . '/../backend/programe/server_pagination.php';
?>

<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Programe Calisthenics</title>
    <link rel="stylesheet" href="../styling/base.css">
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
        <li><a href="edit.php" target="_self">Editare</a></li>
        <li><a href="edit_jquery.php" target="_self">Editare jQ</a></li>
        <li><a href="../formular/formular.php" target="_self">Formular</a></li>
    </ul>
</nav>


<section>
    <h2>Programe saptamanale</h2>

    <h3>Program paginat - JSON (Cerinta 1)</h3>
    <p id="pagination-status"></p>
    <div class="table-container" id="pagination-wrapper" data-k="3">
        <table id="program-table-paginated"></table>
    </div>
    <div class="pagination-controls">
        <button type="button" id="btn-prev-k" disabled>Previous k</button>
        <button type="button" id="btn-next-k">Next k</button>
    </div>

    <h3>Program paginat - XML (Cerinta 2)</h3>
    <p id="pagination-status-xml"></p>
    <div class="table-container" id="pagination-wrapper-xml" data-k="3">
        <table id="program-table-paginated-xml"></table>
    </div>
    <div class="pagination-controls">
        <button type="button" id="btn-prev-k-xml" disabled>Previous k</button>
        <button type="button" id="btn-next-k-xml">Next k</button>
    </div>

    <h3>Program paginat - jQuery JSON (Cerinta 3)</h3>
    <p id="pagination-status-jq"></p>
    <div class="table-container" id="pagination-wrapper-jq" data-k="3">
        <table id="program-table-paginated-jq"></table>
    </div>
    <div class="pagination-controls">
        <button type="button" id="btn-prev-k-jq" disabled>Previous k</button>
        <button type="button" id="btn-next-k-jq">Next k</button>
    </div>

    <h3 id="cerinta-4">Program paginat - server (Cerinta 4)</h3>
    <?php if ($serverError !== null): ?>
        <p class="pagination-error"><?php echo htmlspecialchars($serverError); ?></p>
    <?php else: ?>
        <p class="pagination-status-server">
            Inregistrari <?php echo (int) $serverFrom; ?>-<?php echo (int) $serverTo; ?>
            din <?php echo (int) $serverTotal; ?>
            (cate <?php echo (int) $serverK; ?> pe pagina, server-side)
        </p>
        <div class="table-container">
            <table id="program-table-paginated-server">
                <thead>
                <tr>
                    <?php foreach ($serverColumns as $column): ?>
                        <th><?php echo htmlspecialchars(server_column_label($column)); ?></th>
                    <?php endforeach; ?>
                </tr>
                </thead>
                <tbody>
                <?php foreach ($serverRecords as $row): ?>
                    <tr>
                        <?php foreach ($serverColumns as $column): ?>
                            <td><?php echo htmlspecialchars((string) $row[$column]); ?></td>
                        <?php endforeach; ?>
                    </tr>
                <?php endforeach; ?>
                </tbody>
            </table>
        </div>
        <div class="pagination-controls pagination-controls-server">
            <form method="get" action="programe.php#cerinta-4" class="pagination-form">
                <input type="hidden" name="pg_k" value="<?php echo (int) $serverK; ?>">
                <input type="hidden" name="pg_offset" value="<?php echo (int) $serverPrevOffset; ?>">
                <button type="submit"<?php echo $serverIsFirstPage ? ' disabled' : ''; ?>>Previous k</button>
            </form>
            <form method="get" action="programe.php#cerinta-4" class="pagination-form">
                <input type="hidden" name="pg_k" value="<?php echo (int) $serverK; ?>">
                <input type="hidden" name="pg_offset" value="<?php echo (int) $serverNextOffset; ?>">
                <button type="submit"<?php echo $serverIsLastPage ? ' disabled' : ''; ?>>Next k</button>
            </form>
        </div>
    <?php endif; ?>

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
<script src="../js/pagination.js"></script>
<script src="../js/pagination-xml.js"></script>
<script src="../js/pagination-jquery.js"></script>
<script src = "../js/collapsible.js"></script>
</body>
</html>
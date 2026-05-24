<?php

require_once __DIR__ . '/../config/db.php';

$serverK = isset($_GET['pg_k']) ? (int) $_GET['pg_k'] : 3;
$serverOffset = isset($_GET['pg_offset']) ? (int) $_GET['pg_offset'] : 0;

if ($serverK < 1) {
    $serverK = 3;
}
if ($serverOffset < 0) {
    $serverOffset = 0;
}

$serverColumns = ['ziua', 'exercitiu', 'seturi', 'repetari', 'focus'];
$serverRecords = [];
$serverTotal = 0;
$serverError = null;
$serverIsFirstPage = true;
$serverIsLastPage = true;

try {
    $serverTotal = (int) $pdo->query('SELECT COUNT(*) FROM programe_saptamanale')->fetchColumn();

    if ($serverTotal > 0 && $serverOffset >= $serverTotal) {
        $serverOffset = (int) (floor(($serverTotal - 1) / $serverK) * $serverK);
    }

    $stmt = $pdo->prepare(
        'SELECT ziua, exercitiu, seturi, repetari, focus
         FROM programe_saptamanale
         ORDER BY id
         LIMIT :limit OFFSET :offset'
    );
    $stmt->bindValue(':limit', $serverK, PDO::PARAM_INT);
    $stmt->bindValue(':offset', $serverOffset, PDO::PARAM_INT);
    $stmt->execute();

    $serverRecords = $stmt->fetchAll(PDO::FETCH_ASSOC);
    $serverIsFirstPage = $serverOffset === 0;
    $serverIsLastPage = $serverOffset + count($serverRecords) >= $serverTotal;
} catch (PDOException $e) {
    $serverError = 'Nu s-au putut incarca programele. Ruleaza backend/database/programe_saptamanale.sql in MySQL.';
}

$serverFrom = $serverTotal === 0 ? 0 : $serverOffset + 1;
$serverTo = $serverOffset + count($serverRecords);
$serverPrevOffset = max(0, $serverOffset - $serverK);
$serverNextOffset = $serverOffset + $serverK;

function server_column_label(string $column): string
{
    return ucfirst($column);
}

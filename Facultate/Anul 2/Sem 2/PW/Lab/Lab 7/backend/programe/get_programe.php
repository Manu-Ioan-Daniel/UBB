<?php
header('Content-Type: application/json; charset=utf-8');

require_once __DIR__ . '/../config/db.php';

$k = isset($_GET['k']) ? (int) $_GET['k'] : 3;
$offset = isset($_GET['offset']) ? (int) $_GET['offset'] : 0;

if ($k < 1) {
    $k = 3;
}
if ($offset < 0) {
    $offset = 0;
}

try {
    $countStmt = $pdo->query('SELECT COUNT(*) FROM programe_saptamanale');
    $total = (int) $countStmt->fetchColumn();

    $stmt = $pdo->prepare(
        'SELECT id, ziua, exercitiu, seturi, repetari, focus
         FROM programe_saptamanale
         ORDER BY id
         LIMIT :limit OFFSET :offset'
    );
    $stmt->bindValue(':limit', $k, PDO::PARAM_INT);
    $stmt->bindValue(':offset', $offset, PDO::PARAM_INT);
    $stmt->execute();

    $records = $stmt->fetchAll(PDO::FETCH_ASSOC);

    echo json_encode([
        'total' => $total,
        'k' => $k,
        'offset' => $offset,
        'records' => $records,
        'isFirstPage' => $offset === 0,
        'isLastPage' => $offset + count($records) >= $total,
    ], JSON_UNESCAPED_UNICODE);
} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode([
        'error' => 'Nu s-au putut incarca programele. Ruleaza backend/database/programe_saptamanale.sql in MySQL.',
        'details' => $e->getMessage(),
    ], JSON_UNESCAPED_UNICODE);
}

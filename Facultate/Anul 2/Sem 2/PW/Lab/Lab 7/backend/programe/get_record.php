<?php
header('Content-Type: application/json; charset=utf-8');

require_once __DIR__ . '/../config/db.php';

$id = isset($_GET['id']) ? (int) $_GET['id'] : 0;

if ($id < 1) {
    http_response_code(400);
    echo json_encode(['error' => 'Id invalid.']);
    exit;
}

try {
    $stmt = $pdo->prepare(
        'SELECT id, ziua, exercitiu, seturi, repetari, focus
         FROM programe_saptamanale
         WHERE id = :id'
    );
    $stmt->bindValue(':id', $id, PDO::PARAM_INT);
    $stmt->execute();

    $record = $stmt->fetch(PDO::FETCH_ASSOC);

    if ($record === false) {
        http_response_code(404);
        echo json_encode(['error' => 'Inregistrarea nu exista.']);
        exit;
    }

    echo json_encode(['record' => $record], JSON_UNESCAPED_UNICODE);
} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode(['error' => 'Eroare la citire.', 'details' => $e->getMessage()]);
}

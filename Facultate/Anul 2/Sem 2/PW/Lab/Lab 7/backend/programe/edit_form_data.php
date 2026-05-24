<?php

require_once __DIR__ . '/../config/db.php';

$editIds = [];
$editError = null;

try {
    $stmt = $pdo->query('SELECT id FROM programe_saptamanale ORDER BY id');
    $editIds = $stmt->fetchAll(PDO::FETCH_COLUMN);
} catch (PDOException $e) {
    $editError = 'Nu s-au putut incarca inregistrarile pentru editare.';
}

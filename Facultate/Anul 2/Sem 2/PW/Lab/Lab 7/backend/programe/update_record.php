<?php
header('Content-Type: application/json; charset=utf-8');

require_once __DIR__ . '/../config/db.php';

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    http_response_code(405);
    echo json_encode(['error' => 'Metoda nepermisa.']);
    exit;
}

$id = isset($_POST['id']) ? (int) $_POST['id'] : 0;
$ziua = isset($_POST['ziua']) ? trim($_POST['ziua']) : '';
$exercitiu = isset($_POST['exercitiu']) ? trim($_POST['exercitiu']) : '';
$seturi = isset($_POST['seturi']) ? (int) $_POST['seturi'] : 0;
$repetari = isset($_POST['repetari']) ? (int) $_POST['repetari'] : 0;
$focus = isset($_POST['focus']) ? trim($_POST['focus']) : '';

if ($id < 1 || $ziua === '' || $exercitiu === '' || $focus === '' || $seturi < 1 || $repetari < 1) {
    http_response_code(400);
    echo json_encode(['error' => 'Date invalide.']);
    exit;
}

try {
    $stmt = $pdo->prepare(
        'UPDATE programe_saptamanale
         SET ziua = :ziua, exercitiu = :exercitiu, seturi = :seturi,
             repetari = :repetari, focus = :focus
         WHERE id = :id'
    );
    $stmt->bindValue(':id', $id, PDO::PARAM_INT);
    $stmt->bindValue(':ziua', $ziua, PDO::PARAM_STR);
    $stmt->bindValue(':exercitiu', $exercitiu, PDO::PARAM_STR);
    $stmt->bindValue(':seturi', $seturi, PDO::PARAM_INT);
    $stmt->bindValue(':repetari', $repetari, PDO::PARAM_INT);
    $stmt->bindValue(':focus', $focus, PDO::PARAM_STR);
    $stmt->execute();

    if ($stmt->rowCount() === 0) {
        $check = $pdo->prepare('SELECT id FROM programe_saptamanale WHERE id = :id');
        $check->bindValue(':id', $id, PDO::PARAM_INT);
        $check->execute();
        if ($check->fetch() === false) {
            http_response_code(404);
            echo json_encode(['error' => 'Inregistrarea nu exista.']);
            exit;
        }
    }

    echo json_encode([
        'success' => true,
        'record' => [
            'id' => $id,
            'ziua' => $ziua,
            'exercitiu' => $exercitiu,
            'seturi' => $seturi,
            'repetari' => $repetari,
            'focus' => $focus,
        ],
    ], JSON_UNESCAPED_UNICODE);
} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode(['error' => 'Eroare la salvare.', 'details' => $e->getMessage()]);
}

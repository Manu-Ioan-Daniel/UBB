<?php
header('Content-Type: application/xml; charset=utf-8');

require_once __DIR__ . '/../config/db.php';

$k = isset($_GET['k']) ? (int) $_GET['k'] : 3;
$offset = isset($_GET['offset']) ? (int) $_GET['offset'] : 0;

if ($k < 1) {
    $k = 3;
}
if ($offset < 0) {
    $offset = 0;
}

function xmlText(string $value): string
{
    return htmlspecialchars($value, ENT_XML1 | ENT_QUOTES, 'UTF-8');
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
    $isFirstPage = $offset === 0;
    $isLastPage = $offset + count($records) >= $total;

    echo '<?xml version="1.0" encoding="UTF-8"?>';
    echo '<programe>';
    echo '<meta>';
    echo '<total>' . $total . '</total>';
    echo '<k>' . $k . '</k>';
    echo '<offset>' . $offset . '</offset>';
    echo '<isFirstPage>' . ($isFirstPage ? 'true' : 'false') . '</isFirstPage>';
    echo '<isLastPage>' . ($isLastPage ? 'true' : 'false') . '</isLastPage>';
    echo '</meta>';
    echo '<records>';

    foreach ($records as $row) {
        echo '<record>';
        echo '<id>' . (int) $row['id'] . '</id>';
        echo '<ziua>' . xmlText($row['ziua']) . '</ziua>';
        echo '<exercitiu>' . xmlText($row['exercitiu']) . '</exercitiu>';
        echo '<seturi>' . (int) $row['seturi'] . '</seturi>';
        echo '<repetari>' . (int) $row['repetari'] . '</repetari>';
        echo '<focus>' . xmlText($row['focus']) . '</focus>';
        echo '</record>';
    }

    echo '</records>';
    echo '</programe>';
} catch (PDOException $e) {
    http_response_code(500);
    echo '<?xml version="1.0" encoding="UTF-8"?>';
    echo '<error>';
    echo '<message>' . xmlText('Nu s-au putut incarca programele. Ruleaza backend/database/programe_saptamanale.sql in MySQL.') . '</message>';
    echo '<details>' . xmlText($e->getMessage()) . '</details>';
    echo '</error>';
}

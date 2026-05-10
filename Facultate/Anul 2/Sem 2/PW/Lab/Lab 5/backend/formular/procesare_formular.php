<?php
require_once '../login/check_auth.php';
check_auth();


if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['nume'])) {

    $target_dir = "../uploads/";
    if (!is_dir($target_dir)) {
        mkdir($target_dir, 0777, true);
    }

    $cale_fisier = ""; 

    if (isset($_FILES['fisier_antrenament']) && $_FILES['fisier_antrenament']['error'] === UPLOAD_ERR_OK) {
        $file_name = time() . "_" . basename($_FILES["fisier_antrenament"]["name"]);
        $target_path = $target_dir . $file_name;

        if (move_uploaded_file($_FILES["fisier_antrenament"]["tmp_name"], $target_path)) {
            $cale_fisier = $target_path; 
            $_SESSION['last_file'] = $target_path;
        }
    }

    require_once '../config/db.php';
    /** @var PDO $pdo */

    try {
        $sql = "INSERT INTO planuri_antrenament (user_id, nume_complet, email, varsta, nivel_pregatire, obiectiv_principal, data_inceperii, observatii, cale_fisier) 
                VALUES (:uid, :nume, :email, :varsta, :nivel, :obj, :data, :obs, :cale)";

        $stmt = $pdo->prepare($sql);
        $stmt->execute([
                'uid'    => $_SESSION['user_id'],
                'nume'   => $_POST['nume'],
                'email'  => $_POST['email'],
                'varsta' => $_POST['varsta'],
                'nivel'  => $_POST['nivel'],
                'obj'    => $_POST['obiectiv'],
                'data'   => $_POST['data-start'],
                'obs'    => $_POST['observatii'],
                'cale'   => $cale_fisier
        ]);

        echo "<h3>Formular si date salvate cu succes in MySQL!</h3>";
        if ($cale_fisier) {
            echo "Fisier incarcat: " . basename($cale_fisier);
        }
    } catch (PDOException $e) {
        echo "Eroare la salvarea in baza de date: " . $e->getMessage();
    }
}

if (isset($_POST['delete_now'])) {
    if (isset($_SESSION['last_file'])) {
        $file_to_del = $_SESSION['last_file'];
        if (file_exists($file_to_del)) {
            unlink($file_to_del);
            unset($_SESSION['last_file']);
            echo "<p style='color:red;'>Fisierul a fost sters de pe server!</p>";
            
        }
    }
}
?>

<?php if (isset($_SESSION['last_file'])): ?>
    <form method="post" style="margin-top: 20px;">
        <input type="submit" name="delete_now" value="sterge fisierul de pe server (Test Cerinta 6)">
    </form>
<?php endif; ?>

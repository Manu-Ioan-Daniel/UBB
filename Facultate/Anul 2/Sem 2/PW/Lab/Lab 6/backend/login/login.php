<?php
session_start();
require '../config/db.php';
/** @var mysqli $conn */

if ($_SERVER["REQUEST_METHOD"] == "POST") {


    $user_captcha = isset($_POST['captcha_input']) ? strtoupper($_POST['captcha_input']) : '';

    if ($user_captcha !== $_SESSION['captcha_auth']) {
        unset($_SESSION['captcha_auth']);
        die("Eroare: Codul CAPTCHA este incorect! Mergi înapoi și încearcă din nou.");
    }
    $username = isset($_POST['username']) ? $_POST['username'] : '';
    $password = isset($_POST['password']) ? $_POST['password'] : '';

    echo "Salut: " .$username;

    if (!empty($username) && !empty($password)) {

        //$sql = "SELECT * FROM users WHERE username = ?";
        //username = 'OR  #
        $sql = "SELECT * FROM users WHERE username = '$username'";
        $stmt = $conn->prepare($sql);
        //$stmt->bind_param("s", $username);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result->num_rows === 1) {
            $user = $result->fetch_assoc();

            if ($password == $user['password']) {
                $_SESSION['user_id'] = $user['id'];
                $_SESSION['username'] = $user['username'];
                $_SESSION['role'] = $user['role'];
                if (isset($_POST['remember'])) {
                    //setcookie("user_login", $user['username'], time() + (86400 * 30), "/");
                    //pentru attack csrf
                    setcookie("user_login", $user['username'], [
                        'expires' => time() + 86400,
                        'samesite' => 'None',
                        'secure' => false
                    ]);
                }
                header("Location: ../dashboard/dashboard.php");
                exit();
            } else {
                echo "Parola gresita";
            }
        } else {
            header("Location: ../../login/login.php");

        }
    } else {
        echo "Te rugam sa completezi ambele campuri.";
    }
} else {
    header("Location: ../../login/login.php");
    exit();
}

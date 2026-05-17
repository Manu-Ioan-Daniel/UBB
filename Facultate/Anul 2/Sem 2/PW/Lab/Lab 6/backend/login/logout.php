<?php
session_start();

if (isset($_COOKIE['user_login'])) {
    setcookie("user_login", "", time() - 3600, "/");
}

$_SESSION = array();

if (ini_get("session.use_cookies")) {
    $params = session_get_cookie_params();
    setcookie(session_name(), '', time() - 42000,
        $params["path"], $params["domain"],
        $params["secure"], $params["httponly"]
    );
}

session_destroy();

header("Location: ../../login/login.php");
exit();

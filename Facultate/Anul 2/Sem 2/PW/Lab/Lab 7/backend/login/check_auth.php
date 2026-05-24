<?php

session_start();
function check_auth()
{
    if (isset($_SESSION['user_id'])) {
        return true;
    }

    if (isset($_COOKIE['user_login'])) {

        require_once __DIR__ . '/../config/db.php';
        /** @var mysqli $conn */

        $username_din_cookie = $_COOKIE['user_login'];
        $stmt = $conn->prepare("SELECT id, username, role FROM users WHERE username = ?");
        $stmt->bind_param("s", $username_din_cookie);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($user = $result->fetch_assoc()) {
            $_SESSION['user_id'] = $user['id'];
            $_SESSION['username'] = $user['username'];
            $_SESSION['role'] = $user['role'];
            return true;
        }
    }
    header("Location: ../login/login.php");
    exit();
}
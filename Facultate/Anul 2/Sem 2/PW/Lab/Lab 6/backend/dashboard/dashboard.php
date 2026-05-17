<?php
session_start();

if (!isset($_SESSION['user_id'])) {
    header("Location: ../../login/login.php");
    exit();
}

echo "Salut " . $_SESSION['username'];
header("Location: ../../home/home.php");

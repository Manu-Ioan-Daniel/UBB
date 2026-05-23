<?php

$file = $_GET['file'];
//FIX:
//$real_path = realpath("../backend/uploads/" . $file);
//$allowed_dir = realpath("../backend/uploads/");
//if ($real_path === false || strpos($real_path, $allowed_dir) !== 0) {
//    die("Acces nepermis!");
//}
//$content = file_get_contents($real_path);
$content = file_get_contents("../uploads/" . $file);
echo $content;

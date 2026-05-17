<?php
session_start();

$chars = '23456789ABCDEFGHJKLMNPQRSTUVWXYZ';
$captcha_code = '';
for ($i = 0; $i < 6; $i++) {
    $captcha_code .= $chars[rand(0, strlen($chars) - 1)];
}

$_SESSION['captcha_auth'] = $captcha_code;

$width = 130;
$height = 40;
$image = imagecreatetruecolor($width, $height);

$white = imagecolorallocate($image, 255, 255, 255);
$black = imagecolorallocate($image, 0, 0, 0);
$gray = imagecolorallocate($image, 200, 200, 200);

imagefill($image, 0, 0, $gray);

for ($i = 0; $i < 5; $i++) {
    imageline($image, 0, rand(0, $height), $width, rand(0, $height), $black);
}

imagestring($image, 5, 35, 12, $captcha_code, $black);
ob_clean();
header('Content-Type: image/png');
imagepng($image);
imagedestroy($image);
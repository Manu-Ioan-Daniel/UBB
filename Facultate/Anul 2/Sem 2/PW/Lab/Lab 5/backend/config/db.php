<?php

class Database {
    private $host = "localhost";
    private $user = "root";
    private $pass = "";
    private $dbname = "programare_web";

    public function getConnNormal() {
        $conn = new mysqli($this->host, $this->user, $this->pass, $this->dbname);

        if ($conn->connect_error) {
            die("MySQLi Connection failed: " . $conn->connect_error);
        }
        return $conn;
    }

    public function getConnPDO() {
        try {
            $dsn = "mysql:host=$this->host;dbname=$this->dbname;charset=utf8mb4";
            $pdo = new PDO($dsn, $this->user, $this->pass);
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            return $pdo;
        } catch (PDOException $e) {
            die("PDO Connection failed: " . $e->getMessage());
        }
    }

    public function getConnPostgre() {

        $pg_host = "localhost";
        $pg_db = "calisthenics_pg";
        $pg_user = "postgres";
        $pg_pass = "admin123";

        try {
            $dsn = "pgsql:host=$pg_host;port=5432;dbname=$pg_db;";
            return new PDO($dsn, $pg_user, $pg_pass);
        } catch (PDOException $e) {
            return "PostgreSQL not connected: " . $e->getMessage();
        }

    }
}

$db = new Database();
$conn = $db->getConnNormal();
$pdo = $db->getConnPDO();
<?php

//include '$config.php';

$servername = "localhost";
$username = "root";
$password = "admin";
$dbname = "GestionBiens";

$coni = new mysqli($servername, $username, $password, $dbname);

if ($coni->connect_error) {
    die("Connection failed: " . $coni->connect_error);
}

$sql = "SELECT type_id, type_name from `type`";

$result = $coni->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row["type_id"].",".$row["type_name"].";";

    }
}

$coni->close();


?> 

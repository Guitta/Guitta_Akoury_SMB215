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

$sql = "SELECT center_id, center_name from `center`";

$result = $coni->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row["center_id"].",".$row["center_name"].";";

    }
}

$coni->close();


?> 

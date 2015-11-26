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

$sql = "SELECT `transport_id`, P.personnel_name FROM `transport` AS T JOIN personnel AS P ON P.personnel_id = T.personnel_id";

$result = $coni->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row["transport_id"].",".$row["personnel_name"].";";

    }
}

$coni->close();


?> 

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

$sql = "SELECT `location_id` , c.center_name, s.salle_name 
FROM `location` AS L
JOIN center AS c ON L.center_id = c.center_id
JOIN salle AS s ON L.salle_id = s.salle_id";

$result = $coni->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row["location_id"].",".$row["center_name"]." ".$row["salle_name"].";";

    }
}

$coni->close();


?> 

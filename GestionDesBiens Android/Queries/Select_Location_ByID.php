<?php

//include '$config.php';

$servername = "localhost";
$username = "root";
$password = "admin";
$dbname = "GestionBiens";

$location_id = $_REQUEST['location_id'];

$coni = new mysqli($servername, $username, $password, $dbname);

if ($coni->connect_error) {
    die("Connection failed: " . $coni->connect_error);
}

$sql = "SELECT center_id, salle_id, personnel_id FROM location where location_id = $location_id";

$result = $coni->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row["center_id"].",".$row["salle_id"].",".$row["personnel_id"].";";

    }
}

$coni->close();


?> 

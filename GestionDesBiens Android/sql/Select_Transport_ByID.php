<?php

//include '$config.php';

$servername = "localhost";
$username = "root";
$password = "admin";
$dbname = "GestionBiens";

$transport_id = $_REQUEST['transport_id'];

$coni = new mysqli($servername, $username, $password, $dbname);

if ($coni->connect_error) {
    die("Connection failed: " . $coni->connect_error);
}

$sql = "SELECT personnel_id FROM transport where transport_id = $transport_id";

$result = $coni->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row["personnel_id"].";";

    }
}

$coni->close();


?> 

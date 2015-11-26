<?php

//include '$config.php';

$servername = "localhost";
$username = "root";
$password = "admin";
$dbname = "GestionBiens";

$item_id = $_REQUEST['item_id'];

$coni = new mysqli($servername, $username, $password, $dbname);

if ($coni->connect_error) {
    die("Connection failed: " . $coni->connect_error);
}

$sql = "SELECT item_code, item_name, type_id, item_specification, location_id FROM item where item_id = $item_id";

$result = $coni->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row["item_code"].",".$row["item_name"].",".$row["type_id"].",".$row["item_specification"].",".$row["location_id"].";";

    }
}

$coni->close();


?> 

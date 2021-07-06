<?php
$host = "127.0.0.1";
$username = "root";
$password = "usbw";   
$dbname =  "bird_system";

// Connect to server
$connect=mysql_connect($host, $username, $password) 
                    or die ("Sorry, unable to connect database server");

$dbselect=mysql_select_db($dbname,$connect) 
                    or die ("Sorry, unable to connect database");
					
$Round 	= $_POST["Round"];
$Player1 = $_POST["Player1"];
$Player2 = $_POST["Player2"];
$Player3 = $_POST["Player3"];
$Player4 = $_POST["Player4"];
$CreateUser 	= $_POST["CreateUser"];

// Run the query
$query = "insert into birdhistory (round, player1, player2, player3, player4, createUser)" .
       			   " values ('$Round', '$Player1', '$Player2', '$Player3', '$Player4', '$CreateUser')";

$result = mysql_query($query);

if ($result) {
    print "Record inserted!";
} else {
    print "Failed to insert record!";
}

mysql_close();
?>






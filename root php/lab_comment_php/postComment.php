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
					
$Comment 	= $_POST["Comment"];
$CreateUser 	= $_POST["CreateUser"];

// Run the query
$query = "insert into discussrecord (comment, createUser)" .
       			   " values ('$Comment', '$CreateUser')";

$result = mysql_query($query);

if ($result) {
    print "Record inserted!";
} else {
    print "Failed to insert record!";
}

mysql_close();
?>






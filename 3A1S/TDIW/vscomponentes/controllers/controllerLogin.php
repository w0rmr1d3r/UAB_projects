<?php
	session_start();
	error_reporting(0);
	$sessionDetalle = $_SESSION["detalle"]; 
	require("../views/viewLogin.php");
?>
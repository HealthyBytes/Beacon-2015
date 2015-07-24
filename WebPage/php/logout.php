<?php 
	
	if(!empty($_POST['logout'])){
		alert($_SESSION['user_id']);
		session_unset("user_id");
		session_unset("user");
		header("Location: ./login.php");
	}
?>

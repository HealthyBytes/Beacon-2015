<!DOCTYPE html>
<html>
<head>
	<?php
		include('./setup/functions.php');
		include('./db_connect/db_connect.php');
	?>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

	<!-- Bootstrap-->
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

	<!-- jQuery UI -->
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  	<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

	<link rel="stylesheet" type="text/css" href="./css/main.css" />
	<script src="./js/ehrscape.js"></script>
	<script src="./js/sider.js"></script>
	<script src="./js/main.js"></script>
	<style>
		#login{
			display: table-cell;
			vertical-align: middle;
		}
	</style>
</head>

<body>
	<div id="login" class="col-md-4 vertical-center col-md-offset-4">
		<div class="panel panel-primary">
			<div class="panel-heading">
				Login
			</div>
			<div class="panel-body">
				<form method="post" action="">
				  <div class="form-group">
				    <label for="exampleInputEmail1">Username</label>
				    <input type="text" class="form-control" id="un" name="un" placeholder="Email">
				  </div>
				  <div class="form-group">
				    <label for="exampleInputPassword1">Password</label>
				    <input type="password" class="form-control" id="pass" name="pass" placeholder="Password">
				  </div>
				  <button type="submit" class="btn btn-default">Login</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>

<?php
	session_start();

	if(!empty($_POST["un"]) && !empty($_POST["pass"])){
		$un = $_POST["un"];
		$pw = $_POST["pass"];
		$sha_pw = sha1($pw);
		# $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
		# $random = str_shuffle ($characters);
		# $salt = substr($random,0,10);
		$q = "SELECT * FROM `doctor` WHERE username = '".$un."' AND SUBSTR(password,11) = '".$sha_pw."'";
		$res = mysqli_query($dbc, $q);
		echo mysqli_error($dbc);
		if($res == 1) {
			alert("OK");
			$_SESSION["user"] = $un;
			$res = mysqli_fetch_assoc($res);
			$_SESSION["user_id"] = $res["id"];
			header("Location: ./index.php");
		}
		else{
			alert("Napacni podatki");
		}
	}
?>

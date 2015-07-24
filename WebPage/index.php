<?php
	session_start();
	if(empty($_SESSION["user"])){
		header("Location: ./login.php");
	}	
	header('Content-Type: text/html; charset=utf-8');
?>


<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<link rel="shortcut icon" href="http://172.30.192.11/beacons/icon.ico">
	<!-- <link rel="shortcut icon" href="favicon.ico"> -->
	
	<?php
		include('./setup/functions.php');
		include('./db_connect/db_connect.php');
		include './php/logout.php';
		
	?>
	
	
	
	<!-- Bootstrap-->
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">	
	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">	
	<!-- Latest compiled and minified JavaScript -->
	
	
	<!-- jQuery UI -->
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  	<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

  	
	<link rel="stylesheet" type="text/css" href="./css/main.css" />
	<link rel="stylesheet" type="text/css" href="./css/sider_style.css" />
	<script>
		doctor_id = 1;
	</script>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script src="./js/ehrscape.js"></script>
	<script src="./js/sider.js"></script>
	<script src="./js/refreshData.js"></script>
	<script src="./js/sider.js"></script>
	<script src="./js/main.js"></script>
	<script> doctor_id = <?php echo $_SESSION['user_id']; ?></script> 
	
	<script type="text/template" id="patient_template">
		<li class="ui-state-default dropdown-toggle patients {{myclass}}" id="{{patient-id}}">		
			  <a href="#" class="list-group-item">
				<div class="row">
					<div class="col-md-10 onlist_patient">
						<h4 class="list-group-item-heading">
							<span class="wait_name"></span>
							<span class="wait_surname"></span>
						</h4>
						<p class="list-group-item-text">
								
							<span class="wait_age"></span>												
							<span class="wait_gender"></span>	
							<span class="wait_born"></span>		
													
						</p>
					</div>	
					<div class="col-md-2 bars"><i class="fa fa-bars"></i></div>
				</div>
			  </a>
			  

			  <ul class="sider_dropright">
			  	<li class="uvrsti">Uvrsti</li>
			  	<li class="sprejmi">Sprejmi</li>
			  	<li class="odpusti">Odpusti</li> 			 
		
			  </ul> 
			  
			</li>
	</script>
	
	<?php include "./phr/include.php"; ?>
</head>	
<body>
	<div class="wrap col-md-10 col-md-offset-1">
		<div class="row">
			<div class="sider col-md-3">	
				<?php 
					include './widgets/sider.php';
				?>
			</div>
			<div class="main_body col-md-9">	
				<div class="header">	
					<?php include "./widgets/header.php"; ?> 		
				</div>		
				
				<div class="patient_inf col-md-12">
					<?php
						include './widgets/main_body.php';													
					?>	
									
				</div>
					
			</div>
		</div>	
		<div class="myfooter">
			&copy;MarandStudents
		</div>	
	</div>
	
</body>	
</html>
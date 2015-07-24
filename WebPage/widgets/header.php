<?php 
	$q = "SELECT * FROM hospital,doctor WHERE doctor.hospital_id = hospital.id AND doctor.id =".$_SESSION["user_id"];
	$res = mysqli_query($dbc, $q);
	$res = mysqli_fetch_assoc($res);
	
?>
<div class="col-md-4 doctors_img">
	<center>
		<img height="100px" width="100px" src="http://icons.iconarchive.com/icons/icons-land/medical/128/People-Doctor-Male-icon.png" alt="Smiley face" height="42" width="42">
	</center>
</div>	
<div class="col-md-5 doctors_data">	
	<div class="row" id="doctor_name"><?php echo $res["doctor_name"]." ".$res["doctor_surname"]; ?></div>
	<div class="row" id="hospital"><?php echo $res["name"] ?></div>
	<div class="row" id="address"><?php echo $res["address"] ?></div>	
</div>
<div class="col-md-3 doctors_data">
	<form action="" method="post">		
		 <input type="submit" name="logout" value="Logout" class="btn btn-default pull-right"/>
	</form>			
</div>		

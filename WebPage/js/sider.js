
function getData(ehrId,el){
	
	$.ajax({
	    url: baseUrl + "/demographics/ehr/" + ehrId + "/party",
	    type: 'GET',
	    headers: {
	        "Ehr-Session": sessionId
	    },
	    success: function (data) {
	       var party = data.party;	
	       //Get age
	       var birthday = party.dateOfBirth;
	       //alert(Date.now()+" * "+new Date(birthday).getTime());
	       var ageDifMs = Date.now() - new Date(birthday).getTime();
    	   var ageDate = new Date(ageDifMs); // miliseconds from epoch
	       var age = Math.abs(ageDate.getUTCFullYear() - 1970);	
	      
	       
	       $(el).find(".wait_name").text(party.firstNames);
	       $(el).find(".wait_surname").text(party.lastNames);
	       
	       if(party.dateOfBirth)
	       	$(el).find(".wait_born").html("Born: <strong>"+party.dateOfBirth.substr(0,10)+"</strong>");
	       else
	       	$(el).find(".wait_born").html("");
	       if(party.gender) 	
	       	$(el).find(".wait_gender").html("Gender: <strong>"+ party.gender.substr(0,1)+"</strong>");
	       else
	       	$(el).find(".wait_gender").html("");
	       if(age)
	       	$(el).find(".wait_age").html("Age: <strong>"+ age +"</strong>");
	       else
	       	$(el).find(".wait_age").html("");
	       
	    },
	    error:function(){
	    	console.log("errorGetData");
	    }
	});	
}

function fill_data(){
	$(".sider .patients").each(function(index){
		
		getData($(this).attr("id"),$(this));		
	});
	
}

	


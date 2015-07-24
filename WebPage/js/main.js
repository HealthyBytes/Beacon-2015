function updatePatients(){
	$.ajax({
	    url: './php/getPatients.php',
	    type: 'POST',
	    data: {doctor: doctor_id},
    	dataType: 'json',
    	success: function(data) {
    		//alert(JSON.stringify(data));
    		console.log("updateParents");
    		
    		$("#patient_order").html("");    		
    		for (var i=0;i<data.ehr_ids.length;i++){    			
    			var template = $("#patient_template").html(); 			
    			//$(patients).append(template.replace("{{patient-id}}",data[i]));
    			
	    		$("#patient_order").append(template.replace("{{patient-id}}",data.ehr_ids[i]).replace("{{myclass}}",data.status[i]));	    										
			};	
			fill_data();	
			
		},
		error: function(err,x,y) {
            console.log("error");
		},
	});			
}

$(document).ready(function(){
	username = "ales.smokvina@gmail.com";
	password = "ehr4ales";
	baseUrl = 'https://rest.ehrscape.com/rest/v1';	
	sessionId = getSessionId(username,password);
	
	ehrId = $("#patient_order li:first-child").attr("id");
	$('#timeline-example').ehrscapeTimeline({ehrId : ehrId});
	fill_data();
	
	patients = document.createElement("ul");    
	
	$( "#patient_order" ).sortable();
    $( "#patient_order" ).disableSelection();
    var order;
    //Posodobi vrstni red
    $( "#patient_order" ).sortable({
    	start: function(event, ui) {
	        ui.item.startPos = ui.item.index();
	    },
	    stop: function(event, ui) {
	        console.log("Start position: " + ui.item.startPos);
	        console.log("New position: " + ui.item.index());
	     
	        var startID = $("#patient_order li:nth-child("+(ui.item.startPos+1)+")").attr("id");
	        var secondID = $("#patient_order li:nth-child("+(ui.item.index()+1)+")").attr("id");
	      
	        
	       	//alert(startID+" "+secondID);
	       	var order = JSON.stringify($("#patient_order").sortable('toArray'));
	       	$.ajax({
			    url: './php/update_order.php',
			    type: 'POST',
			    data: {firstPos: ui.item.startPos,
			    		firstID: startID,
			    		secondPos:ui.item.index(),
			    		secondID: secondID,
			    		 doctor: doctor_id},
            	dataType: 'json',
            	success: function(data) {
                    console.log("success");
        		},
        		error: function(err,x,y) {
                    console.log("error");
        		},
        	});	
	    }
	});
   /*
	document.querySelector('body').addEventListener('click', function(event) {
	  if (event.target.className === '.onlist_patient') {
	    //alert(event.target.className);
	  }
	});
	*/
	//Spremeni prikaz na desni
	$("#patient_order").on("click",".onlist_patient",function(e){
		e.preventDefault();		
		
		ehrId = $(this).closest("li").attr("id");			
		
        login().done(function () {
        clearData();	
        patientData().done(function() {	            
            $.when(
                bloodPressures(),
                getWeight(),
                getHeight(),
                getBMI(),
                getSpo2(),
                
                getTemperature(),
                getPulse(),
                getAllergies(),
                getMedications(),
                getProblems(),
                getLabs()
            );
             console.log(ehrId);
             $('#timeline-example').html("");
             $('#timeline-example').ehrscapeTimeline({ehrId : ehrId});
	        });
	    
	    });
		
       
	});
	//Prikaži menu za razvršanje pacienta
	$("#patient_order").on("click",".bars",function(e){
		
		var el = $(e.target).closest("li");
		if($(el).find("ul").css("display") == "none"){
			$(".sider_dropright").css({"display":"none"});
			var pos = $(el).position().left;	
			var width = $(el).width();	
			if(width <= ($(window).height() * 0.9))
				$(el).find("ul").css({"display":"block","position":"absolute","left": pos+width+2,"z-index":100,"top":$(el).position().top});
			else
				$(el).find("ul").css({"display":"block"});	
			$('#'+$(el).attr("id")+'.uvrsti').focus();		
		}
		else
			$(el).find("ul").css({"display":"none"});
		
		
	});
	//Odpusti pacienta
	$("#patient_order").on("click",".odpusti",function(e){
		var id = $(this).parent().closest("li").attr("id");
		$.ajax({
		  url: "http://172.30.192.11:3000/api/removeFromQueue",
		  type: "POST",
		  data: {"id" : id},		 		  
		  dataType: "json",
		  success: function(res){
			//alert("Success removing"+JSON.stringify(res));
			$(e.target).parent().hide();			
			$("#"+id).remove();
			
		  },
		  error: function() {
		  	alert("Err");
		  }
		});
	});
	//Remove first patient from the list and show the second one
	$(".next_patient").click(function(){
		var id = $("#patient_order li:first-child").attr("id");		
		$.ajax({
		  url: "http://172.30.192.11:3000/api/removeFromQueue",
		  type: "POST",
		  data: {"id" : id},		 		  
		  dataType: "json",
		  success: function(res){	
		  	ehrId = $("#patient_order li:nth-child(2)").attr("id");				
			$("#"+id).remove();		
			ehrId = $("#patient_order li:first-child").attr("id");	
			$( "#"+ ehrId +" .sprejmi").trigger("click");			
	        login().done(function () {
	        clearData();	
	        patientData().done(function() {	            
	            $.when(
	                bloodPressures(),
	                getWeight(),
	                getHeight(),
	                getBMI(),
	                getSpo2(),
	                
	                getTemperature(),
	                getPulse(),
	                getAllergies(),
	                getMedications(),
	                getProblems(),
	                getLabs()
	            );
	            console.log(ehrId);
	            $('#timeline-example').html("");
	            $('#timeline-example').ehrscapeTimeline({ehrId : ehrId});
		        });
		    
		    });
		
		  },
		  error: function() {
		  	alert("Err");
		  }
		});
	});
	//Sprejmi ali uvrsti pacienta
	$("#patient_order").on("click",".sprejmi,.uvrsti",function(e){
		var id = $(this).parent().closest("li").attr("id");
		var status = $(this).hasClass("uvrsti") ? "ordered":"accepted";
		$.ajax({
		  url: "http://172.30.192.11:3000/api/setStatus",
		  type: "POST",
		  data: {"id" : id,"status": status},		 		  
		  dataType: "json",
		  success: function(res){
			//alert("Success removing"+JSON.stringify(res));
			
			$("#"+id).removeClass("unordered accepted ordered").addClass(status);				
			$(e.target).parent().hide();
		  },
		  error: function() {
			alert("Err");
		  }
		});
	});
	/*
	$('a[data-toggle="tab"]').on('shown.bs.tab', function(e){
    	alert($(e.target).text());
        var currentTab = $(e.target).text(); // get current tab
        var LastTab = $(e.relatedTarget).text(); // get last tab
        $(".current-tab span").html(currentTab); 
        $(".last-tab span").html(LastTab);
    });
    */
   $(".graphs").click(function(){
   		
   		
   		$(window).css( 'cursor', 'wait' );
   		 login().done(function () {
	        clearData();	
	        patientData().done(function() {	            
	            $.when(
	                bloodPressures(),
	                getWeight(),
	                getHeight(),
	                getBMI(),
	                getSpo2(),
	                
	                getTemperature(),
	                getPulse(),
	                getAllergies(),
	                getMedications(),
	                getProblems(),
	                getLabs()
	            );
		        });
		    
		    }).done(function(){
		    	 $(window).css( 'cursor', 'default' );
		    });
		   
   });	
	window.setInterval(updatePatients, 10000);
});



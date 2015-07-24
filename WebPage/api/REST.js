function REST_ROUTER(router,connection,md5) {
    var self = this;
    self.handleRoutes(router,connection,md5);
}

function search(res,username,password,data){
	 var searchData = [
	    {key: "firstNames", value: data.pname},
	    {key: "lastNames", value: data.psurname}
	];
	 var request = require("request");	
	 request({
		method: "POST",
		url: "https://rest.ehrscape.com/rest/v1/session?username="+username+"&password="+password,
		body: {},
	    json: true
	    },
	    function (err,r,body) {
		
		  var sessionID = body.sessionId;
		  
		    //console.log(JSON.stringify(partyData)+"\n\n");
		    request({
		 		method: "POST",
		 		url: "https://rest.ehrscape.com/rest/v1/demographics/party/query",
		 		body: searchData,
		 		headers: {
		        	'Ehr-Session': sessionID
			    },
			    json: true,
			    },
			    function (err,r,body) {				 
	                  console.log(r);
	                  if(body && body.parties && body.parties.length > 0){
	                  	//res.json({"Result": "In","Data": body.parties[0]});
	                  	for (j in body.parties[0].partyAdditionalInfo) {
			                if (body.parties[0].partyAdditionalInfo[j].key === 'ehrId') {
			                	var json = ({"Msg": "InEhrScape","Msg2":""});
			                	  var ehrID = body.parties[0].partyAdditionalInfo[j].value;
			                  	 //Kreiraj pacienta v bazi				                  
				                  findInDB(ehrID,json,data,res);
				                  
			                    
			                    break;
			                }
			            }	
			            
	                  }
	                  else{
	                  	 createNew(res,sessionID,username,password,data);		                  	  
	                  }                  	          	
			});	
			  	
		});
}
function createNew(res,sessionID,username,password,data){ 
 	
 	var request = require("request");	
	request({
		method: "POST",
		url: "https://rest.ehrscape.com/rest/v1/ehr",
		body:{},
		headers: {
	    	'Ehr-Session': sessionID
	    },
	    json: true,
	    },
	    function (err,r,body) {
	    	
	    	ehrID = body.ehrId;
	    			
	    	var partyData = {
		        firstNames: data.pname,
		        lastNames: data.psurname,
		        dateOfBirth: data.bDate,
				gender: data.gender,
		        partyAdditionalInfo: [
		            {
		                key: "ehrId",
		                value: ehrID
		            }
		        ]
		    };	
		
		    request({
		 		method: "POST",
		 		url: "https://rest.ehrscape.com/rest/v1/demographics/party",
		 		body: partyData,
		 		headers: {
		        	'Ehr-Session': sessionID
			    },
			    json: true,
			    },
			    function (err,r,body) {
				   if (body.action == 'CREATE') {
	                  var json = ({"Msg": "NotInEhrscape","Msg2":"Successfully created patient!"});
	                  
	                  //Kreiraj pacienta v bazi
	                  createInDB(ehrID,json);
	                  //Vstavi pacienta v vrsto
	                  findInDB(ehrID,json,data,res);

	                }  	
	                else{
	                	 res.status(400);
	                	 res.json({"Msg": "NotInEhrscape","Msg2":"Error creating patient"});
	                }    	
			});	
	
	});	
	 		
}
function createInDB(ehrID,json){
	var query = "INSERT INTO `patient`(ehr_id) VALUES ('"+ehrID+"');";
    connection.query(query,function(err,rows,fields){
    	if (err) {
    		
    		console.log("Error create in db");
    		console.log("--------\n"+err.message+"\n-------\n"); 
    		res.status(400);
    		res.json({"Error":err.message});
    	}
    	else{
    			
    	}
  });
}
function findInDB(ehrID,json,data,res){
	var query = "SELECT patient_id FROM `patient` WHERE ehr_id = '"+ehrID+"';";
    connection.query(query,function(err,rows,fields){
    	if (err){
    		res.status(400);
    		console.log("Error find in db "+ query);	
    		console.log("--------\n"+err.message+"\n-------\n"); res.json({"Error":err.message});
    			 
    	}
    	else{
    		json["patient_id"] = rows[0].patient_id;
    		vstaviVVrsto(res,data.doctor_id,rows[0].patient_id,json);
    	}
  });
}
function vstaviVVrsto(res,doctorID,patientID,json){
	var query = "SELECT DISTINCT next_patient FROM doctor WHERE doctor.id = "+doctorID+";";
	connection.query(query,function(err,rows,fields){
        if (err ){ 
        	res.status(400);
        	console.log("Err vstavi V vrsto "+query);	
        	console.log("--------\n"+err.message+"\n-------\n"); res.status(400); res.json({"Error":err.message});
        }
        else if(rows.length < 1){
        	res.status(400); res.json({"Error":"Not valid doctor"});
        }        
        else{
        	// Increment next patint_idx
        	var next_idx = rows[0].next_patient;
        	var query = "UPDATE `doctor` SET next_patient = next_patient + 1 WHERE doctor.id = "+doctorID;
        	connection.query(query,function(err,rows,fields){
	        if (err){ 	console.log("Err increment "+query);	console.log("--------\n"+err.message+"\n-------\n");  res.status(400); res.json({"Error":err.message});      }
	        });
	        
			
		    query = "INSERT INTO `queue`(`patient_id`, `doctor_id`, `wait_idx`, status) VALUES ("+patientID+","+doctorID+","+next_idx+",'unordered');";
		    connection.query(query,function(err,rows,fields){
	        	if (err){
	        		
		    		console.log("Error vstavi v db "+query);	
		    		 console.log("--------\n"+err.message+"\n-------\n");  res.status(400); res.json({"Error":err.message});
		    			 
		    	}
	        	else{
	        		query ="SELECT patient_id FROM queue,doctor WHERE doctor.id = queue.doctor_id AND doctor.id = "+doctorID+" ORDER BY wait_idx";
	        		connection.query(query,function(err,rows,fields){
			        if (err){ 	console.log("Err wait_idx "+query);	console.log("--------\n"+err.message+"\n-------\n"); res.status(400); res.json({"Error":err.message});      }
			        else{
			        	//Poi��em index v vrsti
			        	console.log("\n\n\n\n"+JSON.stringify(rows));
			        	for (i = 0; i < rows.length; i++) { 
						   if(rows[i].patient_id == patientID)
			        			json["wait_idx"] = i + 1;
						}			        		
			        	res.json(json);	
			        }
			        });
	        		
	        		
	        	}
	      	});
	    }
    });
}


REST_ROUTER.prototype.handleRoutes = function(router,connection,md5) {
    router.use(function (req, res, next) {
	
	    // Website you wish to allow to connect
	    res.setHeader('Access-Control-Allow-Origin', '*');
	
	    // Request methods you wish to allow
	    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
	
	    // Request headers you wish to allow
	    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
	
	    // Set to true if you need the website to include cookies in the requests sent
	    // to the API (e.g. in case you use sessions)
	    res.setHeader('Access-Control-Allow-Credentials', true);
	
	    // Pass to next layer of middleware
	    next();
	});
    router.get("/",function(req,res){
        res.json({"Message" : "Welcome to our api!"});
    });   
    router.get("/users/:user_id",function(req,res){
        var query = "SELECT * FROM patient WHERE patient_id="+req.params.user_id;
        
        //Pogledam �e je uporabnik v bazi in pridobim njegov id
        connection.query(query,function(err,rows,fields){
            if (err){
            	console.log("--------\n"+err.message+"\n-------\n"); res.json({"Error":err.message});
            	res.status(400);
            	res.json({"res":"error"});
            }  			
		  	if(rows.length == 1){		  	
		  		var ehrID = rows[0].patient_id;
		  		//Dodaj pacienta v vrsto
		    	res.json({"msg":"InDB"});
		    	
		   	}
		    else
		    	res.json({"msg":"notInDB"});	
        });
    });
    router.post("/firstlogin",function(req,res){
     	
     	var username = "ales.smokvina@gmail.com";
		var password = "ehr4ales";
		var data = {"pname":req.body.name
			,"psurname":req.body.surname,
			"bDay":req.body.bDate,
			"doctor_id":req.body.doctor,
			"gender":req.body.gender
			};
		search(res,username,password,data);     	
    }); 
    router.post("/login",function(req,res){
     	console.log(req.body.doctor+""+req.body.patient)		
	 	vstaviVVrsto(res,req.body.doctor,req.body.patient,{});
    }); 
    router.post("/position",function(req,res){
    	
    	query ="SELECT doctor_name,doctor_surname,patient_id,status FROM queue,doctor WHERE doctor.id = queue.doctor_id AND doctor.id = "+req.body.doctor+" ORDER BY wait_idx";
		connection.query(query,function(err,rows,fields){
	        if (err  || rows.length == 0 ){ 	console.log("Err position "+query);	console.log("--------\n"+err.message+"\n-------\n"); res.json({"Error":err.message});      
	        	res.status(400);
	        	res.json({"status": "error"});
	        }
	        else{
	        	//Poi��em index v vrsti
	        	var wait_idx = -1;	        	
	        	for (i = 0; i < rows.length; i++) { 
				   if(rows[i].patient_id == req.body.patient)
	        			wait_idx = i + 1;
				}	
				if(rows[wait_idx-1]){		        		
	        		res.json({"wait_idx": wait_idx,"status":rows[wait_idx-1].status,"doctor_name":rows[wait_idx-1].doctor_name+" "+rows[wait_idx-1].doctor_surname});
	        	}	
	        	else{
	        		res.status(400);
	        		res.json({"Error": "error"});
	        	}
	        }
	     });
    });
    
	router.post("/doctor",function(req,res){    	
    	query ="SELECT doctor_id FROM beacon WHERE uid = '"+req.body.uid+"' AND major = '"+req.body.major+"' AND minor = '"+req.body.minor+"';";
		connection.query(query,function(err,rows,fields){
	        if (err || !rows[0].doctor_id){ 	console.log("Err doctor "+query);	console.log("--------\n"+err.message+"\n-------\n"); 
	        	res.status(400);
	        	res.json({"Error":err.message});      
	        	res.json({"status": "error"});
	        }
	        else{  
	        	query ="SELECT doctor_name,doctor_surname,id FROM doctor WHERE doctor.id = "+rows[0].doctor_id+";";   	
	        	connection.query(query,function(err,rows,fields){
			        if (err){ 	console.log("Err doctor "+query);	console.log("--------\n"+err.message+"\n-------\n"); res.json({"Error":err.message});      
			        	res.status(400);
			        	res.json({"status": "error"});
			        }
			        else	
			        	res.json(rows[0]);	
			     });	        	
	        }
	     });
    });
	router.post("/removeFromQueue",function(req,res){    	
		query ="DELETE FROM queue USING patient,queue WHERE patient.patient_id = queue.patient_id AND patient.ehr_id = '"+req.body.id+"';";   	
		res.setHeader('Access-Control-Allow-Origin', '*');
		connection.query(query,function(err,rows,fields){
			if (err){ 	console.log("Err doctor "+query);	console.log("--------\n"+err.message+"\n-------\n"); res.json({"Error":err.message});      
				res.status(400);
				res.json({"status": "error","res":rows});
			}
			else	
				res.json({"status":"removed","query":query});	
		 });	        	
	});
	router.post("/setStatus",function(req,res){    	
	
		query ="UPDATE queue SET status = '"+req.body.status+"' WHERE queue.patient_id IN (SELECT DISTINCT patient_id FROM patient WHERE patient.ehr_id = '"+req.body.id+"');";   	
		res.setHeader('Access-Control-Allow-Origin', '*');
		connection.query(query,function(err,rows,fields){
			if (err){ 	console.log("Err set status "+query);	console.log("--------\n"+err.message+"\n-------\n"); res.json({"Error":err.message});      
				res.status(400);
				res.json({"status": "error","res":rows});
			}
			else	
				res.json({"status":"status changed","query":query});	
		 });	        	
	});
	
}

module.exports = REST_ROUTER;
window.onload = somefunction();

function somefunction()
{
	let url = "/ReimbursementProject/AccountInfoServlet";
		
	asynchFunction();
	
    async function asynchFunction() {
            let response = await fetch(url, {
                method: 'POST',
                headers: 
				{
                    'Content-Type': 'application/json;charaset=utf-8',
                },
               /* body: JSON.stringify(user),*/
 
            }).then(response => {return response.text()}

				).then(function(response)
				{
					//console.log("RESPONSE: " + response) 
					parseResponse(response);
					
				}).catch(error => {
					//window.location.href = "/ReimbursementProject";
                	console.error(error);

            	})		
        }
}

function parseResponse(response)
{
	const tbody = document.querySelector("tbody");
	let empty = 'No Tickets Found!'
	let user = {
            id: '',
            amount: '',
			time_submitted: '',
			time_resolved: '',
			description: '',
			author: '',
			resolver: '',
			status: '',
			type: ''
	};
	
	//console.log(response);
	if(response == empty)
	{
		tbodyEl.innerHTML += `
            <tr>
                <td>`+ empty + `</td>
            </tr>
        `;
	}
	else
	{
		var res = JSON.parse(response);
		console.log(res.firstname);
		
		document.getElementById('spanname').innerHTML=res.firstname + " " + res.lastname;
		document.getElementById('spanemail').innerHTML=res.email;
		document.getElementsByName('firstname')[0].placeholder=res.firstname;
		document.getElementsByName('lastname')[0].placeholder=res.lastname;
		document.getElementsByName('role')[0].placeholder=res.role;
		document.getElementsByName('email')[0].placeholder=res.email;
		document.getElementById('username').innerHTML="Username: " + res.username;
		document.getElementById('id').innerHTML="User ID: " + res.id;
		
		/*for (let i = 0; i < res.length; i++) {
			console.log(res[i]);
			if(res[i].status == 0)
				var s = "PENDING";
			if(res[i].status == 1)
				var s = "ACCEPTED";
			if(res[i].status == -1)
				var s = "DENIED";
			
			 tbody.innerHTML += `
	            <tr>
	                <td>`+ res[i].ID + `</td>
	                <td>`+ res[i].amount + `</td>
					<td>` + res[i].type + `</td>
					<td>` + s + `</td>
					<td>TEST</td>
	                
	            </tr>
	        `;
		}*/
  		//ext += cars[i] + "<br>"; <td><button class="deleteBtn">Delete</button></td>
	}
	
	
}
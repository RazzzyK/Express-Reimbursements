async function idkwhatimdoing()
{
	let url = "/ReimbursementProject/ManagerServlet";
	const searchInput = document.querySelector("searchbox");
	
	let userN =  document.getElementById('searchbox').value;
	//var sanitized = userN.replace(/""/g, '');
	//console.log(sanitized);
	
	let response = await fetch(url, {
                method: 'POST',
                headers: 
				{
                    'Content-Type': 'application/json;charaset=utf-8',
                },
                body: userN,
 
            }).then(response => {return response.text()
			}).then(function(response)
				{
					//console.log(response) 
					parseResponse(response);
					
				}).catch(error => {
					
                console.error(error);

            	})
	
	//searchInput.addEventListener("submit", onAddWebsite);
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
		console.log(response);
		var sanitized = response.replace(/}{/g, '},{');
		console.log(sanitized);
		var res = JSON.parse(response);
		//var array = [1,2,3]
		
		for (let i = 0; i < res.length; i++) {
			console.log(res);
			if(res[i].status == 0)
				var s = "PENDING";
			if(res[i].status == 1)
				continue;
			if(res[i].status == -1)
				continue;
			
			 tbody.innerHTML += `
	            <tr>
	                <td>`+ res[i].ID + `</td>
	                <td>`+ res[i].amount + `</td>
					<td>` + res[i].type + `</td>
					<td>` + s + `</td>
					<td>TEST</td>
	                <td><button class="deleteBtn">Approve</button>   <button class="deleteBtn">Deny</button></td>
	            </tr>
	        `;
		}
  		//ext += cars[i] + "<br>"; <td><button class="deleteBtn">Delete</button></td>
	}
	
	
}
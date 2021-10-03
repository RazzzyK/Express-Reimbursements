//window.onload = 
const tableEl = document.querySelector("table");
const tableE2 = document.querySelector("table2");
let userN;

async function populateEmployeeTable()
{
	document.getElementById('card2').style.visibility = "hidden";
	let url = "/ReimbursementProject/ManagerServlet";
	
	let response = await fetch(url, {
                method: 'POST',
                headers: 
				{
                    'Content-Type': 'application/json;charaset=utf-8',
                },
                body: "GETEMPLOYEES",
 
            }).then(response => {return response.text()
			}).then(function(response)
				{
					//console.log(response) 
					parseResponseEmployee(response);
					
				}).catch(error => {
					
                console.error(error);

            	});



}

async function idkwhatimdoing(userN)
{
	
	let url = "/ReimbursementProject/ManagerServlet";
	const searchInput = document.querySelector("searchbox");
	
	//userN =  document.getElementById('searchbox').value;
		
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
					parseResponse(response, userN);
					
				}).catch(error => {
					
                console.error(error);

            	});
	
	//searchInput.addEventListener("submit", onAddWebsite);
}

function parseResponse(response, userN)
{
	const tbody = document.getElementById("tbody2");
	let empty = '"No Tickets Found!"'
	console.log(JSON.stringify(response) + empty);
	if(JSON.stringify(response) === empty)
	{
		document.getElementById('card2').style.visibility = "hidden";
		alert("No Pending Tickets for this employee")
		console.log(empty.replace('"', ''));
		tbody.innerHTML += `
            <tr>
                <td>`+ empty + `</td>
            </tr>
        `;
console.log("Hello");
	}
	else
	{
		document.getElementById('card2').style.visibility = "visible";
		var res = JSON.parse(response);

		tbody.innerHTML = "";
		for (let i = 0; i < res.length; i++) {

			if(res[i].status == 0)
				var s = "PENDING";
			if(res[i].status == 1)
				continue;
			if(res[i].status == -1)
				continue;
			
			console.log(userN +  " " + res[i].ID)
			
			 tbody.innerHTML += `
	            <tr>
	                <td class="bruhthisbetterwork">`+ res[i].ID + `</td>
	                <td>` + res[i].amount + `</td>
					<td>` + res[i].type + `</td>
					<td>` + s + `</td>
					<td>TEST</td>
	                <td><button class="approveBtn" onClick="appproveReimbursement(` + res[i].ID + `, '` + userN + `')">Approve</button>   <button class="denyBtn" onClick="denyReimbursement(` + res[i].ID + `, '` + userN + `')">Deny</button></td>
	            </tr>
	        `;
			
		}
  		//ext += cars[i] + "<br>"; <td><button class="deleteBtn">Delete</button></td>
	}
}

function parseResponseEmployee(response)
{
	const tbody = document.getElementById("tbody");
	let empty = 'No Users Found!'
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
		var res = JSON.parse(response);

		
		for (let i = 0; i < res.length; i++) {

			/*if(res[i].status == 0)
				var s = "PENDING";
			if(res[i].status == 1)
				continue;
			if(res[i].status == -1)
				continue;*/
			
			console.log(" " + JSON.stringify(res));
			/*if(document.getElementById("tbody2") != null){
			    console.log("Im Here");
			}*/
			
			 tbody.innerHTML += `
	            <tr onclick="idkwhatimdoing('` + res[i].username + `')">
					<td>`+ res[i].id + `</td>
	                <td>` + res[i].firstname +  ` ` + res[i].lastname + `</td>
	            </tr>
	        `;
			
		}
  		//ext += cars[i] + "<br>"; <td><button class="deleteBtn">Delete</button></td>
	}
}

/*let test = document.getElementById("table");
document.getElementById("table").addEventListener("click", funn);

if(test) {
	console.log("Here");
	test.addEventListener('click', funn);
}*/

function funn(e)
{
	alert(e);
}

async function appproveReimbursement(id, userN)
{
	console.log("Here" + userN);
	let url = "/ReimbursementProject/ManagerServlet";
	
	let response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charaset=utf-8'
            },
            body: (id + ",APPROVE"),

        }).then((response) => {
			let result = response.json(); //response.text, response.formatDat, response.blob

			//window.location.href = response.url;
			//location.reload();
        }).catch(error => {
            console.error(error);
        });
	
	var tbody = document.getElementById("tbody2");
	tbody.innerHTML = "";

	reload(userN);

}

async function denyReimbursement(id, userN)
{
	let url = "/ReimbursementProject/ManagerServlet";
	
	let response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charaset=utf-8'
            },
            body: (id + ",DENY"),

        }).then((response) => {
			let result = response.json(); //response.text, response.formatDat, response.blob

			//window.location.href = response.url;
			
        }).catch(error => {
            console.error(error);
        });

	var tbody = document.getElementById("tbody2");
	tbody.innerHTML = "";
	
	reload(userN);
}

async function reload(userN)
{
	let url = "/ReimbursementProject/ManagerServlet";
	
	let res = await fetch(url, {
                method: 'POST',
                headers: 
				{
                    'Content-Type': 'application/json;charaset=utf-8',
                },
                body: userN,
 
            }).then(res => {return res.text()
			}).then(function(res)
				{
					console.log(res) 
					parseResponse(res, userN);
					
				}).catch(error => {
					
                console.log(res);

            	});
}
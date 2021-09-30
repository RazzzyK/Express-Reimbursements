window.onload = somefunction();

function somefunction()
{
	let url = "/ReimbursementProject/EmployeeServlet";
	

	/*let user = {
            username: document.getElementById('username').value,
            password: document.getElementById('password').value
        };*/
	
	
	
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
					//console.log(response) 
					parseResponse(response);
					
				}).catch(error => {
					
                console.error(error);

            	})	
			//console.log("This is response: " + JSON.stringify(response)); out of scope
					
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
		//var array = [1,2,3]
		
		for (let i = 0; i < res.length; i++) {
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
		}
  		//ext += cars[i] + "<br>"; <td><button class="deleteBtn">Delete</button></td>
	}
	
	
}

//================================================================================================================================================

function dynamicTable()
{
	const formEl = document.querySelector("form");
    const tbodyEl = document.querySelector("tbody");
    const tableEl = document.querySelector("table");
    function onAddWebsite(e) {
    	e.preventDefault();
        const website = document.getElementById("website").value;
        const url = document.getElementById("url").value;
        tbodyEl.innerHTML += `
            <tr>
                <td>${url}</td>
                <td>${website}</td>
                <td><button class="deleteBtn">Delete</button></td>
				<td></td>
				<td></td>
            </tr>
        `;
    }

    function onDeleteRow(e) {
    	if (!e.target.classList.contains("deleteBtn")) {
          return;
        }

        const btn = e.target;
        btn.closest("tr").remove();
	}

    formEl.addEventListener("submit", onAddWebsite);
    tableEl.addEventListener("click", onDeleteRow);
}

/*// Open the full screen search box
function openSearch() {
  document.getElementById("myOverlay").style.display = "block";
}

// Close the full screen search box
function closeSearch() {
  document.getElementById("myOverlay").style.display = "none";
}*/

/*const btn =
document.querySelector(".myBtn")
const searchBox =
document.querySelector(".fullScreen")
const closeBtn = 
document.querySelector(".closeBtn")
  
  
closeBtn.addEventListener("click", ()=>{
    searchBox.style.display = "none"
})
  
btn.addEventListener('click', ()=>{
    searchBox.style.display = "block"
})*/













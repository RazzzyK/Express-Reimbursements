function somefunction()
{
	let user = {
            username: document.getElementById('username').value,
            password: document.getElementById('password').value
        };
	let url = "/ReimbursementProject/LoginServlet";
	
	
	asynchFunction();
	
        async function asynchFunction() {
            let response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json;charaset=utf-8'
                },
                body: JSON.stringify(user),

            }).then((response) => {
	
				let result = response.json(); //response.text, response.formatDat, response.blob
				//Parse result back to ArrayList then can use it however
				//let result = await response.json;
				//console.log("Hello this is response.json: " + response.text);
				window.location.href = response.url;
				/*alert("Reloaded");
				location.reload();*/
                

            }).catch(error => {
                console.error(error);
            });

        }
	
}
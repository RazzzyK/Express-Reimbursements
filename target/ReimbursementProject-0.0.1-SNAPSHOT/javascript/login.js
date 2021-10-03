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

			window.location.href = response.url;
			//location.reload();
        }).catch(error => {
            console.error(error);
        });

    }
}
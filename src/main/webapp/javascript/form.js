function somefunction()
{
	let new_ticket = {
			amount: document.getElementById('amount').value,
            type: document.getElementById('type').value,
			/*blob: document.getElementById('reciept').value,*/
			description: document.getElementById('inputMessage').value
            
        };
	
	let url = "/ReimbursementProject/FormServlet";
	
	
	asynchFunction();
	
    async function asynchFunction() {
        let response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charaset=utf-8'
            },
            body: JSON.stringify(new_ticket),

        }).then((response) => {

			let result = response.json(); //response.text, response.formatDat, response.blob
			window.location.href = response.url;
			/*location.reload();*/
            

        }).catch(error => {
            console.error(error);
        })

    }
}

function formatCurrency(num) {
	num = num.toString().replace(/\$|\,/g,'');
	if(isNaN(num))
	num = "0";
	
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num*100+0.50000000001);
	cents = num%100;
	num = Math.floor(num/100).toString();
	
	if(cents<10)
	cents = "0" + cents;
	
	for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	num = num.substring(0,num.length-(4*i+3))+','+
	num.substring(num.length-(4*i+3));
	return (((sign)?'':'-') + '$' + num + '.' + cents);
}
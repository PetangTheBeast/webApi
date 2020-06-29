<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
       	<!--- <link rel="stylesheet" href="style.css"> --->
        <link rel="stylesheet" href="http://localhost:8080/KurzovniListky/WebContent/css/style1.css" >
        <title>
            Kurzovni Listky
        </title>
    </head>
    <body>
        
        <table id="table1" class="table">
            <thead>
                
            </thead>
            <tbody>
               
            </tbody>
        </table>
		<p><button type="button" onclick="getActualDataFromIPI()">Update</button></p>
        <script type="text/javascript">
            const tableBody = document.querySelector("#table1 > tbody");
            const tableHead = document.querySelector("#table1 > thead");
            function load(){
				
                getDatabase();
            }
			function getDatabase(){
				const request = new XMLHttpRequest();
				request.open("get", "http://localhost:8080/KurzovniListky/kurzlistky?attribute=true", false);
				request.onreadystatechange = () => {
                    try{
						console.log(request.responseText);
                        const json = JSON.parse(request.responseText);
                        printJson(json);
                    } catch (e){
                        console.warn("could not load json");
                    }

                };
                request.send();
			}
			function getActualDataFromIPI(){
				const request = new XMLHttpRequest();
				request.open("get", "http://localhost:8080/KurzovniListky/kurzlistky?attribute=false", false);
				request.onreadystatechange = () => {
                    try{
						console.log(request.responseText);
                        const json = JSON.parse(request.responseText);
                        printJson(json);
                    } catch (e){
                        console.warn("could not load json");
                    }

                };
                request.send();
			}
            function printJson(json){
                //console.log(json);
                while(tableBody.firstChild){
                    tableBody.removeChild(tableBody.firstChild);
                }
                tableHead.removeChild(tableHead.firstChild);
                const headRow = document.createElement("tr");
                for (let [key, value] of Object.entries(json[0])) {
                        
                        const newEl = document.createElement("th");
                        newEl.textContent = key;
                        headRow.appendChild(newEl);
                }
                tableHead.appendChild(headRow);
                json.forEach(element => {
                    console.log(element);
                    const newRow = document.createElement("tr");
                    for (let [key, value] of Object.entries(element)) {
                        console.log(`${key}: ${value}`);
                        const newEl = document.createElement("td");
                        newEl.textContent = value;
                        newRow.appendChild(newEl);
                    }
                    tableBody.appendChild(newRow);
                });
                
            }
            document.addEventListener("DOMContentLoaded", () => {load();})
        </script>
    </body>
</html>
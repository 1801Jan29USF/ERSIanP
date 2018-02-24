function pastTickets() {
    let url = 'http://localhost:8080/ERSProject1/PastTickets';

    //create new XMLHttpRequest object to facilitate posting to Tomcat Server
    let xhttp = new XMLHttpRequest();
    //when the request has changed states, the call back function is executed
    xhttp.onreadystatechange = function () {
        // response is in ready state
        if (xhttp.readyState === 4) {
            // 200 says response was a success
            if (xhttp.status === 200) {
                resp = JSON.parse(xhttp.responseText);
                if (resp !== null) {
                    let ul = document.getElementById("list");

                    for (i = 0; i < resp.length; i++) {
                        var table = document.getElementById("table");
                        var row = table.insertRow();
                        var cell1 = row.insertCell(0);
                        var cell2 = row.insertCell(1);
                        var cell3 = row.insertCell(2);
                        var cell4 = row.insertCell(3);
                        var cell5 = row.insertCell(4);
                        var cell6 = row.insertCell(5);


                        // Add some text to the new cells:
                        cell1.innerHTML = resp[0];
                        cell2.innerHTML = resp[1];
                        cell3.innerHTML = resp[2];
                        cell4.innerHTML = resp[3];
                        cell5.innerHTML = resp[4];
                        cell6.innerHTML = resp[5];



                    }
                }

            } else {
                console.log('Request for employee past tickets failed')
            }
        } else {
            console.log('Response is not ready yet')
        }
    }
    xhttp.open('POST', url);
    xhttp.send();
}

pastTickets();
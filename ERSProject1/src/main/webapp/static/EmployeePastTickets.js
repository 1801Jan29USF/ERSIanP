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
                if (resp !== null) {
                    resp = JSON.parse(xhttp.responseText);
                    for (i = 0; i < resp.length; i++) {
                        let ul = document.getElementById("list");
                        let li = document.createElement("li");
                        li.className = "list-group-item";
                        li.appendChild(document.createTextNode(resp[i]));
                        ul.classname = "list-group";
                        ul.appendChild(li);
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
    xhttp.send(request);
}

pastTickets();
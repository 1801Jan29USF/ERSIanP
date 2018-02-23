function allPastTickets() {
    let url = 'http://localhost:8080/ERSProject1/AllPastTickets';
    let amount = document.getElementById("amount").value;
    let description = document.getElementById("description").value;
    let type = document.getElementById("type").options[document.getElementById("type").selectedIndex].text

    let request = JSON.stringify([amount, description, type]);
    console.log(request);

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

                        //create a row for list with reinbursement message
                        let ul = document.getElementById("list");
                        let li = document.createElement("li");
                        li.className = "list-group-item";
                        li.appendChild(document.createTextNode(resp[i]));
                        ul.classname = "list-group";
                        ul.appendChild(li);

                        if (resp[i].includes("PENDING") {



                            //create the approve or deny selcect element and append it to list row element
                            let sel = document.createElement("SELECT")
                            var newListData = new Option("Approve", "Approve");
                            var newListData2 = new Option("Approve", "Approve");
                            sel.className = "form-control;
                            sel.appendChild(newListData);
                            sel.appendChild(newListData2);
                            li.appendChild(sel)


                            //add a submit button to the row 
                            var btn = document.createElement("BUTTON");
                            var t = document.createTextNode("SUBMIT");
                            btn.className = "btn btn-primary btn-lg"
                            btn.appendChild(t);
                            li.appendChild(btn);
                        }
                    }
                }

            } else {
                console.log('Request for all past tickets failed')
            }
        } else {
            console.log('Response is not ready yet')
        }
    }
    xhttp.open('POST', url);
    xhttp.send(request);
}

allPastTickets();
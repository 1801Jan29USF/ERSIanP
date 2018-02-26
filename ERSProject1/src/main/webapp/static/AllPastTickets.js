

function approveDeny(row) {

    let url = 'http://localhost:8080/ERSProject1/AllPastTickets';
    let choice = row.childNodes[0].selectedIndex;
    if (choice == 0) {
        choice = 1;
    }
    else {
        choice = 2;
    }
    let info = JSON.stringify([row.id, choice]);

    //create new XMLHttpRequest object to facilitate posting to Tomcat Server
    let xhttp = new XMLHttpRequest();
    //when the request has changed states, the call back function is executed
    xhttp.onreadystatechange = function () {
        // response is in ready state
        if (xhttp.readyState === 4) {
            // 200 says response was a success
            if (xhttp.status === 200) {

                allPastTickets();


            } else {
                console.log('Request for employee past tickets failed')
            }
        } else {
            console.log('Response is not ready yet')
        }
    }
    xhttp.open('POST', url);
    xhttp.send(info);

}








function allPastTickets() {
    let url = 'http://localhost:8080/ERSProject1/AllPastTickets';

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
                    console.log(resp);
                    let i = 0;
                    let table = document.getElementById("table");
                    let id;
                    while (i < resp.length) {
                        var row = table.insertRow();
                        var cell1 = row.insertCell(0);
                        var cell2 = row.insertCell(1);
                        var cell3 = row.insertCell(2);
                        var cell4 = row.insertCell(3);
                        var cell5 = row.insertCell(4);
                        var cell6 = row.insertCell(5);
                        var cell7 = row.insertCell(6);
                        var cell8 = row.insertCell(7);
                        // Add some text to the new cells:
                        cell1.innerHTML = resp[i];
                        cell2.innerHTML = resp[i + 1];
                        cell3.innerHTML = resp[i + 2];
                        cell4.innerHTML = resp[i + 3];
                        cell5.innerHTML = resp[i + 4];
                        cell6.innerHTML = resp[i + 5];
                        i_d = resp[i + 8];
                        if (resp[i + 6] === '0') {
                            cell7.innerHTML = "<form method='POST' onsubmit='event.preventDefault(); approveDeny(this)' id = " + i_d + "><select class='form-control' id='approvedeny'><option value='one'>"
                                + "Approve</option><option value='two'>Deny</option></select> <br><button type='submit'"
                                + "class='btn btn-primary' name = 'name' value = 'exrequest'>Approve/Deny</button> </form> ";
                        }
                        else {
                            if (resp[i + 5] === "1") {
                                cell6.innerHTML = "Approved";
                            }
                            if (resp[i + 5] === "2") {
                                cell6.innerHTML = "Denied";
                            }
                        }
                        if (resp[i + 7] === "0") {
                            cell8.innerHTML = "Other";
                        }
                        if (resp[i + 7] === "1") {
                            cell8.innerHTML = "Lodging";
                        }
                        if (resp[i + 7] === "2") {
                            cell8.innerHTML = "Travel";
                        }
                        if (resp[i + 7] === "3") {
                            cell8.innerHTML = "Food";
                        }

                        i = i + 9;
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

allPastTickets();
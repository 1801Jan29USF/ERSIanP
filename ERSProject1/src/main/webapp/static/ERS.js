
let id;

function submitRequest() {
    let url = 'http://localhost:8080/ERSProject1/SubmitRequest/' + id;
    let amount = document.getElementById("amount").value;
    let description = document.getElementById("description").value;
    let request = JSON.stringify([amount, description]);

    //create new XMLHttpRequest object to facilitate posting to Tomcat Server
    let xhttp = new XMLHttpRequest();
    //when the request has changed states, the call back function is executed
    xhttp.onreadystatechange = function () {
        // response is in ready state
        if (xhttp.readyState === 4) {
            // 200 says response was a success
            if (xhttp.status === 200) {

                //notify the user that the request has been set
                //TODO

            } else {
                console.log('Request for login failed')
            }
        } else {
            console.log('Response is not ready yet')
        }
    }
    xhttp.open('POST', url);
    xhttp.send(request);
}

function profile() {
    let url = 'http://localhost:8080/ERSProject1/Profile/' + id;

    //create new XMLHttpRequest object to facilitate posting to Tomcat Server
    let xhttp = new XMLHttpRequest();
    //when the request has changed states, the call back function is executed
    xhttp.onreadystatechange = function () {
        // response is in ready state
        if (xhttp.readyState === 4) {
            // 200 says response was a success
            if (xhttp.status === 200) {
                respArr = JSON.parse(xhttp.responseText);
                document.getElementById("username").innerText = 'ERS Username: ' + respArr[0];
                document.getElementById("username").innerText = 'ERS Password: ' + respArr[1];
                document.getElementById("username").innerText = 'First Name: ' + respArr[2];
                document.getElementById("username").innerText = 'Last Name' + respArr[3];
                document.getElementById("username").innerText = 'Email' + respArr[4];
                document.getElementById("username").innerText = 'Position' + respArr[5];


            } else {
                console.log('Request for login failed')
            }
        } else {
            console.log('Response is not ready yet')
        }
    }
    xhttp.open('POST', url);
    xhttp.send(credentials);
}


function verifyAndLogin() {
    let url = 'http://localhost:8080/ERSProject1/Login';
    username = document.getElementById("enteredUsername").value;
    password = document.getElementById("enteredPassword").value
    let credentials = JSON.stringify([username, password]);


    //create new XMLHttpRequest object to facilitate posting to Tomcat Server
    let xhttp = new XMLHttpRequest();
    //when the request has changed states, the call back function is executed
    xhttp.onreadystatechange = function () {
        // response is in ready state
        if (xhttp.readyState === 4) {
            // 200 says response was a success
            if (xhttp.status === 200) {
                respArr = JSON.parse(xhttp.responseText);
                id = respArr[0];
                //notify the user that he has been logged it
                //TODO
                if (respArr[1] === 'Employee') {
                    window.location = 'http://localhost:8080/ERSProject1/EmployeeHome/' + id;
                }
                else {
                    window.location = 'http://localhost:8080/ERSProject1/ManagerHome/' + id;
                }

            } else {
                console.log('Request for login failed')
            }
        } else {
            console.log('Response is not ready yet')
        }
    }
    xhttp.open('POST', url);
    xhttp.send(credentials);
}
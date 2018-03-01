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
                resp = JSON.parse(xhttp.responseText);
                console.log(resp);
                if (resp !== "") {
                    if (resp === 0) {

                        window.location = 'http://localhost:8080/ERSProject1/EmployeeProfile';
                    }
                    else {
                        window.location = 'http://localhost:8080/ERSProject1/ManagerProfile';
                    }
                }
                else {
                    alert("User credentials could not be found please try again.");
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
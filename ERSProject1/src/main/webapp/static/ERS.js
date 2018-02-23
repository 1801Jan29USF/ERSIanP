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
                if (resp !== "") {
                    if (resp === 'Employee') {

                        window.location = 'http://localhost:8080/ERSProject1/EmployeeHome';
                    }
                    else {
                        window.location = 'http://localhost:8080/ERSProject1/ManagerHome';
                    }
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
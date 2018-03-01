function loadProfile() {
    let url;

    if (document.url === 'http://localhost:8080/ERSProject1/ManagerProfile') {
        url = 'http://localhost:8080/ERSProject1/ManagerProfile';
    }
    else {
        url = 'http://localhost:8080/ERSProject1/EmployeeProfile'
    }

    //create new XMLHttpRequest object to facilitate posting to Tomcat Server
    let xhttp = new XMLHttpRequest();
    //when the request has changed states, the call back function is executed
    xhttp.onreadystatechange = function () {
        // response is in ready state
        if (xhttp.readyState === 4) {
            // 200 says response was a success
            if (xhttp.status === 200) {
                respArr = JSON.parse(xhttp.responseText);
                console.log(respArr);
                document.getElementById("username").innerText = 'Username:     ' + respArr[0];
                document.getElementById("password").innerText = 'Password:     ' + respArr[1];
                document.getElementById("firstname").innerText = 'First Name:     ' + respArr[2];
                document.getElementById("lastname").innerText = 'Last Name:     ' + respArr[3];
                document.getElementById("email").innerText = 'Email:     ' + respArr[4];
                document.getElementById("position").innerText = 'Position:     ' + respArr[5];


            } else {
                console.log('Request for login failed')
            }
        } else {
            console.log('Response is not ready yet')
        }
    }
    xhttp.open('POST', url);
    xhttp.send();
}
loadProfile();

function verifyAndLogin(username, password) {
    let url = 'http://localhost:8080/ers/static/Login';
    username = document.getElementById("Username").value;
    password = document.getElementById("Password").value


    //create new XMLHttpRequest object to facilitate posting to Tomcat Server
    let xhttp = new XMLHttpRequest();
    //when the request has changed states, the call back function is executed
    xhttp.onreadystatechange = function () {
        // response is in ready state
        if (xhttp.readyState === 4) {
            // 200 says response was a success
            if (xhttp.status === 200) {

                //convert credentials into a JSON string
                credentials = JSON.stringify([username, password]);

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
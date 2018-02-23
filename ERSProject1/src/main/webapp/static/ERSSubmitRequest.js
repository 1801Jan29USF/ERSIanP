
function submitRequest() {
    let url = 'http://localhost:8080/ERSProject1/SubmitRequest';
    let amount = document.getElementById("amount").value;
    let description = document.getElementById("description").value;
    let type = document.getElementById("type").options[document.getElementById("type").selectedIndex].text
    console.log("ajskldjflasd");

    let request = JSON.stringify([amount, description, type]);

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





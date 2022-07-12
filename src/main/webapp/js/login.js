/**
 *view-controller for login.html
 *
 *Broker
 *Rauthor Lars Steinhardt
 */

/**
 *register listeners
 */
$(document).ready(function(){
/**
 *Listener for submitting the form sends the login data to the web service
 */
$("#loginForm").submit(sendLogin);
/**
 *listener for button[Abmelden]
 */
$("#logoff").click(sendLogoff());
});

/*
*sends the login-request
*@param for the form with the usernane/password
*/

function sendLogin(form){
    form.preventDefault();
    $
        .ajax({
            url: "./resource/user/login",
            dataType: "text",
            type: "POST",
            data : $("#loginForm").serialize()
        })
        .done(function () {
            window.location.href = "./login.html";
        })
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 484) {
                $("message").text("Benutzername/Passwort unbekannt")
            }else{
                $("message").text("Es ist ein Fehler aufgetreten")
            }
        })
}

    /**
     *sends the logoff-request
     */
    function sendLogoff(){

        form.preventDefault();
        $
            .ajax({
                url: "./resource/user/logoff",
                dataType: "text",
                type: "DELETE"
            })
            .done(function () {
                window.location.href = "./login.html";
            })
            .fail(function (xhr, status, errorThrown) {
            })
    }
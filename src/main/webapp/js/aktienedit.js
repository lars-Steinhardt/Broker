/**
 * view-controller for aktienedit.html
 * @author Lars Steinhardt
 */
document.addEventListener("DOMContentLoaded", () => {
    readAktien();
});

/**
 * reads a aktien
 */
function  readAktien() {
    const aktienID = getQueryParam("id");
    fetch("./resource/aktien/read?id=" + aktienID)
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showAktien(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * show the data of a aktien
 * @param data
 */

function showAktien(data){
    document.getElementById("aktienID").value = data.aktienID;
    document.getElementById("isin").value = data.isin;
    document.getElementById("brokerID").value = data.brokerID;
    document.getElementById("kurs").value = data.kurs;
    document.getElementById("volumen").value = data.volumen;
}

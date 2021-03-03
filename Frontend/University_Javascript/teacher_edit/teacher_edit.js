import {getParameterByName} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';

window.addEventListener('load', () => {
    const infoForm = document.getElementById('infoForm');
    const portraitForm = document.getElementById('portraitForm');

    infoForm.addEventListener('submit', event => updateInfoAction(event));
    portraitForm.addEventListener('submit', event => uploadPortraitAction(event));

    fetchAndDisplayTeacher();
});


/**
 * Fetches currently logged user's teachers and updates edit form.
 */
function fetchAndDisplayTeacher() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let response = JSON.parse(this.responseText);
            for (const [key, value] of Object.entries(response)) {
                let input = document.getElementById(key);
                if (input) {
                    input.value = value;
                }
            }
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/users/' + getParameterByName('user') + '/teachers/'
        + getParameterByName('teacher'), true);
    xhttp.send();
}

/**
 * Action event handled for updating teacher info.
 * @param {Event} event dom event
 */
function updateInfoAction(event) {
    event.preventDefault();

    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            fetchAndDisplayTeacher();
        }
    };
    xhttp.open("PUT", getBackendUrl() + '/api/users/' + getParameterByName('user') + '/teachers/'
        + getParameterByName('teacher'), true);

    const request = {
        'name': document.getElementById('name').value,
        'background': document.getElementById('background').value,
        'age': parseInt(document.getElementById('age').value)
    };

    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.send(JSON.stringify(request));
}

/**
 * Action event handled for uploading teacher portrait.
 * @param {Event} event dom event
 */
function uploadPortraitAction(event) {
    event.preventDefault();

    const xhttp = new XMLHttpRequest();
    xhttp.open("PUT", getBackendUrl() + '/api/users/' + getParameterByName('user') + '/teachers/'
        + getParameterByName('teacher') + '/portrait', true);

    let request = new FormData();
    request.append("portrait", document.getElementById('portrait').files[0]);

    xhttp.send(request);

}

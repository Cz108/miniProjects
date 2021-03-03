import {getParameterByName} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';

window.addEventListener('load', () => {
    const editUserForm = document.getElementById('editUserForm');

    editUserForm.addEventListener('submit', event => updateUserAction(event));

    fetchAndDisplayUser();
});

/**
 * Fetches currently logged user's users and updates edit form.
 */
function fetchAndDisplayUser() {
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
    xhttp.open("GET", getBackendUrl() + '/api/users/' + getParameterByName('user'), true);
//   xhttp.open("http://localhost:8181/api/users/kevin", ture);
    xhttp.send();
}
function updateUserAction(event) {
    event.preventDefault();

    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            fetchAndDisplayUser();
        }
    };
    xhttp.open("PUT", getBackendUrl() + '/api/users/' + getParameterByName('user'), true);

    const request = {
        'name': document.getElementById('name').value,
        'surname': document.getElementById('surname').value,
        'birthDate': document.getElementById('birthDate').value,
        'email': document.getElementById('email').value,
    };

    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.send(JSON.stringify(request));
}
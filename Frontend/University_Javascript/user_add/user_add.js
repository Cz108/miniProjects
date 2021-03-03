import {getParameterByName} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';


window.addEventListener('load', () => {
    const newUserForm = document.getElementById('newUserForm');

    newUserForm.addEventListener('submit', event => createUser(event));


});

function createUser(event) {
    event.preventDefault();

    const xhttp = new XMLHttpRequest();

    xhttp.open("POST", getBackendUrl() + '/api/users/', true);

    const request = {
        'login': document.getElementById('login').value,
        'name': document.getElementById('name').value,
        'surname': document.getElementById('surname').value,
        'birthDate': document.getElementById('birthDate').value,
        'password': document.getElementById('password').value,
        'email': parseInt(document.getElementById('email').value)
    };

    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.send(JSON.stringify(request));
}
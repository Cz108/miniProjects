import {getParameterByName} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';


window.addEventListener('load', () => {
    const newTeacherForm = document.getElementById('newTeacherForm');

    newTeacherForm.addEventListener('submit', event => createTeacher(event));

});

function createTeacher(event) {
    event.preventDefault();
    const xhttp = new XMLHttpRequest();
    xhttp.open("POST", getBackendUrl() + '/api/users/'+ getParameterByName('user') + '/teachers', true);
    const request = {
        'name': document.getElementById('name').value,
        'background': document.getElementById('background').value,
        'age': document.getElementById('age').value,
        'direction': document.getElementById('direction').value,
    };
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(JSON.stringify(request));

}
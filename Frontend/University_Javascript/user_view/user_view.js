import {
    getParameterByName,
    clearElementChildren,
    createLinkCell,
    createButtonCell,
    createTextCell,
    createImageCell,
    setTextNode
} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';

window.addEventListener('load', () => {
    fetchAndDisplayUser();
    fetchAndDisplayTeachers();
});

/**
 * Fetches all users and modifies the DOM tree in order to display them.
 */
function fetchAndDisplayTeachers() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayTeachers(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/users/' + getParameterByName('user') + '/teachers', true);
    xhttp.send();
}

/**
 * Updates the DOM tree in order to display teachers.
 *
 * @param {{teachers: {id: number, name:string}[]}} teachers
 */
function displayTeachers(teachers) {
    let tableBody = document.getElementById('tableBody');
    clearElementChildren(tableBody);
    teachers.teachers.forEach(teacher => {
        tableBody.appendChild(createTableRow(teacher));
    })
    let add_teacher = document.getElementById('add_teacher');
    add_teacher.href='../teacher_add/teacher_add.html?user='+getParameterByName('user');
}

/**
 * Creates single table row for entity.
 *
 * @param {{id: number, name: string}} teacher
 * @returns {HTMLTableRowElement}
 */
function createTableRow(teacher) {
    let tr = document.createElement('tr');
    tr.appendChild(createImageCell(getBackendUrl() + '/api/users/' + getParameterByName('user') + '/teachers/'
        + teacher.id + '/portrait'));
    tr.appendChild(createTextCell(teacher.name));
    tr.appendChild(createLinkCell('view', '../teacher_view/teacher_view.html?user='
        + getParameterByName('user') + '&teacher=' + teacher.id));
    tr.appendChild(createLinkCell('edit', '../teacher_edit/teacher_edit.html?user='
        + getParameterByName('user') + '&teacher=' + teacher.id));
    tr.appendChild(createButtonCell('delete', () => deleteCharacter(teacher.id)));
    return tr;
}

/**
 * Deletes entity from backend and reloads table.
 *
 * @param {number} teacher to be deleted
 */
function deleteCharacter(teacher) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
            fetchAndDisplayTeachers();
        }
    };
    xhttp.open("DELETE", getBackendUrl() + '/api/users/' + getParameterByName('user')
        + '/teachers/' + teacher, true);
    xhttp.send();
}


/**
 * Fetches single user and modifies the DOM tree in order to display it.
 */
function fetchAndDisplayUser() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayUser(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/users/' + getParameterByName('user'), true);
    xhttp.send();
}

/**
 * Updates the DOM tree in order to display user.
 *
 * @param {{login: string, name: string, surname:string}} user
 */
function displayUser(user) {
    setTextNode('username', user.login);
    setTextNode('name', user.name);
    setTextNode('surname', user.surname);
    setTextNode('birthDate', user.birthDate);
    setTextNode('email', user.email);

}

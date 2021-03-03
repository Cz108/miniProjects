import {clearElementChildren, createLinkCell, createButtonCell, createTextCell} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';

window.addEventListener('load', () => {
    fetchAndDisplayUsers();
});

/**
 * Fetches all users and modifies the DOM tree in order to display them.
 */
function fetchAndDisplayUsers() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayUsers(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/users', true);
//    xhttp.open("GET", 'http://localhost:8080/api/users', true);
    xhttp.send();
}

/**
 * Updates the DOM tree in order to display users.
 *
 * @param {{users: string[]}} users
 */
function displayUsers(users) {
    let tableBody = document.getElementById('tableBody');
    clearElementChildren(tableBody);
    users.users.forEach(user => {
        tableBody.appendChild(createTableRow(user));
    })
}

/**
 * Creates single table row for entity.
 *
 * @param {string} user
 * @returns {HTMLTableRowElement}
 */
function createTableRow(user) {
    let tr = document.createElement('tr');
    tr.appendChild(createTextCell(user));
    tr.appendChild(createLinkCell('view', '../user_view/user_view.html?user=' + user));
    tr.appendChild(createLinkCell('edit', '../user_edit/user_edit.html?user=' + user))
    tr.appendChild(createButtonCell('delete', () => deleteUser(user)));
    return tr;
}

/**
 * Deletes entity from backend and reloads table.
 *
 * @param {string } user to be deleted
 */
function deleteUser(user) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
            fetchAndDisplayUsers();
        }
    };
    xhttp.open("DELETE", getBackendUrl() + '/api/users/' + user, true);
    xhttp.send();
}

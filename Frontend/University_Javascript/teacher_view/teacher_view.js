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
    fetchAndDisplayTeacher();
});

function fetchAndDisplayTeacher() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayTeacher(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/teachers/'+ getParameterByName('teacher'), true);
    xhttp.send();


}
function displayTeacher(teacher) {
    setTextNode('teacher_name', teacher.name);
    let image = document.getElementById('image');
    image.src =  getBackendUrl() + '/api/teachers/'+ getParameterByName('teacher') + '/portrait/'
    setTextNode('name', teacher.name);
    setTextNode('background', teacher.background);
    setTextNode('age', teacher.age);
    setTextNode('direction', teacher.direction);

}

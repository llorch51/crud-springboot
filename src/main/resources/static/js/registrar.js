// Call the dataTables jQuery plugin
$(document).ready(function() {

});

async function registrarUsuarios() {
    let datos = {};
    datos.nombre = document.querySelector('#txtNombre').value.toUpperCase();
    datos.apellido = document.querySelector('#txtApellido').value.toUpperCase();
    datos.email = document.querySelector('#txtEmail').value.toLowerCase();
    datos.telefono = document.querySelector('#txtTelefono').value;
    datos.password = document.querySelector('#txtPassword').value;

    let repetirPassword = document.querySelector('#txtRepetirPassword').value;
    if (datos.password != repetirPassword) {
        alert('Las contraseñas no coinciden');
        return;
    }
    const request = await fetch('http://localhost:8080/api/usuarios', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    });
    alert('Usuario registrado con exito');
    window.location.href = 'login.html';

};

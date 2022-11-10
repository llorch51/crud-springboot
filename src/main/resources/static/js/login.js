// Call the dataTables jQuery plugin
$(document).ready(function() {

});

async function iniciarSesion() {
    let datos = {};
    datos.email = document.querySelector('#txtEmail').value;
    datos.password = document.querySelector('#txtPassword').value;


    const request = await fetch('api/login', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    });
    const respuesta = await request.text();//porque si hay datos devueltos

    if (respuesta != 'error') {
        localStorage.token = respuesta;
        localStorage.email = datos.email;
        window.location.href = 'usuarios.html';
    }else{
        alert('Usuario o contraseña incorrectos');
    }

};

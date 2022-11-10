// Call the dataTables jQuery plugin
$(document).ready(function() {
  cargarUsuarios()
  $('#usuarios').DataTable();
  actualizarEmailDelUsuario()
});

function actualizarEmailDelUsuario() {
  document.querySelector('#txt-email-usuario').innerHTML = localStorage.email;
}

async function cargarUsuarios() {
  const request = await fetch('http://localhost:8080/api/usuarios', {
    method: 'GET',
    headers: getHeaders()
  });
  const usuarios = await request.json()
  let listadoHTML = '';
  for (let usuario of usuarios) {
    let telefonoTexto = usuario.telefono == null ? '-' : usuario.telefono;
    let usuarioHTML =
        '<tr><td>'+usuario.id+'</td>' +
        '<td>'+usuario.nombre +' '+ usuario.apellido+'</td>' +
        '<td>'+usuario.email+'</td>' +
        '<td>'+telefonoTexto+'</td>' +
        '<td>' +
            '<a href="#" onclick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle btn-sm" style="">' +
            '<i class="fas fa-trash"></i>' +
            '</a>' +
          '</td></tr>';
    listadoHTML += usuarioHTML;
  }
  console.log(usuarios);


  document.querySelector('#usuarios tbody').outerHTML = listadoHTML;
};

function getHeaders() {
  return {
    'Accept': 'application/json',
    'Content-Type': 'application/json',
    'Authorization': localStorage.token
  }
}

async function eliminarUsuario(id) {
  if(!confirm('¿Está seguro de eliminar el usuario?')) {
    return;
  }
  const request = await fetch('http://localhost:8080/api/usuarios/' + id, {
    method: 'DELETE',
    headers: getHeaders()
  });
  location.reload();
}
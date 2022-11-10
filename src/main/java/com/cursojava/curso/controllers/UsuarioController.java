package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired//crea instancia de UsuarioDaoImpl para pasarsela a UsuarioDao(interfaz)
    private UsuarioDao usuarioDao;//se comparte objeto en memoria, para que no se esten creando mas
    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "usuarios/{id}", method = RequestMethod.GET)//cuando se llame a la ruta usuario/{id} se ejecutara el metodo
    public Usuario getUsuario(@PathVariable Long id){
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
        usuario.setEmail("llorch@gmail.com");
        usuario.setTelefono("123456789");
        return usuario;
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)//ruta
    public List<Usuario> getUsuarios(@RequestHeader(value="Authorization") String token){
        if(!validarToken(token)){
            return null;
        }
        return usuarioDao.getUsuarios();
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)//ruta
    public void registrarUsuarios(@RequestBody Usuario usuario){//convierte JSON que recibe en un objeto de tipo Usuario
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, usuario.getPassword());
        usuario.setPassword(hash);
        usuarioDao.registrarUsuarios(usuario);
    }

    @RequestMapping(value = "usuario123")
    public Usuario editar(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
        usuario.setEmail("llorch@gmail.com");
        usuario.setTelefono("123456789");
        return usuario;
    }

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value="Authorization") String token, @PathVariable Long id){
        if(!validarToken(token)){
            return;
        }
        usuarioDao.eliminar(id);
    }

    @RequestMapping(value = "usuario789")
    public Usuario buscar(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
        usuario.setEmail("llorch@gmail.com");
        usuario.setTelefono("123456789");
        return usuario;
    }

    private boolean validarToken(String token){
        String usuarioID = jwtUtil.getKey(token);
        return usuarioID != null;
    }
}

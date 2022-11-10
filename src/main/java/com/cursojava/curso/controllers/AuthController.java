package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)//ruta
    public String login(@RequestBody Usuario usuario){
        Usuario usuarioDB = usuarioDao.obtenerUsusarioPorCredenciales(usuario);
        if(usuarioDB != null){
           String tokenJWT = jwtUtil.create(String.valueOf(usuarioDB.getId()), usuarioDB.getEmail());//crea token
            return tokenJWT;
        }else{
            return "error";
        }
    }
}

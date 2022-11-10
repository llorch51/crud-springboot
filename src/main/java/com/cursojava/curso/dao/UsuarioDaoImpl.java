package com.cursojava.curso.dao;

import com.cursojava.curso.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository// va poder acceder a la base de datos
@Transactional
public class UsuarioDaoImpl implements UsuarioDao {//implementa la interfaz

    @PersistenceContext
    EntityManager entityManager;//conexion con la base de datos

    @Override
    public List<Usuario> getUsuarios() {
        String query = "FROM Usuario";//en clase usuario indicaremos a que tabla se refiere

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrarUsuarios(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public Usuario obtenerUsusarioPorCredenciales(Usuario usuario) {
        String query = "FROM Usuario WHERE email = :email";//se checa email por un lado y password por otro
        List<Usuario> lista = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                //.setParameter("password", usuario.getPassword())
                .getResultList();//si encuentra al usuario, lo mete en la lista. que sera de un solo elemento

        if(lista.isEmpty()) {//si la lista esta vacia, no encontro al usuario
            return null;
        }

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if( argon2.verify(lista.get(0).getPassword(), usuario.getPassword())){//verifica que el password que se recibe sea igual al que esta en la base de datos
            return lista.get(0);
        }
        return null;
    }


}

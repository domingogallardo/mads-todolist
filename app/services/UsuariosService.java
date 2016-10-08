package services;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import models.*;

public class UsuariosService {

    // El login de usuario no debe estar den la BD
    public static Usuario grabaUsuario(Usuario usuario) {
        Usuario usuarioIgualLogin = UsuarioDAO.findUsuarioPorLogin(usuario.login);
        if (usuarioIgualLogin != null) {
            return null;
        }
        else return UsuarioDAO.create(usuario);
    }

    public static Usuario modificaUsuario(Usuario usuario) {
        UsuarioDAO.update(usuario);
        return usuario;
    }

    public static Usuario findUsuario(String id) {
        return UsuarioDAO.find(id);
    }

    public static void deleteUsuario(String id) {
        UsuarioDAO.delete(id);
    }

    public static List<Usuario> findAllUsuarios() {
        List<Usuario> lista = UsuarioDAO.findAll();
        Logger.debug("Numero de usuarios: " + lista.size());
        return lista;
    }

    public static Usuario findUsuarioPorLogin(String login) {
        return UsuarioDAO.findUsuarioPorLogin(login);
    }

    public static Usuario login(String login, String password) {
        Usuario usuario = UsuarioDAO.findUsuarioPorLogin(login);
        if (usuario == null)
            return null;
        else if (!password.equals(usuario.password))
            return null;
        else return usuario;
    }

    public static Usuario registraUsuario(Usuario nuevoUsuario) {
        Usuario usuarioIgualLogin = UsuarioDAO.findUsuarioPorLogin(nuevoUsuario.login);
        if (usuarioIgualLogin == null || usuarioIgualLogin.password == null) {
            return grabaUsuario(nuevoUsuario);
        }
        // el login ya existe y tiene contraseña
        else return null;
    }
}

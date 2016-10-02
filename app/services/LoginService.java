package services;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import models.*;


public class LoginService {

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
            return UsuariosService.grabaUsuario(nuevoUsuario);
        }
        // el login ya existe y tiene contraseña
        else return null;
    }
}

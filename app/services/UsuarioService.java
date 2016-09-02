package services;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import java.util.List;
import java.util.Date;

public class UsuarioService {
    public static Usuario grabaUsuario(Usuario usuario) {
        return UsuarioDAO.create(usuario);
    }

    public static Usuario modificaUsuario(Usuario usuario) {
        // ToDo
    }

    public static Usuario findUsuario(String id) {
        // ToDo
    }

    public static boolean deleteUsuario(String id) {
        // ToDo
    }

    public static List<Usuario> findAllUsuarios() {
        // ToDo
    }
}

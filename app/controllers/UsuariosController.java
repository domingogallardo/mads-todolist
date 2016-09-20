package controllers;

import play.*;
import play.mvc.*;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.*;

import models.*;
import services.*;

import views.html.*;

import javax.inject.*;
import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class UsuariosController extends Controller {

    @Inject FormFactory formFactory;

    @Transactional(readOnly = true)
    public Result listaUsuarios() {
        List<Usuario> usuarios = UsuariosService.findAllUsuarios();
        return ok("Listado usuarios");
    }

    public Result formularioNuevoUsuario() {
        return ok(formCreacionUsuario.render(formFactory.form(Usuario.class),""));
    }

    @Transactional
    public Result grabaNuevoUsuario() {
        Form<Usuario> usuarioForm = formFactory.form(Usuario.class).bindFromRequest();
        if (usuarioForm.hasErrors()) {
            return badRequest(formCreacionUsuario.render(usuarioForm, "Hay errores en el formulario"));
        }
        Usuario usuario = usuarioForm.get();
        Logger.debug("Usuario a grabar: " + usuario.toString());
        usuario = UsuariosService.grabaUsuario(usuario);
        return ok("Usuario grabado");
    }
}

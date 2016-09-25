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
        String mensaje = flash("grabaUsuario");
        List<Usuario> usuarios = UsuariosService.findAllUsuarios();
        return ok(listaUsuarios.render(usuarios, mensaje));
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
        flash("grabaUsuario", "El usuario se ha grabado correctamente");
        return redirect(controllers.routes.UsuariosController.listaUsuarios());
    }

    @Transactional(readOnly = true)
    public Result detalleUsuario(String id) {
        Usuario usuario = UsuariosService.findUsuario(id);
        Logger.debug("Encontrado usuario " + usuario.id + ": " + usuario.login);
        return ok(detalleUsuario.render(usuario));
    }

    @Transactional
    public Result grabaUsuarioModificado() {
        return ok("No implementado");
    }

    @Transactional
    public Result editaUsuario(String id) {
        Usuario usuario = UsuariosService.findUsuario(id);
        if (usuario == null) {
            return ok("Usuario no encontrado");
        } else {
            Form<Usuario> usuarioForm = formFactory.form(Usuario.class);
            usuarioForm = usuarioForm.fill(usuario);
            return ok(formModificacionUsuario.render(usuarioForm, ""));
        }
    }
}

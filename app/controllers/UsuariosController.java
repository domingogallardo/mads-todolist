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
        String aviso = flash("aviso");
        String error = flash("error");
        Logger.debug("Menasaje de aviso: " + aviso);
        Logger.debug("Mensaje de error: " + error);
        List<Usuario> usuarios = UsuariosService.findAllUsuarios();
        return ok(listaUsuarios.render(usuarios, aviso, error));
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
        flash("aviso", "El usuario se ha grabado correctamente");
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
        Form<Usuario> usuarioForm = formFactory.form(Usuario.class).bindFromRequest();
        if (usuarioForm.hasErrors()) {
            return badRequest(formCreacionUsuario.render(usuarioForm, "Hay errores en el formulario"));
        }
        Usuario usuario = usuarioForm.get();
        Logger.debug("Usuario a grabar: " + usuario.toString());

        // Se recupera la contraseña del usuario y se actualiza en el
        // usuario modificado
        Usuario usuarioSinModificar = UsuariosService.findUsuario(usuario.id);
        usuario.password = usuarioSinModificar.password;

        usuario = UsuariosService.modificaUsuario(usuario);
        flash("aviso", "Usuario " + usuario.id + " modificado");
        return redirect(controllers.routes.UsuariosController.listaUsuarios());
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

    @Transactional
    public Result borraUsuario(String id) {
        Logger.debug("Voy a borrar el usuario: " + id);
        try {
            UsuariosService.deleteUsuario(id);
            flash("aviso", "Usuario " + id + " borrado correctamente");
        } catch (Exception ex) {
            String errorMsg = ex.getMessage();
            System.err.println("Excepción: " + errorMsg);
            flash("error", errorMsg);
        }
        return ok();
    }

    public Result formularioRegistro() {
        return ok(formRegistro.render(formFactory.form(Registro.class),""));
    }

    @Transactional
    public Result registroUsuario() {
        Form<Registro> registroForm = formFactory.form(Registro.class).bindFromRequest();
        if (registroForm.hasErrors()) {
            return badRequest(formRegistro.render(registroForm, "Hay errores en el formulario"));
        }
        Registro registro = registroForm.get();
        if (!registro.password.equals(registro.confirmacion)) {
            return badRequest(formRegistro.render(registroForm, "No coincide la contraseña y la confirmación"));
        }
        Usuario nuevoUsuario = new Usuario(registro.login, registro.password);
        nuevoUsuario = UsuariosService.registraUsuario(nuevoUsuario);
        if (nuevoUsuario == null) {
            return badRequest(formRegistro.render(registroForm, "El login ya existe"));
        } else return ok(saludo.render(registro.login));
    }

    public Result formularioLogin() {
        return ok(formLogin.render(formFactory.form(Login.class), ""));
    }

    @Transactional(readOnly = true)
    public Result login() {
        Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(formLogin.render(loginForm, "Hay errores en el formulario"));
        }
        Login login = loginForm.get();
        Usuario usuario = UsuariosService.login(login.login, login.password);
        if (usuario == null)
            return badRequest(formLogin.render(loginForm, "Error al logearse"));
        return ok(saludo.render(usuario.login + " - " + usuario.nombre));
    }
}

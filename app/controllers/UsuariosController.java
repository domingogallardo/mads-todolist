package controllers;

import play.mvc.*;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.*;

import models.*;

import views.html.*;

import javax.inject.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class UsuariosController extends Controller {

    @Inject FormFactory formFactory;

    @Transactional(readOnly = true)
    public Result listaUsuarios() {
        return ok("No implementado");
    }

    public Result formularioNuevoUsuario() {
        return ok(formCreacionUsuario.render(formFactory.form(Usuario.class),""));
    }

    @Transactional
    public Result grabaNuevoUsuario() {
        return ok("No implementado");
    }
}

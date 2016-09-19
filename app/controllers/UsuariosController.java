package controllers;

import play.mvc.*;
import play.db.jpa.*;

import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class UsuariosController extends Controller {

  @Transactional
  public Result formularioNuevoUsuario() {
      return ok("Resultado transaccional");
  }
}

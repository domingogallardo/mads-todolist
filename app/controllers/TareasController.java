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

public class TareasController extends Controller {

    @Transactional(readOnly = true)
    public Result listaTareas(Integer usuarioId) {
        String aviso = flash("aviso");
        String error = flash("error");
        Logger.debug("Menasaje de aviso: " + aviso);
        Logger.debug("Mensaje de error: " + error);
        List<Tarea> tareas = TareasService.listaTareasUsuario(usuarioId);
        String descripcion = "";
        for (Tarea t: tareas) {
            descripcion += " - " + t.descripcion;
        }
        return ok("Tareas: " + descripcion);
    }
}

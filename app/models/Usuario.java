package models;

import java.util.Date;
import javax.persistence.*;
import play.data.validation.Constraints;
import play.data.format.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected String id;
    protected String login;
    protected String nombre;
    protected String apellidos;
    protected String eMail;
    @Formats.DateTime(pattern="dd-MM-yyyy")
    protected Date fechaNacimiento;

    // No definimos un setter para id, porque será
    // generado automáticamente por la base de datos

    public String getId() {
      return id;
    }

    public void setLogin(String login) {
      this.login = login;
    }

    public String getLogin() {
      return login;
    }

    public void setNombre(String nombre) {
      this.nombre = nombre;
    }

    public String getNombre() {
      return nombre;
    }

    public void setApellidos(String apellidos) {
      this.apellidos = apellidos;
    }

    public String getApellidos() {
      return apellidos;
    }

    public void setEMail(String eMail) {
      this.eMail = eMail;
    }

    public String getEMail() {
      return eMail;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
      this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaNacimiento() {
      return fechaNacimiento;
    }

    // Sustituye por null todas las cadenas vacías que pueda tener
    // un usuario en sus atributos
    public void nulificaAtributos() {
        if (nombre != null && nombre.isEmpty()) nombre = null;
        if (apellidos != null && apellidos.isEmpty()) apellidos = null;
        if (eMail != null && eMail.isEmpty()) eMail = null;
    }

    public String toString() {
        String fechaStr = null;
        if (fechaNacimiento != null) {
            SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
            fechaStr = formateador.format(fechaNacimiento);
        }
        return String.format("Usuario id: %s login: %s nombre: %s " +
                      "apellidos: %s eMail: %s fechaNacimiento: %s",
                      id, login, nombre, apellidos, eMail, fechaStr);
    }
}

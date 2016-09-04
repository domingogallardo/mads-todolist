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

    // Una cadena vacía se guarda como null.
    // JPA guarda este valor en la BD.
    public void setLogin(String login) {
      this.login = (login != "") ? login : null;
    }

    public String getLogin() {
      return login;
    }

    public void setNombre(String nombre) {
      this.nombre = (nombre != "") ? nombre : null;
    }

    public String getNombre() {
      return nombre;
    }

    public void setApellidos(String apellidos) {
      this.apellidos = (apellidos != "") ? apellidos : null;
    }

    public String getApellidos() {
      return apellidos;
    }

    public void setEMail(String eMail) {
      this.eMail = (eMail != "") ? eMail : null;
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

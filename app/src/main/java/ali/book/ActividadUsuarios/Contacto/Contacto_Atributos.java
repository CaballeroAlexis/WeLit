package ali.book.ActividadUsuarios.Contacto;

/**
 * Created by Alexis on 25/10/2017.
 */

public class Contacto_Atributos {
    private int Avatar;
    private String Nombre;
    private String UltimoMensaje;
    private String HoraMensaje;

    private String id;

    public Contacto_Atributos(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAvatar() {
        return Avatar;
    }

    public void setAvatar(int avatar) {
        Avatar = avatar;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getUltimoMensaje() {
        return UltimoMensaje;
    }

    public void setUltimoMensaje(String ultimoMensaje) {
        UltimoMensaje = ultimoMensaje;
    }

    public String getHoraMensaje() {
        return HoraMensaje;
    }

    public void setHoraMensaje(String horaMensaje) {
        HoraMensaje = horaMensaje;
    }
}
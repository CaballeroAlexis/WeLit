package ali.book.ActividadUsuarios.Amigos;

import android.widget.ListView;

import java.util.List;

/**
 * Created by Alexis on 12/09/2017.
 */

public class Amigos_Atributos {
    private int Avatar;
    private String Nombre;
    private String UltimoMensaje;
    private String HoraMensaje;
    private String distancia;
    private ListView listalibros;
    private List<String> listadoCompleto;

    public List<String> getListadoCompleto() {
        return listadoCompleto;
    }

    public void setListadoCompleto(List<String> listadoCompleto) {
        this.listadoCompleto = listadoCompleto;
    }

    public ListView getListalibros() {
        return listalibros;
    }

    public void setListalibros(ListView listalibros) {
        this.listalibros = listalibros;
    }

    private String id;

    public Amigos_Atributos(){

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

    public String getDistancia() {return distancia;}

    public void setDistancia(String distancia) {this.distancia = distancia;}
}

package ali.book.Internet;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ali.book.Preferences;
import ali.book.R;
import ali.book.VolleyRP;

import static ali.book.Login.locationA;
import static ali.book.Login.locationB;

/**
 * Created by Alexis on 02/11/2017.
 */

public abstract class SolicitudesJson {
    public static final String URL_INSERT_CONTACTO="https://alexiswolfy.000webhostapp.com/ArchivosPHP/Contactos_INSERT.php";
    public final static String URL_GET_ALL_USUARIOS = "https://alexiswolfy.000webhostapp.com/ArchivosPHP/Amigos_GETALL.php?id=";
    public static final String URL_GET_ALL_MENSAJES_USUARIOS="https://alexiswolfy.000webhostapp.com/ArchivosPHP/Mensajes_GETID.php";
    public static final String URL_GET_ALL_LIBROS="https://alexiswolfy.000webhostapp.com/ArchivosPHP/Libros_GETALL.php?id=";
    public static final String URL_INSERT_AVATAR="https://alexiswolfy.000webhostapp.com/ArchivosPHP/Avatar_INSERT.php";
    public static final String URL_INSERT_LIBRO="https://alexiswolfy.000webhostapp.com/ArchivosPHP/Libros_INSERT.php";


    public abstract void SolicitudCompletada(JSONObject j);
    public abstract void SolicitudErronea();

    public SolicitudesJson(){}
    public  void solicitudJsonGET(Context c, String URL) {

        JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                SolicitudCompletada(datos);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                SolicitudErronea();
            }
        });
        VolleyRP.addToQueue(solicitud, VolleyRP.getInstance(c).getRequestQueue(), c, VolleyRP.getInstance(c));

    }

    public  void solicitudJsonPOST(Context c, String URL, HashMap h) {

        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST,URL, new JSONObject(h), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                SolicitudCompletada(datos);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                SolicitudErronea();
            }
        });
        VolleyRP.addToQueue(solicitud, VolleyRP.getInstance(c).getRequestQueue(), c, VolleyRP.getInstance(c));

    }











}

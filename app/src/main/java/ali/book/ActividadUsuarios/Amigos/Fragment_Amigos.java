package ali.book.ActividadUsuarios.Amigos;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ali.book.ActividadUsuarios.Contacto.ExpandAndCollapseViewUtil;
import ali.book.Internet.SolicitudesJson;
import ali.book.Preferences;
import ali.book.R;
import ali.book.VolleyRP;

import static ali.book.Login.locationA;
import static ali.book.Login.locationB;

/**
 * Created by Alexis on 12/09/2017.
 */

public class Fragment_Amigos extends Fragment{



    private String[] values = new String[]{"El viejo y el mar","El camino de los reyes", "Nacidos de la bruma"};
    private RecyclerView rv;
    private List<Amigos_Atributos> atributosList;
    private AmigosAdapter adapter;

    private VolleyRP volley;
    private RequestQueue mRequest;
    private static final String URL_GET_ALL_USUARIOS="https://alexiswolfy.000webhostapp.com/ArchivosPHP/Amigos_GETALL.php";
    private static final String URL_INSERT_CONTACTO="https://alexiswolfy.000webhostapp.com/ArchivosPHP/Contactos_INSERT.php";


    private double latitudb;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {


        View v= inflater.inflate(R.layout.fragment_amigos,container,false);
        volley= VolleyRP.getInstance(getContext());
        mRequest=volley.getRequestQueue();

        atributosList =new ArrayList<>();
        rv=(RecyclerView) v.findViewById(R.id.AmigosRecyclerView);
        LinearLayoutManager lm=new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        adapter= new AmigosAdapter(atributosList, getContext());
        rv.setAdapter(adapter);



        SolicitudJSON();

        return v;
    }




    public void agregarAmigo(int avatar, String nombre, String distancia, String hora, String id, List<String> listaLibros){
        Amigos_Atributos amigosAtributos= new Amigos_Atributos();
        amigosAtributos.setAvatar(avatar);
        amigosAtributos.setNombre(nombre);
        amigosAtributos.setDistancia(distancia);
        amigosAtributos.setId(id);
        amigosAtributos.setListadoCompleto(listaLibros);

        atributosList.add(amigosAtributos);
        adapter.notifyDataSetChanged();

    }



    public void SolicitudJSON(){
        JsonObjectRequest solicitud = new JsonObjectRequest(URL_GET_ALL_USUARIOS,null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse (JSONObject datos){
                try {
                    String UsuariosLibros=datos.getString("UsuariosConLibro");
                    String NuestroUsuario=Preferences.obtenerPreferenceString(getContext(),Preferences.USUARIO_PREFERENCES_LOGIN);
                    List<String> listUsuarios = new ArrayList<String>();


                    List<ArrayList<String>> listTotal = new ArrayList<ArrayList<String>>();

                   ArrayList<String> user = new ArrayList<String>();

                    JSONArray jsUserLibros =new JSONArray(UsuariosLibros); //5

                    JSONArray jsUserOrdenados;


                    //OBtener total de usuarios
                    for (int i=0; i<jsUserLibros.length();i++){
                        JSONObject js = jsUserLibros.getJSONObject(i);
                        if (!NuestroUsuario.equals(js.getString("id")) && !listUsuarios.contains(js.getString("id"))) {
                            listUsuarios.add(js.getString("id"));
                        }
                    }

                    //Agrupar libros con usuarios
                    for (int j=0; j<listUsuarios.size();j++) {
                        List<String> listLibros = new ArrayList<String>();
                        String id_usuario = listUsuarios.get(j);

                        for (int k = 0; k < jsUserLibros.length(); k++) {

                            JSONObject js = jsUserLibros.getJSONObject(k);
                            if (id_usuario.equals(js.getString("id"))) {
                                listLibros.add(js.getString("titulo"));
                                locationB.setLatitude(js.getDouble("latitud"));
                                locationB.setLongitude(js.getDouble("longitud"));


                            }
                        }

                        //Agregar valores a tarjetas
                        float distance = locationA.distanceTo(locationB);
                        int distancia = Math.round(distance / 1000) + 1;

                        agregarAmigo(R.drawable.ic_account_circle, id_usuario, String.valueOf(distancia) + " Km", "00:00", id_usuario, listLibros);

                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error al descomponer el json",Toast.LENGTH_SHORT ).show();
                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Ocurrió un error. Contacte con el administrador",Toast.LENGTH_SHORT ).show();
            }
        });
        VolleyRP.addToQueue(solicitud, mRequest,getContext(),volley);
    }




    public void toggleDetails(View v, ViewGroup vg, ImageView iv, TextView info, int size) {
        if (vg.getVisibility() == View.GONE) {
            ExpandAndCollapseViewUtil.expand(vg, 250,size);
            info.setText("-Información");
            rotate(-180.0f, iv);
        } else {
            ExpandAndCollapseViewUtil.collapse(vg, 250);
            info.setText("+Información");

            rotate(180.0f,iv);
        }
    }

    public void toggleDetailsLibros(View v, ViewGroup vg, ImageView iv, TextView info, int size) {
        if (vg.getVisibility() == View.GONE) {
            ExpandAndCollapseViewUtil.expand(vg, 250,size);
        } else {
            ExpandAndCollapseViewUtil.collapse(vg, 250);

        }
    }


    private void rotate(float angle, ImageView iv) {
        Animation animation = new RotateAnimation(0.0f, angle, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillAfter(true);
        animation.setDuration(250);
        iv.startAnimation(animation);
    }




}
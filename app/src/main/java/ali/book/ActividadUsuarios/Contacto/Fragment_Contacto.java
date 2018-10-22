package ali.book.ActividadUsuarios.Contacto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ali.book.ActividadUsuarios.Amigos.AmigosAdapter;
import ali.book.ActividadUsuarios.Amigos.Amigos_Atributos;
import ali.book.Internet.SolicitudesJson;
import ali.book.Preferences;
import ali.book.R;
import ali.book.VolleyRP;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ali.book.Preferences;
import ali.book.R;
import ali.book.VolleyRP;

/**
 * Created by Alexis on 12/09/2017.
 */

public class Fragment_Contacto extends Fragment {

    private RecyclerView rv;
    private List<Contacto_Atributos> atributosList;
    private List<Contacto_Atributos> listAuxiliar; //Todos los elementos
    private Contacto_Adapter adapter;
    private EditText filtro;

    private VolleyRP volley;
    private RequestQueue mRequest;
    private static final String URL_GET_ALL_USUARIOS="https://alexiswolfy.000webhostapp.com/ArchivosPHP/Contactos_GETALL.php?id=";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_contacto,container,false);
        volley= VolleyRP.getInstance(getContext());
        mRequest=volley.getRequestQueue();

        atributosList =new ArrayList<>();
        listAuxiliar = new ArrayList<>();
        rv=(RecyclerView) v.findViewById(R.id.ContactosRecyclerView);

        filtro=(EditText)v.findViewById(R.id.FiltrarContactos);
        LinearLayoutManager lm=new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        adapter=new Contacto_Adapter(atributosList,getContext());

        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        SolicitudJSON();

        filtro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtro(""+s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }






    public void agregarAmigo(int avatar, String nombre, String ultimoMensaje, String hora, String id){
        Contacto_Atributos contacto_atributos= new Contacto_Atributos();
        contacto_atributos.setAvatar(avatar);
        contacto_atributos.setNombre(nombre);
        //contacto_atributos.setUltimoMensaje(ultimoMensaje);
        //contacto_atributos.setHoraMensaje(hora);
        contacto_atributos.setId(id);
        atributosList.add(contacto_atributos);
        listAuxiliar.add(contacto_atributos);
        adapter.notifyDataSetChanged();

    }

    public void filtro(String texto){
        atributosList.clear();
        for(int i=0;i<listAuxiliar.size();i++){
            if(listAuxiliar.get(i).getNombre().toLowerCase().contains(texto.toLowerCase())){

                atributosList.add(listAuxiliar.get(i));

            }
        }
        adapter.notifyDataSetChanged();

    }


    public void SolicitudJSON(){
        final String NuestroUsuario=Preferences.obtenerPreferenceString(getContext(),Preferences.USUARIO_PREFERENCES_LOGIN);

        JsonObjectRequest solicitud = new JsonObjectRequest(URL_GET_ALL_USUARIOS+NuestroUsuario,null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse (JSONObject datos){
                try {

                    ListarLibros.solicitudJsonGET(getContext(),SolicitudesJson.URL_GET_ALL_LIBROS+NuestroUsuario);

                    String TodosLosDatos=datos.getString("Resultado");
                    JSONArray jsUsersTokens =new JSONArray(TodosLosDatos);

                    for(int i=0; i<jsUsersTokens.length();i++){
                        JSONObject js =jsUsersTokens.getJSONObject(i);

                        agregarAmigo(R.drawable.ic_account_circle, js.getString("id_contacto"), "Nada", "00:00", js.getString("id_contacto"));
                        adapter.notifyDataSetChanged();

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



    SolicitudesJson ListarLibros =new SolicitudesJson() {
        @Override
        public void SolicitudCompletada(JSONObject j) {
            String[] libros= new String[]{};

        }


        public void SolicitudErronea() {
            return ;
        }
    };
}

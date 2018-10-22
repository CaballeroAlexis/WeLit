package ali.book.ActividadUsuarios.Perfil;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.HashMap;

import ali.book.Internet.SolicitudesJson;
import ali.book.Preferences;
import ali.book.R;
import ali.book.VolleyRP;

/**
 * Created by Alexis on 15/11/2017.
 */

public class Nuevo_Libro extends AppCompatActivity{
    private EditText NuevoTitulo;
    private Button NuevoLibroEnviar;
    private VolleyRP volley;
    private RequestQueue mRequest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_libro);
        NuevoTitulo= (EditText) findViewById(R.id.NuevoLibroTitulo);
        NuevoLibroEnviar= (Button) findViewById(R.id.EnviarLibro);
        volley= VolleyRP.getInstance(this);
        mRequest=volley.getRequestQueue();


        NuevoLibroEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!NuevoTitulo.getText().toString().isEmpty()){

                    SubirLibro(NuevoTitulo.getText().toString());
                    finish();


                }



            }

        });

    }



    private void SubirLibro(String titulo){
        HashMap<String, String> hs= new HashMap<>();
        hs.put("id_usuario",Preferences.obtenerPreferenceString(this, Preferences.USUARIO_PREFERENCES_LOGIN));
        hs.put("titulo",NuevoTitulo.getText().toString());
        hs.put("foto","0");
        hs.put("description","Nuevo Libro agregado automaticamente");



        s.solicitudJsonPOST(Nuevo_Libro.this,SolicitudesJson.URL_INSERT_LIBRO,hs);


    }


    SolicitudesJson s =new SolicitudesJson() {
        @Override
        public void SolicitudCompletada(JSONObject j) {

        }

        @Override
        public void SolicitudErronea() {
        }
    };

}

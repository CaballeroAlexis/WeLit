package ali.book.Mensaje;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ali.book.Internet.SolicitudesJson;
import ali.book.Login;
import ali.book.Preferences;
import ali.book.R;
import ali.book.Servicios.Registro;
import ali.book.VolleyRP;

/**
 * Created by Alexis on 23/08/2017.
 */

public class Mensajeria extends AppCompatActivity{
    public static final String MENSAJE="MENSAJE";
    private BroadcastReceiver bR;
    private RecyclerView rv;
    private Button BtEnviarMensaje;
    private EditText EscribirMensaje;

    private List<MensajeDeTexto> mensajeDeTextos;
    private MensajesAdapter adapter;
    private String Mensaje_Enviar="";
    private String EMISOR = "";
    private String RECEPTOR;
    private static final String IP_MENSAJE="https://alexiswolfy.000webhostapp.com/ArchivosPHP/Enviar_mensajes.php";

    private VolleyRP volley;
    private RequestQueue mRequest;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensajeria);
        BtEnviarMensaje= (Button) findViewById(R.id.BtEnviarMensaje);
        EscribirMensaje= (EditText) findViewById(R.id.EscribirMensaje);

        mensajeDeTextos= new ArrayList<>();
        volley= VolleyRP.getInstance(this);
        mRequest=volley.getRequestQueue();

        EMISOR = Preferences.obtenerPreferenceString(this,Preferences.USUARIO_PREFERENCES_LOGIN);
        Intent i= getIntent();
        Bundle bundle=i.getExtras();
        if(bundle!=null){
            RECEPTOR=bundle.getString("key_receptor");
        }


        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        rv=(RecyclerView) findViewById(R.id.rvMensajes);
        LinearLayoutManager lm=new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        rv.setLayoutManager(lm);





        adapter= new MensajesAdapter(mensajeDeTextos,this);
        rv.setAdapter(adapter);



        BtEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje=EscribirMensaje.getText().toString().trim();
                String TOKEN= FirebaseInstanceId.getInstance().getToken();

                if(!mensaje.isEmpty() && !RECEPTOR.isEmpty()) {
                    Mensaje_Enviar=mensaje;
                    EnviarMensaje();
                    CreateMensaje(mensaje,"00:00",1);
                    EscribirMensaje.setText("");

                }
                if(TOKEN!=null) {
                    Toast.makeText(Mensajeria.this, TOKEN, Toast.LENGTH_SHORT).show();
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setScrollbarChat();
        bR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String mensaje=intent.getStringExtra("key_mensaje");
                String hora=intent.getStringExtra("key_hora");
                String horaParametro[]=hora.split("\\,");
                String emisor=intent.getStringExtra("key_emisorPHP");
                if(emisor.equals(RECEPTOR)) {
                    CreateMensaje(mensaje, horaParametro[0], 2);
                }


            }
        };


        SolicitudesJson s =new SolicitudesJson() {
            @Override
            public void SolicitudCompletada(JSONObject j) {
                try {
                    JSONArray js= j.getJSONArray("resultado");
                    for(int i=0; i<js.length();i++) {
                        JSONObject jo=js.getJSONObject(i);
                        String mensaje=jo.getString("mensaje");
                        String hora=jo.getString("hora_del_mensaje").split(",")[0];
                        int tipoMensaje=jo.getInt("tipo_mensaje");

                        CreateMensaje(mensaje, hora, tipoMensaje);

                    }

                } catch (JSONException e) {
                    Toast.makeText(Mensajeria.this, "Error al descomprimir los mensajes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void SolicitudErronea() {

            }
        };
        HashMap <String, String> hs= new HashMap<>();
        hs.put("emisor",EMISOR);
        hs.put("receptor",RECEPTOR);
        s.solicitudJsonPOST(this,SolicitudesJson.URL_GET_ALL_MENSAJES_USUARIOS,hs);
    }




    private void EnviarMensaje(){
        HashMap<String, String> hasmaptoken=new HashMap<>();
        hasmaptoken.put("emisor",EMISOR);
        hasmaptoken.put("receptor",RECEPTOR);
        hasmaptoken.put("mensaje",Mensaje_Enviar);

        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST,IP_MENSAJE,new JSONObject(hasmaptoken), new Response.Listener<JSONObject>(){
            @Override
            public void onResponse (JSONObject datos){




            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Mensajeria.this, "Error inesperado",Toast.LENGTH_SHORT ).show();
            }
        });
        VolleyRP.addToQueue(solicitud, mRequest,this,volley);
    }

    public void CreateMensaje(String mensaje,String hora, int tipoMensaje){
        MensajeDeTexto mensajeAuxiliar= new MensajeDeTexto();
        mensajeAuxiliar.setId("0");
        mensajeAuxiliar.setMensaje(mensaje);
        mensajeAuxiliar.setTipoMensaje(tipoMensaje);
        mensajeAuxiliar.setHoraDelMensaje(hora);
        mensajeDeTextos.add(mensajeAuxiliar);
        adapter.notifyDataSetChanged();
        setScrollbarChat();
    }
    @Override
    protected void onPause(){
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(bR, new IntentFilter(MENSAJE));
    }

    public void setScrollbarChat(){
        rv.scrollToPosition(adapter.getItemCount()-1);

    }




}

package ali.book;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ali.book.ActividadUsuarios.Activity_Usuarios;
import ali.book.Servicios.Registro;

public class Login extends AppCompatActivity {
    private EditText EtUsuario;
    private EditText EtContraseña;
    private Button BtIngresar;
    private Button BtRegistrar;
    private static final String IP="https://alexiswolfy.000webhostapp.com/ArchivosPHP/Login_GETID.php?id=";
    private static final String IP_TOKEN="https://alexiswolfy.000webhostapp.com/ArchivosPHP/Token_INSERT_UPDATE.php";
    private static final String IP_CONTACTO="https://alexiswolfy.000webhostapp.com/ArchivosPHP/Contactos_CREATE.php";
    public static  Location locationA = new Location("punto A");
    public static  Location locationB = new Location("punto B");

    private RadioButton RBsesion;
    private String USER="";
    private String PASS="";
    private VolleyRP volley;
    private RequestQueue mRequest;
    private Boolean ActivateButton;



    public double lat;
    public double lon;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        locationStart();
        if(Preferences.obtenerPreferenceBoolean(this,Preferences.PREFERENCE_STATE_BUTTON)){
            IniciarActividadSiguiente();


            //i.putExtra("key_emisor",USER);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
                locationStart();
            } else {
                locationStart();
            }
        }


        volley= VolleyRP.getInstance(this);
        mRequest=volley.getRequestQueue();
        EtUsuario=(EditText) findViewById(R.id.EtUsuario);
        EtContraseña=(EditText) findViewById(R.id.EtContraseña);
        BtIngresar= (Button) findViewById(R.id.BtIngresar);
        BtRegistrar=(Button) findViewById(R.id.BtRegistrar);

        RBsesion = (RadioButton) findViewById(R.id.RBSesion);
        ActivateButton=RBsesion.isChecked();

        RBsesion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (ActivateButton){
                    RBsesion.setChecked(false);
                }
                 ActivateButton=RBsesion.isChecked();
            }
        });


        BtIngresar.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick (View view) {

            VerificarLogin(EtUsuario.getText().toString(),EtContraseña.getText().toString());

        }

        });
        BtRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {

                Intent i=new Intent(Login.this, Registro.class);
                startActivity(i);
            }

        });


        }








    public void VerificarLogin(String user, String pass){
        USER =user;
        PASS =pass;
    SolicitudJSON(IP+user);
    }

    public void SolicitudJSON(String URL){
        JsonObjectRequest solicitud = new JsonObjectRequest(URL,null, new Response.Listener<JSONObject>(){
        @Override
        public void onResponse (JSONObject datos){
        VerificarPassword(datos);
    }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Ocurrió un error. Contacte con el administrador",Toast.LENGTH_SHORT ).show();
            }
        });
        VolleyRP.addToQueue(solicitud, mRequest,this,volley);
    }
    public void VerificarPassword(JSONObject datos){

        try {
            String estado= datos.getString("resultado");
            if(estado.equals("CC")){

                JSONObject Jsondatos= new JSONObject(datos.getString("datos"));
                String usuario= Jsondatos.getString("id");
                String contraseña=Jsondatos.getString("Password");
                if (usuario.equals(USER) && contraseña.equals(PASS)){
                    String Token=FirebaseInstanceId.getInstance().getToken();
                    if (Token!=null) SubirToken(Token);
                    else  Toast.makeText(this, "El token es nulo", Toast.LENGTH_SHORT).show();


                }
                else Toast.makeText(this, "Error en la contraeseña", Toast.LENGTH_SHORT).show();



            }
            else{
                Toast.makeText(this, estado,Toast.LENGTH_SHORT).show();
            }



        } catch (JSONException e) {
            Toast.makeText(this,"Error al iniciar sessión",Toast.LENGTH_SHORT).show();

        }
    }

    private void SubirToken(String token){
        HashMap<String, String> hasmaptoken=new HashMap<>();
        hasmaptoken.put("id",USER);
        hasmaptoken.put("token",token);
        hasmaptoken.put("latitud", String.valueOf(locationA.getLatitude()));
        hasmaptoken.put("longitud", String.valueOf(locationA.getLongitude()));
        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST,IP_TOKEN,new JSONObject(hasmaptoken), new Response.Listener<JSONObject>(){
            @Override
            public void onResponse (JSONObject datos){
                Preferences.savePreferenceBoolean(Login.this,RBsesion.isChecked(),Preferences.PREFERENCE_STATE_BUTTON);
                Preferences.savePreferenceString(Login.this,USER,Preferences.USUARIO_PREFERENCES_LOGIN);
                try {
                    Toast.makeText(Login.this,datos.getString("resultado"),Toast.LENGTH_SHORT).show();
                } catch (JSONException e){}
                IniciarActividadSiguiente();
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Error al subir token",Toast.LENGTH_SHORT ).show();
            }
        });
        VolleyRP.addToQueue(solicitud, mRequest,this,volley);
    }

    public void IniciarActividadSiguiente(){
        Intent i= new Intent(Login.this,Activity_Usuarios.class);
        startActivity(i);
        finish();

    }



    //Esta función es como el default de todos  los gps, no hace falta tocar nada en esta parte
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setLogin(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);


    }

    /* Aqui empieza la Clase Localizacion */

    //Esta función es la que te interesa
    public class Localizacion implements LocationListener {
        Login login;

        public Login getMainActivity() {
            return login;
        }

        public void setLogin(Login login) {
            this.login = login;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion


            /*Estas 4 variables se las declara al inicio de la clase. locationA es un elemento propio del GPS llamado Location y se lo declara al inicio del programa así:
            public static  Location locationA = new Location("punto A");
            public static  Location locationB = new Location("punto B");
          */

            lat=loc.getLatitude();
            lon=loc.getLongitude();
            locationA.setLatitude(lat);
            locationA.setLongitude(lon);



        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado

        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }



    }


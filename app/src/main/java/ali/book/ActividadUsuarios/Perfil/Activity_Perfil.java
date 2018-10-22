package ali.book.ActividadUsuarios.Perfil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ali.book.Internet.SolicitudesJson;
import ali.book.Preferences;
import ali.book.R;
import ali.book.VolleyRP;

/**
 * Created by Alexis on 12/11/2017.
 */

public class Activity_Perfil extends AppCompatActivity{

    private TextView nombre;
    private ImageView avatar;
    private Button AgregarLibros;
    private ArrayAdapter<String> adaptador;
    Intent i;
    Bitmap bmp;
    Bitmap Nuevobmp;
    final static int cons = 0;
    private VolleyRP volley;
    private RequestQueue mRequest;
    private ListView list;
    private static final String URL_GET_ALL_USUARIOS="https://alexiswolfy.000webhostapp.com/ArchivosPHP/Amigos_GETALL.php";

    private ArrayList<String> sistemas;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        volley= VolleyRP.getInstance(this);
        mRequest=volley.getRequestQueue();
        nombre = (TextView) findViewById(R.id.NombrePerfil);
        avatar = (ImageView) findViewById(R.id.AvatarPerfil);
        AgregarLibros = (Button) findViewById(R.id.AgregarLibro);

        nombre.setText(Preferences.obtenerPreferenceString(this, Preferences.USUARIO_PREFERENCES_LOGIN));
        list = (ListView)findViewById(R.id.listView);
        ObtenerLibros();



        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, cons);
            }

        });
        AgregarLibros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Activity_Perfil.this, "Agregar libro", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Activity_Perfil.this, Nuevo_Libro.class);

                finish();
                Intent v= getIntent();
                startActivity(v);
                startActivity(i);

            }

        });

        avatar.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View arg0) {

                //convertir imagen a bitmap
                avatar.buildDrawingCache();
                Bitmap bmap = avatar.getDrawingCache();

                //guardar imagen
                GuardarImagen savefile = new GuardarImagen();
                savefile.SaveImage(Activity_Perfil.this, bmap);
                return true;
            }
        });


    }


    SolicitudesJson s =new SolicitudesJson() {
        @Override
        public void SolicitudCompletada(JSONObject j) {

        }

        @Override
        public void SolicitudErronea() {

        }
    };

        protected void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode,resultCode,data);
            if(resultCode== Activity.RESULT_OK){

                Bundle ext = data.getExtras();
                bmp = (Bitmap)ext.get("data");
                Nuevobmp = (Bitmap)ext.get("data");

                int x = bmp.getWidth();
                int y = bmp.getHeight();
                int[] intArray = new int[x * y];
                Nuevobmp.getPixels(intArray, 0, x, 0, 0, x, y);
                Toast.makeText(this, String.valueOf(intArray), Toast.LENGTH_SHORT).show();
                avatar.setImageBitmap(Nuevobmp);

                HashMap<String, String> hs= new HashMap<>();
                hs.put("id",Preferences.obtenerPreferenceString(this, Preferences.USUARIO_PREFERENCES_LOGIN));
                hs.put("avatar",String.valueOf(intArray));
                s.solicitudJsonPOST(this,SolicitudesJson.URL_INSERT_AVATAR,hs);


            }



    }



    public void ObtenerLibros(){
        JsonObjectRequest solicitud = new JsonObjectRequest(URL_GET_ALL_USUARIOS,null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse (JSONObject datos){
                try {
                    String UsuariosLibros=datos.getString("UsuariosConLibro");
                    String NuestroUsuario=Preferences.obtenerPreferenceString(Activity_Perfil.this,Preferences.USUARIO_PREFERENCES_LOGIN);
                    ArrayList<String> listaLibros = new ArrayList<String>();

                    JSONArray jsUserLibros =new JSONArray(UsuariosLibros); //5


                    //OBtener los libros del usuario
                    for (int i=0; i<jsUserLibros.length();i++){
                        JSONObject js = jsUserLibros.getJSONObject(i);
                        if (NuestroUsuario.equals(js.getString("id"))) {

                            listaLibros.add(js.getString("titulo"));
                        }
                    }

                    adaptador = new ArrayAdapter<String>(Activity_Perfil.this, android.R.layout.simple_list_item_1, listaLibros);
                    list.setAdapter(adaptador);





                } catch (JSONException e) {
                    Toast.makeText(Activity_Perfil.this, "Error al descomponer el json",Toast.LENGTH_SHORT ).show();
                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Activity_Perfil.this, "Ocurrió un error. Contacte con el administrador",Toast.LENGTH_SHORT ).show();
            }
        });
        VolleyRP.addToQueue(solicitud, mRequest,Activity_Perfil.this,volley);
    }




}

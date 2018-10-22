package ali.book.Servicios;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ali.book.Login;
import ali.book.Mensaje.Mensajeria;
import ali.book.R;
import ali.book.VolleyRP;

/**
 * Created by Alexis on 05/09/2017.
 */

public class Registro extends AppCompatActivity {

    private EditText user;
    private EditText password;
    private EditText nombre;
    private EditText apellido;
    private EditText dia;
    private EditText mes;
    private EditText año;
    private EditText email;
    private EditText telefono;
    private static final String IP_REGISTRAR="https://alexiswolfy.000webhostapp.com/ArchivosPHP/Registro_INSERT.php";
    private RadioButton hombre;
    private RadioButton mujer;
    private VolleyRP volley;
    private RequestQueue mRequest;

    private Button Registrar;





    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        user= (EditText) findViewById(R.id.UserRegistro);
        password= (EditText) findViewById(R.id.PasswordRegistro);
        nombre= (EditText) findViewById(R.id.NombreRegistro);
        apellido= (EditText) findViewById(R.id.ApellidoRegistro);
        dia= (EditText) findViewById(R.id.DíaRegistro);
        mes= (EditText) findViewById(R.id.MesRegistro);
        año= (EditText) findViewById(R.id.AñoRegistro);
        email= (EditText) findViewById(R.id.EmailRegistro);
        telefono= (EditText) findViewById(R.id.TelefonoRegistro);
        hombre= (RadioButton) findViewById(R.id.HombreRegistro);
        mujer= (RadioButton) findViewById(R.id.MujerRegistro);
        volley= VolleyRP.getInstance(this);
        mRequest=volley.getRequestQueue();

        hombre.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mujer.setChecked(false);
            }
        });
        mujer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                hombre.setChecked(false);
            }
        });


        Registrar =(Button) findViewById(R.id.BtTerminarRegistro);
        Registrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String genero="";

                if(hombre.isChecked()) genero="hombre";
                else if (mujer.isChecked()) genero="mujer";
                RegistrarWebService(
                        user.getText().toString().trim(),
                        password.getText().toString().trim(),
                        nombre.getText().toString().trim(),
                        apellido.getText().toString().trim(),
                        dia.getText().toString().trim()+"/"+mes.getText().toString().trim()+"/"+año.getText().toString().trim(),
                        email.getText().toString().trim(),
                        telefono.getText().toString().trim(),
                        genero);


            }

        });
    }
    private void RegistrarWebService(String usuario, String contraseña, String nombre, String apellido, String fechaNacimiento, String email, String numero, String genero){
        if(!usuario.isEmpty() &&
                !usuario.isEmpty() &&
                !contraseña.isEmpty() &&
                !nombre.isEmpty() &&
                !apellido.isEmpty() &&
                !fechaNacimiento.isEmpty()&&
                !email.isEmpty() &&
                !numero.isEmpty()) {


            HashMap<String, String> hasmaptoken = new HashMap<>();
            hasmaptoken.put("id", usuario);
            hasmaptoken.put("password", contraseña);
            hasmaptoken.put("Nombre", nombre);
            hasmaptoken.put("Apellido", apellido);
            hasmaptoken.put("FechaNacimiento", fechaNacimiento);
            hasmaptoken.put("Email", email);
            hasmaptoken.put("Telefono", numero);
            hasmaptoken.put("Genero", genero);

            JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST, IP_REGISTRAR, new JSONObject(hasmaptoken), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject datos) {
                    try {
                        String estado = datos.getString("Resultado");
                        if (estado.equalsIgnoreCase("Usuario registrado correctamente")) {
                            Toast.makeText(Registro.this, estado, Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Registro.this, estado, Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        Toast.makeText(Registro.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();


                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Registro.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                }
            });
            VolleyRP.addToQueue(solicitud, mRequest, this, volley);
        }else Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
    }
}

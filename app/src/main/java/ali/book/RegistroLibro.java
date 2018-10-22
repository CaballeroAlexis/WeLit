package ali.book;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ali.book.Utilidades.Utilidades;

/**
 * Created by Javier on 15/11/2017.
 */

public class RegistroLibro extends AppCompatActivity {
    EditText campoId, campoNombre, campoTelefono;
    Button Guardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        campoId= (EditText) findViewById(R.id.campo_id);
        campoNombre= (EditText) findViewById(R.id.campo_nombre);


        Guardar = (Button)findViewById(R.id.BtonGuardar);


        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {

                // registroSQL();
                registrarUsuarioSQL();
            }

        });
    }

    private void registrarUsuarioSQL() {
        ConexionSQLite con = new  ConexionSQLite(this, "libros",null,1);
        SQLiteDatabase db = con.getWritableDatabase();


        String insert="INSERT INTO "+ Utilidades.TABLA_LISTA_LIBROS+"( "+Utilidades.CAMPO_ID+","+Utilidades.CAMPO_NOMBRE+")"+
                " VALUES ("+campoId.getText().toString()+",'"+campoNombre.getText().toString()+"')";

        db.execSQL(insert);
        db.close();
        Toast.makeText(getApplicationContext(),"Se Guardo la nota ", Toast.LENGTH_SHORT).show();

    }


    private void registroSQL() {

        ConexionSQLite con = new  ConexionSQLite(this, "libros",null,1);
        SQLiteDatabase db = con.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_ID,campoId.getText().toString());
        values.put(Utilidades.CAMPO_NOMBRE,campoNombre.getText().toString());



        long idResultante=db.insert(Utilidades.TABLA_LISTA_LIBROS, Utilidades.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"id Registro: "+idResultante,Toast.LENGTH_SHORT).show();
        db.close();
    }

}



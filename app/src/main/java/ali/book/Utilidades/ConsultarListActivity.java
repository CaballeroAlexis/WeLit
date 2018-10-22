package ali.book.Utilidades;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ali.book.ConexionSQLite;
import ali.book.Entidades.Libro;
import ali.book.R;

public class ConsultarListActivity extends AppCompatActivity {
    ListView ListaViewLibros;
    ArrayList<String> listaInformacion;
    ArrayList<Libro> listaLibros;

    ConexionSQLite conn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_list);
        conn= new ConexionSQLite(getApplicationContext(),"libros",null,1);
        ListaViewLibros = (ListView)findViewById(R.id.ListaLibros);

        consultarListaAnotaciones();

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaInformacion);
        ListaViewLibros.setAdapter(adaptador);

    }

    private void consultarListaAnotaciones() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Libro libro =  null;
        listaLibros= new ArrayList<Libro>();
        //select * from libro
        Cursor cursor = db.rawQuery("SELECT * FROM "+Utilidades.TABLA_LISTA_LIBROS,null);

        while (cursor.moveToNext()){
            libro= new Libro();
            libro.setId(cursor.getInt(0));
            libro.setNombre(cursor.getString(1));

            listaLibros.add(libro);


        }
        obtenerLista();




    }

    private void obtenerLista() {

        listaInformacion = new ArrayList<String>();

        for (int i=0; i<listaLibros.size();i++){

            listaInformacion.add(listaLibros.get(i).getId()+" - "
                    +listaLibros.get(i).getNombre());
        }
    }
}
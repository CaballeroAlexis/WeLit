package ali.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ali.book.Utilidades.ConsultarListActivity;

/**
 * Created by Alexis on 14/11/2017.
 */

public class Anotaciones extends AppCompatActivity {


    private Button BOTON_UNO;
    private Button BOTON_DOS;
    private Button BOTON_TRES;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotaciones);
        ConexionSQLite con = new  ConexionSQLite(this, "db_usuarios",null,1);
        BOTON_UNO = (Button)findViewById(R.id.BOTON_UNO);
        BOTON_DOS = (Button)findViewById(R.id.BOTON_DOS);

        BOTON_UNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {

                Intent i=new Intent(Anotaciones.this, RegistroLibro.class);
                startActivity(i);
            }

        });

        BOTON_DOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {

                Intent i=new Intent(Anotaciones.this, ConsultarListActivity.class);
                startActivity(i);
            }

        });
       /* BOTON_DOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {

                Intent i=new Intent(MainActivity.this, ConsularActivity.class);
                startActivity(i);
            }

        });*/
    }
}

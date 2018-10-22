package ali.book.ActividadUsuarios;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ali.book.ActividadUsuarios.Perfil.Activity_Perfil;
import ali.book.Anotaciones;
import ali.book.Login;
import ali.book.Preferences;
import ali.book.R;

import static ali.book.Login.locationA;
import static java.security.AccessController.getContext;

/**
 * Created by Alexis on 17/09/2017.
 */

public class Activity_Usuarios extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("WeLit");
        setContentView(R.layout.activity_usuarios);
        tabLayout= (TabLayout)  findViewById(R.id.tabLayoutUsuarios);
        viewPager= (ViewPager) findViewById(R.id.ViewPagerUsuarios);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new Adapter_Usuarios(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               String valor= String.valueOf(Login.locationA.getLatitude())  + " "+ String.valueOf(Login.locationA.getLongitude()) ;

                if (position==0){
                    setTitle("Búsqueda");
                }else if (position==1){
                    setTitle("Contactos");
                }/*else if (position==2){
                    setTitle("Contactos");
                    Toast.makeText(Activity_Usuarios.this, "Estoy en el fragment 3", Toast.LENGTH_SHORT).show();
                }*/

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_amigos,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.CerrarSesion){
            Preferences.savePreferenceBoolean(Activity_Usuarios.this, false,Preferences.PREFERENCE_STATE_BUTTON);
            Intent i=new Intent(Activity_Usuarios.this, Login.class);
            startActivity(i);
            finish();
        }
        else if (id==R.id.VerPerfil){
            Intent i=new Intent(Activity_Usuarios.this, Activity_Perfil.class);
            startActivity(i);
        }

        else if (id==R.id.Anotaciones){
            Intent i=new Intent(Activity_Usuarios.this, Anotaciones.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


}

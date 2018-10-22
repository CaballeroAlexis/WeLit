package ali.book.ActividadUsuarios;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ali.book.ActividadUsuarios.Amigos.Fragment_Amigos;
import ali.book.ActividadUsuarios.Contacto.Fragment_Contacto;

/**
 * Created by Alexis on 17/09/2017.
 */

public class Adapter_Usuarios extends FragmentPagerAdapter {
    public Adapter_Usuarios(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new Fragment_Amigos();
        }else if (position==1){

            return new Fragment_Contacto();
        }/*else if(position==2){
            return new Fragment_Contacto();
        }*/
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

            if (position==0){


               return "Búsqueda";
            }else if (position==1){
                return "Contactos";
            }/*else if(position==2){
                return "Contactos";
            }*/
            return null;

    }
}

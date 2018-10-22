package ali.book.ActividadUsuarios.Amigos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import ali.book.ActividadUsuarios.Contacto.Contacto_Adapter;
import ali.book.ActividadUsuarios.Contacto.ExpandAndCollapseViewUtil;
import ali.book.ActividadUsuarios.Contacto.Fragment_Contacto;
import ali.book.Internet.SolicitudesJson;
import ali.book.Mensaje.Mensajeria;
import ali.book.Preferences;
import ali.book.R;

import static ali.book.R.id.imageViewExpand;

/**
 * Created by Alexis on 12/09/2017.
 */

public class AmigosAdapter extends RecyclerView.Adapter<AmigosAdapter.HolderAmigos> {


    String[] values = new String[]{"El viiiiejo y el mar","El camino de los reyes","Nacidos de la bruma","22","33","6090"};//,"Capitan America","Hulk","Thor","Black Widow","Ant man","Spider man"};
    private List<Amigos_Atributos> atributosList;
    private Context context;



    public AmigosAdapter(List<Amigos_Atributos> atributosList, Context context){
            this.atributosList=atributosList;
            this.context= context;
    }

    @Override
    public HolderAmigos onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_busqueda_usuarios,parent,false);
        return new AmigosAdapter.HolderAmigos(v);
    }

    @Override
    public void onBindViewHolder(final HolderAmigos holder, final int position) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, atributosList.get(position).getListadoCompleto());


        holder.ImageView.setImageResource(atributosList.get(position).getAvatar());
        holder.nombre.setText(atributosList.get(position).getNombre());
        holder.distancia.setText(atributosList.get(position).getDistancia());
        holder.listView.setAdapter(adapter);
        holder.listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                int item = position;
                String itemval = (String)holder.listView.getItemAtPosition(position);
                Fragment_Amigos fragmentAmigos= new Fragment_Amigos();

                fragmentAmigos.toggleDetailsLibros(view, holder.linearLayoutDetailsLibros,holder.ImageViewExpand, holder.expandible, atributosList.get(position).getListadoCompleto().size());

            }

        });






        holder.ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        holder.contactar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Mensajeria.class);
                i.putExtra("key_receptor", atributosList.get(position).getId());
                context.startActivity(i);
                HashMap<String, String> hs = new HashMap<>();
                HashMap<String, String> sh = new HashMap<>();
                hs.put("id", Preferences.obtenerPreferenceString(context, Preferences.USUARIO_PREFERENCES_LOGIN));
                hs.put("id_contacto", i.getStringExtra("key_receptor"));
                sh.put("id_contacto", Preferences.obtenerPreferenceString(context, Preferences.USUARIO_PREFERENCES_LOGIN));
                sh.put("id", i.getStringExtra("key_receptor"));
                nuevoContacto.solicitudJsonPOST(context, SolicitudesJson.URL_INSERT_CONTACTO, hs);
                nuevoContacto.solicitudJsonPOST(context, SolicitudesJson.URL_INSERT_CONTACTO, sh);

            }
        });



        holder.expandible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment_Amigos fragmentAmigos= new Fragment_Amigos();
               // ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, values);

                //holder.listView.setAdapter(adapter);
                fragmentAmigos.toggleDetails(v, holder.linearLayoutDetails,holder.ImageViewExpand, holder.expandible, atributosList.get(position).getListadoCompleto().size());

            }
        });
    }


    SolicitudesJson nuevoContacto =new SolicitudesJson() {
        @Override
        public void SolicitudCompletada(JSONObject j) {

        }
        public void SolicitudErronea() {
            return ;
        }
    };






    @Override
    public int getItemCount() {
        return atributosList.size();
    }

    static class HolderAmigos extends RecyclerView.ViewHolder{

        //CardViewNormal
        CardView cardView;
        ImageView ImageView;
        TextView nombre;
        TextView distancia;
        Button contactar;

        TextView expandible;
        TextView info;

        //Expandible
        ViewGroup linearLayoutDetails;
        ViewGroup linearLayoutDetailsLibros;

        ImageView ImageViewExpand;

        //Listado de libros
        ListView listView;
        String[] listaCompleta;


        public HolderAmigos(View itemView) {

            super(itemView);
            cardView= (CardView) itemView.findViewById(R.id.CardViewBusqueda);
            ImageView=(ImageView)  itemView.findViewById(R.id.AvatarBusqueda);
            nombre =(TextView) itemView.findViewById(R.id.NombreBusqueda);
            distancia=(TextView) itemView.findViewById(R.id.PerfilDistancia);
            contactar=(Button) itemView.findViewById(R.id.ContactarUsuario);
            expandible=(TextView) itemView.findViewById(R.id.Expandible);

            linearLayoutDetails = (ViewGroup) itemView.findViewById(R.id.linearLayoutDetails);
            ImageViewExpand = (ImageView) itemView.findViewById(imageViewExpand);

            listView=(ListView) itemView.findViewById(R.id.listView);


            linearLayoutDetailsLibros = (ViewGroup) itemView.findViewById(R.id.linearLayoutDetailsLibros);



        }
    }









}
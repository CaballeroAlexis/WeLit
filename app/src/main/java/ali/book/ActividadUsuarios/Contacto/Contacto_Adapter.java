package ali.book.ActividadUsuarios.Contacto;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ali.book.Mensaje.Mensajeria;

import ali.book.R;

/**
 * Created by Alexis on 12/09/2017.
 */
public class Contacto_Adapter extends  RecyclerView.Adapter<Contacto_Adapter.HolderContacto>{



    private List<Contacto_Atributos> atributosList;
    private Context context;

    public Contacto_Adapter(List<Contacto_Atributos> atributosList, Context context){
        this.atributosList=atributosList;
        this.context= context;
    }

    @Override
    public HolderContacto onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_contactos,parent,false);
        return new Contacto_Adapter.HolderContacto(v);
    }

    @Override
    public void onBindViewHolder(Contacto_Adapter.HolderContacto holder, final int position) {
        holder.ImageView.setImageResource(atributosList.get(position).getAvatar());
        holder.nombre.setText(atributosList.get(position).getNombre());
        //holder.mensaje.setText(atributosList.get(position).getUltimoMensaje());
       // holder.hora.setText(atributosList.get(position).getHoraMensaje());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(context,Mensajeria.class);
                i.putExtra("key_receptor",atributosList.get(position).getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return atributosList.size();
    }

    static class HolderContacto extends RecyclerView.ViewHolder{

        CardView cardView;
        android.widget.ImageView ImageView;
        TextView nombre;
        TextView mensaje;
        TextView hora;

        public HolderContacto(View itemView) {
            super(itemView);
            cardView= (CardView) itemView.findViewById(R.id.CardViewAmigos);
            ImageView=(ImageView)  itemView.findViewById(R.id.AvatarAmigo);
            nombre =(TextView) itemView.findViewById(R.id.NickAmigo);
          //  mensaje =(TextView) itemView.findViewById(R.id.MensajeAmigo);
          //  hora =(TextView) itemView.findViewById(R.id.HoraAmigo);

        }
    }
}

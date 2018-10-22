package ali.book.Mensaje;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ali.book.R;

/**
 * Created by Alexis on 28/08/2017.
 */

public class MensajesAdapter extends RecyclerView.Adapter<MensajesAdapter.MensajesViewHolder>{
    private List<MensajeDeTexto> mensajeDeTextos;
    private Context context;

    public MensajesAdapter(List<MensajeDeTexto> mensajeDeTextos, Context context) {
        this.mensajeDeTextos = mensajeDeTextos;
        this.context=context;

    }

    @Override
    public MensajesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_mensaje,parent,false);
        return new MensajesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MensajesViewHolder holder, int position) {
        RelativeLayout.LayoutParams rl=(RelativeLayout.LayoutParams) holder.cardView.getLayoutParams();
        FrameLayout.LayoutParams fl= (FrameLayout.LayoutParams)  holder.mensajeBG.getLayoutParams();
        LinearLayout.LayoutParams llMensaje=(LinearLayout.LayoutParams) holder.TvHora.getLayoutParams();
        LinearLayout.LayoutParams llHora=(LinearLayout.LayoutParams) holder.TvHora.getLayoutParams();


        if(mensajeDeTextos.get(position).getTipoMensaje()==1) { //1 Emisor.
            holder.mensajeBG.setBackgroundResource(R.drawable.in_message_bg);
            rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT,0);
            rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            llMensaje.gravity=Gravity.RIGHT;
            llHora.gravity=Gravity.RIGHT;
            fl.gravity= Gravity.RIGHT;
            holder.tvMensaje.setGravity(Gravity.RIGHT);

        }
        else if (mensajeDeTextos.get(position).getTipoMensaje()==2){ //2 Receptor
            holder.mensajeBG.setBackgroundResource(R.drawable.out_message_bg);
            rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,0);
            rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            llMensaje.gravity=Gravity.LEFT;
            llHora.gravity=Gravity.LEFT;
            fl.gravity= Gravity.LEFT;
            holder.tvMensaje.setGravity(Gravity.LEFT);
        }

            holder.mensajeBG.setLayoutParams(fl);
            holder.cardView.setLayoutParams(rl);
            holder.tvMensaje.setLayoutParams(llMensaje);
            holder.TvHora.setLayoutParams(llHora);

            holder.tvMensaje.setText(mensajeDeTextos.get(position).getMensaje());
            holder.TvHora.setText(mensajeDeTextos.get(position).getHoraDelMensaje());
        if(android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            holder.cardView.getBackground().setAlpha(0);
        }   else{
            holder.cardView.setBackgroundColor(ContextCompat.getColor(context,android.R.color.transparent));
        }
    }

    @Override
    public int getItemCount() {
        return mensajeDeTextos.size();
    }

    static class MensajesViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        LinearLayout mensajeBG;
        TextView tvMensaje;
        TextView TvHora;

        MensajesViewHolder(View itemView){
            super(itemView);
            mensajeBG=(LinearLayout) itemView.findViewById(R.id.mensajeBg);
            cardView= (CardView) itemView.findViewById(R.id.cvMensaje);
            tvMensaje= (TextView) itemView.findViewById(R.id.msTexto);
            TvHora= (TextView) itemView.findViewById(R.id.msHora);
        }

    }
}

package com.example.sistemas.appmolinotransporte.DespachoConductores.Adapter;

import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import android.graphics.Color;

import com.example.sistemas.appmolinotransporte.DespachoConductores.Interface.OnRecyclerViewItemClickListener;
import com.example.sistemas.appmolinotransporte.DespachoConductores.Model.Conductor;
import com.example.sistemas.appmolinotransporte.R;


/**
 * Created by developer on 15/12/17.
 */

public class NotifiConductorAdapter extends RecyclerView.Adapter<NotifiConductorAdapter.ViewHolder> implements View.OnClickListener {

    private List<Conductor> listConductor;
    private OnRecyclerViewItemClickListener<Conductor> itemClickListener;
    private int layout;


    public NotifiConductorAdapter(int layout) {
        this.layout = layout;
    }

    public void setListConductor(List<Conductor> listConductor)
    {
        this.listConductor = listConductor;
        notifyDataSetChanged();
        //listConductor.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Conductor item = listConductor.get(position);
        holder.itemView.setTag(item);
        holder.nameConductor.setText(item.getName()+" "+item.getApellido());
        holder.consecutivo.setText(item.getConsecutivo());
        holder.cedulaConductor.setText(item.getCedula());
        holder.placaVehiculo.setText(item.getPlaca());
        holder.horaInico.setText(item.getHourSatrt());
        //String despachar = "1";
        int estado = Integer.parseInt(item.getEstado());
        if (estado == 1) {
            holder.colorEstado.setBackgroundColor(Color.GREEN);
            holder.estado.setText("Por Despachar");
        }
        if (estado == 4)
        {
            holder.colorEstado
            .setBackgroundColor(Color.RED);
            holder.estado.setText("Despachando");
        }

    }


    @Override
    public int getItemCount() {
        //int count = listConductor.indexOf(listConductor.size());
        if (listConductor != null)
        {
            return listConductor.size();

        }
        else return 0;
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null )
        {
            Conductor model = (Conductor) v.getTag();
            itemClickListener.onItemClick(v,model);
        }

    }
    public void remove(Conductor item)
    {
        int position = listConductor.indexOf(item);
        listConductor.remove(position);
        notifyDataSetChanged();
    }
    public  int total()
    {
        return listConductor.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener<Conductor> listener)
    {
        this.itemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //declaracion de los elementos que se mostraran en la vista
        TextView nameConductor;
        TextView cedulaConductor;
        TextView placaVehiculo;
        TextView horaInico;
        TextView estado;
        TextView colorEstado;
        TextView consecutivo;
        CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            //casting de objetos que declaramaos
            nameConductor = (TextView) itemView.findViewById(R.id.nameCondutor);
            horaInico = (TextView) itemView.findViewById(R.id.horallegada);
            cedulaConductor = (TextView) itemView.findViewById(R.id.identification);
            placaVehiculo = (TextView) itemView.findViewById(R.id.numberPlaca);
            consecutivo =(TextView)itemView.findViewById(R.id.numberFactura);
            estado =(TextView)itemView.findViewById(R.id.textEspera);
            colorEstado =(TextView)itemView.findViewById(R.id.colorEstado);
            cardView = (CardView)itemView.findViewById(R.id.cardNoification);

        }
    }
}

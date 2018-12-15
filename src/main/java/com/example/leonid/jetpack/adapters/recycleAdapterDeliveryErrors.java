package com.example.leonid.jetpack.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.leonid.jetpack.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Objects.Delivery;
import Objects.DeliveryGuysShift;

public class recycleAdapterDeliveryErrors extends RecyclerView.Adapter<recycleAdapterDeliveryErrors.ViewHolder>{
    final static String TAG = "recycleAdaptererrors";
    private ArrayList<Delivery> deliveries;
    private ItemClickListener itemClickListener;

    public recycleAdapterDeliveryErrors(ArrayList<Delivery> objects, @NonNull recycleAdapterDeliveryErrors.ItemClickListener itemClickListener) {
        this.deliveries = objects;
        this.itemClickListener = itemClickListener;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        Context context = viewGroup.getContext();
        View parent = LayoutInflater.from(context).inflate(R.layout.adapter_layout_late_delivery, viewGroup, false);
        return ViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Delivery delivery = deliveries.get(position);

        try {
            viewHolder.setText(delivery);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewHolder.setImageOnClick(delivery);

    }

    @Override
    public int getItemCount() {
        return deliveries.size();
    }
    @Override
    public long getItemId(int position) {
        return deliveries.get(position).getIndex();
    }


    public static final class ViewHolder extends RecyclerView.ViewHolder {
        private final View parent;
        private final TextView textDisplay;
        private final ImageButton ib;


        public static ViewHolder newInstance(View parent) {

            TextView textDisplay = (TextView) parent.findViewById(R.id.late_delivery_text);
            ImageButton ib = parent.findViewById(R.id.imageButton);
            return new ViewHolder(parent, textDisplay,ib);
        }

        private ViewHolder(View parent,  TextView textDisplay,ImageButton ib) {
            super(parent);
            this.parent = parent;
            this.textDisplay = textDisplay;
            this.ib = ib;
        }


        public void setText(Delivery d) throws ParseException {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            String out = "" ;
            out += "#" + d.getIndexString()+ " ";
            out += d.getBusiness_name() + " ";
            if (d.getWas_late_deliveries())
            {
                Date arrived_to_costumer = df.parse(d.getTimeDeliver());
                Date time_of_order = df.parse(d.getTimeInserted());
                long time_late = (arrived_to_costumer.getTime() - time_of_order.getTime()) ;
                df = new SimpleDateFormat("mm");
                Date time_late_date = new Date(time_late);
                String time_late_string = df.format(time_late_date);

                out += "איחור ללקוח ";
                out += time_late_string;
            }
            else if(d.getWas_late_restoraunt())
            {
                Date arrived_to_rest = df.parse(d.getTimeArriveToRestoraunt());
                Date leave_restoraunt = df.parse(d.getTimeTaken());
//                long a = (leave_restoraunt.getTime() - arrived_to_rest.getTime());
//                Log.d(TAG,"delta in milis: " + a +" in min: " + a/(1000*60) );
                long time_late = (leave_restoraunt.getTime() - arrived_to_rest.getTime()) ;
                Date time_late_date = new Date(time_late);
                df = new SimpleDateFormat("mm");
                String time_late_string = df.format(time_late_date);
                Log.d(TAG,"time is : " + time_late_string );
                out += "עיכוב שליח ";
                out += time_late_string;
            }
            out += "דקות ";
            out += d.getDate();
            textDisplay.setText(out);
        }

        public void setImageOnClick(final Delivery d)
        {
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder =  new AlertDialog.Builder(view.getContext());
                    LayoutInflater li = LayoutInflater.from(view.getContext());
                    final View myView = li.inflate(R.layout.on_error_click_dialog, null);
                    //    final Dialog dialog = new Dialog(context,R.style.Dialog);


//                dialog.setContentView(R.layout.on_shift_click_dialog);
//                dialog.setTitle(shift.getName());
                    TextView index_order = (TextView) myView.findViewById(R.id.index_order);
                    index_order.setText("הזמנה:" + "#" + d.getIndexString());
                    TextView date_order = (TextView) myView.findViewById(R.id.date_order);
                    date_order.setText("תאריך: " + d.getDate());
                    TextView delivery_guy_info = (TextView) myView.findViewById(R.id.delivery_guy_info);
                    delivery_guy_info.setText("שליח: " + d.getDeliveryGuyName() + " טלפון שליח " + d.getDeliveryGuyPhone());
                    TextView restoraunt = (TextView) myView.findViewById(R.id.restoraunt);
                    restoraunt.setText("מסעדה: " + d.getBusiness_name());
                    TextView restoraunt_adress = (TextView) myView.findViewById(R.id.restoraunt_adress);
                    restoraunt_adress.setText("כתובת המשלוח:" + d.getAdressTo() + " " + d.getApartment() + "/" + d.getBuilding());
                    TextView time_of_order = (TextView) myView.findViewById(R.id.time_of_order);
                    time_of_order.setText("זמן יצירת הזמנה:" + d.getTimeInserted());
                    TextView time_after_prepare = (TextView) myView.findViewById(R.id.time_after_prepare);
                    time_after_prepare.setText("זמן הכנת משלוח:" + d.getPrepare_time());
                    TextView time_arrived_to_restoraunt = (TextView) myView.findViewById(R.id.time_arrived_to_restoraunt);
                    time_arrived_to_restoraunt.setText("זמן הגעת השליח:" +d.getTimeArriveToRestoraunt());
                    TextView time_leave_restoraunt = (TextView) myView.findViewById(R.id.time_leave_restoraunt);
                    time_leave_restoraunt.setText("זמן יציאת השליח:" + d.getTimeTaken());
                    TextView time_delivery = (TextView) myView.findViewById(R.id.time_delivery);
                    time_delivery.setText("זמן מסירת המשלוח:" + d.getTimeDeliver());

                    builder.setView(myView);
                    if (d.getWas_late_restoraunt())
                    {
                        builder.setTitle("עיכוב מסעדה");
                    }
                    else if(d.getWas_late_deliveries())
                    {
                        builder.setTitle("איחור חברת משלוחים");
                    }

                    builder.setNeutralButton("סיום", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    final  AlertDialog dialog = builder.create();

                    dialog.show();

                }
            });
        }
        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }
    }

    public interface ItemClickListener {
        void itemClicked(Delivery delivery);
    }
}

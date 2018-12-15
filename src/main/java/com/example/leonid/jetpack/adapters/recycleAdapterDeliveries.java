package com.example.leonid.jetpack.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.leonid.jetpack.FragmentDeliveries;
import com.example.leonid.jetpack.MainActivity;
import com.example.leonid.jetpack.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Objects.Delivery;

public class recycleAdapterDeliveries extends RecyclerView.Adapter<recycleAdapterDeliveries.ViewHolder> {


    private ArrayList<Delivery> deliveries;
    private  ItemClickListener itemClickListener;

    public recycleAdapterDeliveries(ArrayList<Delivery> objects, @NonNull ItemClickListener itemClickListener) {
        this.deliveries = objects;
        this.itemClickListener = itemClickListener;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        Context context = viewGroup.getContext();
        View parent = LayoutInflater.from(context).inflate(R.layout.adapter_layout_deliveries, viewGroup, false);
        return ViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final Delivery delivery = deliveries.get(position);
        if (!delivery.getIs_gas_sta()) {
            viewHolder.setIndex(delivery.getIndexString());
            viewHolder.setTime_of_order(delivery.getTimeInserted());
            viewHolder.setTime_to_prepare(delivery.getPrepare_time());
            viewHolder.setStatus(delivery.getStatus());
            viewHolder.setOnLongClickListener(itemClickListener,delivery.getIndexString());
            viewHolder.setAddresses(delivery.getAdressFrom() , delivery.getAdressTo(),delivery.getBusiness_name(),delivery.getCostumerName());
            viewHolder.setComment(delivery.getComment());
            viewHolder.setNum_of_packages(delivery.getNum_of_packets());



            if (!delivery.getStatus().equals("A")) {
                //C or D
                if(!delivery.getStatus().equals("B"))
                {
                    viewHolder.setTime_taken(delivery.getTimeInserted(),delivery.getTimeTaken(),Integer.valueOf(delivery.getPrepare_time()),delivery.getTime_bonus());
                }
                else
                {
                    viewHolder.setTime_taken(delivery.getTimeInserted(),delivery.getTime_aprox_deliver_to_rest(),Integer.valueOf(delivery.getPrepare_time()),delivery.getTime_bonus());
                }
                viewHolder.setName_of_delivery_guy(delivery.getDeliveryGuyName());

                viewHolder.setTime_arrived(delivery.getTimeInserted(),delivery.getTime_aprox_deliver(),delivery.getTime_max_to_costumer(),delivery.getTime_bonus());

            }
            viewHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (FragmentDeliveries.is_select_deliveries_mode)
                    {
                        viewHolder.setSelectItemClick(itemClickListener,delivery.getIndexString());

                    }
                    else
                    {
                        itemClickListener.itemClicked(delivery);
                    }

                }
            });
        }
        else
        {
            viewHolder.setTime_of_order(delivery.getTimeTaken());
            viewHolder.setName_of_delivery_guy(delivery.getDeliveryGuyName());
            viewHolder.setAddresses(delivery.getGasStation().getName());
        }
    }

    @Override
    public int getItemCount() {
        return deliveries.size();
    }
    @Override
    public long getItemId(int position) {
        return deliveries.get(position).getIndex();
    }


    public void moveItem(int start, int end) {
        int max = Math.max(start, end);
        int min = Math.min(start, end);
        if (min >= 0 && max < deliveries.size()) {
            Delivery item = deliveries.remove(min);
            deliveries.add(max, item);
            notifyItemMoved(min, max);
        }
    }

    public int getPositionForId(long id) {
        for (int i = 0; i < deliveries.size(); i++) {
            if (deliveries.get(i).getIndex() == id) {
                return i;
            }
        }
        return -1;
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {
        private final View parent;
        private final TextView index_delivery;
        private final TextView time_of_order;
        private final TextView time_to_prepare;
        private final TextView status;
        private final TextView addresses;
        private final TextView time_taken;
        private final TextView time_arrived;
        private final TextView comment;
        private final TextView num_of_packages;
        private final TextView name_of_delivery_guy;
        private final LinearLayout ll;

        public static ViewHolder newInstance(View parent) {

            TextView index_delivery = (TextView) parent.findViewById(R.id.index_delivery);
            TextView time_of_order =  (TextView) parent.findViewById(R.id.time_of_order);
            TextView time_to_prepare =  (TextView) parent.findViewById(R.id.time_to_prepare);
            TextView status = (TextView) parent.findViewById(R.id.status);
            TextView addresses = (TextView) parent.findViewById(R.id.addresses);
            TextView time_taken = (TextView) parent.findViewById(R.id.time_taken);
            TextView time_arrived = (TextView) parent.findViewById(R.id.time_arrived);
            TextView comment = (TextView) parent.findViewById(R.id.comment);
            TextView num_of_packages = (TextView) parent.findViewById(R.id.num_of_packages);
            TextView name_of_delivery_guy = (TextView) parent.findViewById(R.id.name_of_delivery_guy);
            LinearLayout ll = parent.findViewById(R.id.linearLayout4);
            return new ViewHolder(parent, index_delivery, time_of_order, time_to_prepare,status,addresses,time_taken,
                    time_arrived,comment,num_of_packages,name_of_delivery_guy,ll);
        }

        private ViewHolder(View parent,  TextView index_delivery, TextView time_of_order, TextView time_to_prepare,TextView status,
                           TextView addresses,TextView time_taken, TextView time_arrived ,
                           TextView comment,TextView num_of_packages, TextView name_of_delivery_guy,LinearLayout ll) {
            super(parent);
            this.parent = parent;
           this.index_delivery = index_delivery;
           this.time_of_order = time_of_order;
           this.time_to_prepare = time_to_prepare;
           this.status = status;
           this.addresses = addresses;
           this.time_taken = time_taken;
           this.time_arrived = time_arrived;
           this.comment = comment;
           this.num_of_packages = num_of_packages;
           this.name_of_delivery_guy = name_of_delivery_guy;
           this.ll = ll;
        }

        public void setOnLongClickListener(final ItemClickListener icl,final String index)
        {
            Log.d("TAG","reset_all_marked not");
            ll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    icl.itemLongClick(index,parent);
                    return true;
                }
            });

        }
        public void setSelectItemClick(final ItemClickListener icl,final String index)
        {
            icl.itemClicked(index,parent);
        }
        public void setIndex(CharSequence text) {
            index_delivery.setText("#" + text);
        }

        public void setTime_of_order(CharSequence text) {

            Spanned text2;

            text2 = Html.fromHtml( "זמן יצירה:"   + "<b>" + text + "</b>");
//        myTextView.setText(Html.fromHtml(text + "<font color=\"#FFFFFF\">" + CepVizyon.getPhoneCode() + "</font><br><br>"
//                + getText(R.string.currentversion) + CepVizyon.getLicenseText()));
            // date.setText( text + delivery.getDate());

            time_of_order.setText(text2);
        }

        public void setTime_to_prepare(CharSequence text) {
            Spanned text2;

            text2 = Html.fromHtml( "זמן הכנה:"   + "<b>" + text + "</b>" +  " דק");
//        myTextView.setText(Html.fromHtml(text + "<font color=\"#FFFFFF\">" + CepVizyon.getPhoneCode() + "</font><br><br>"
//                + getText(R.string.currentversion) + CepVizyon.getLicenseText()));
            // date.setText( text + delivery.getDate());
            time_to_prepare.setText(text2);
        }
        public void setStatus(CharSequence text) {
            status.setText(text);
        }

        public void setAddresses(CharSequence adres_from, CharSequence adress_to,CharSequence business_name,CharSequence cust_name) {
            Spanned text2;
            String text ="<font color=\"#191972\">" + "<b>" + "(" + business_name + ")" + "</b>" + "</font>" + " " + adres_from + "<br>"
                        +"<font color=\"#066828\">" + "<b>" +"(" + cust_name + ")" + "</b>" + "</font>" +" " + adress_to;
            text2 = Html.fromHtml(text);
            addresses.setText(text2);
        }
        public void setAddresses(CharSequence gas_sta_name) {

            addresses.setText(gas_sta_name);
        }
//  viewHolder.setTime_taken(delivery.getTimeTaken(),delivery.getTimeArriveToRestoraunt());
//            viewHolder.setTime_arrived(delivery.getTimeDeliver(),delivery.getTimeInserted());
        public void setTime_taken(CharSequence time_ins_arg,CharSequence time_arrived_to_rest,int time_prepare,int time_bonus_arg) {
            long min = 0;
            try {
            Date  arrived = new SimpleDateFormat("HH:mm").parse(time_arrived_to_rest.toString());
            Date time_inserted = new SimpleDateFormat("HH:mm").parse(time_ins_arg.toString());
            long diff = arrived.getTime() - time_inserted.getTime();
             min =  diff / (60 * 1000) % 60;
             min -= time_prepare;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Spanned text2;
            String text ="הגעה למסעדה:" + "<b>" + time_arrived_to_rest + "</b>" ;
            //came early
            if (min <0 )
            {
                min = -min;
                text += " " + "<font color=\"#1ff4a6\">" + "(" + min + "-"+")" + "</font>";
            }
            else if (min != 0)
            {
                text += " " + "<font color=\"#ed122c\">" + "(" + min +"+" + ")" + "</font>";
            }

            if (time_bonus_arg > 0 )
            {
                 text +="<br>" + "בונוס:"  + "<b>" + "<font color=\"#eaae2c\">"+ time_bonus_arg + "+" +  "</font>" + "</b>" ;
            }

            text2 = Html.fromHtml(text);
            time_taken.setText(text2);
            time_taken.setVisibility(View.VISIBLE);
        }



        public void setTime_arrived(CharSequence inserted,CharSequence delivered,int time_max_to_costumer,int time_bonus) {
            long max = 0;
            try {
                Date  time_delivered = new SimpleDateFormat("HH:mm").parse(delivered.toString());
                Date time_inserted = new SimpleDateFormat("HH:mm").parse(inserted.toString());
                long diff = time_delivered.getTime() - time_inserted.getTime();
                max =  diff / (60 * 1000) % 60;
                max -= time_max_to_costumer;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Spanned text2;
            String text ="הגעה ללקוח:" + "<b>" + delivered + "</b>" ;
            //came early
            if (max <0 )
            {
                max = -max;
                text += " " + "<font color=\"#1ff4a6\">" + "(" + max + "-"+")" + "</font>";
            }
            else if (max != 0)
            {
                text += " " + "<font color=\"#ed122c\">" + "(" + max +"+" + ")" + "</font>";
            }
            if (time_bonus > 0 )
            {
                text +="<br>";
            }

            text2 = Html.fromHtml(text);
            time_arrived.setText(text2);
            time_arrived.setVisibility(View.VISIBLE);
        }


        public void setComment(CharSequence text) {
            comment.setText(text);
            comment.setVisibility(View.VISIBLE);
        }
        public void setNum_of_packages(CharSequence text_Arg) {
            Spanned text2;
            String text =  "מספר חבילות:"  + "<b>"  + text_Arg +"</b>";
            text2 = Html.fromHtml(text);
            num_of_packages.setText( text2);

        }

        public void setName_of_delivery_guy(CharSequence name_guy) {
            Spanned text2;
            String text ="שליח:" + "<font color=\"#70196b\">" + "<b>"  + name_guy +"</b>" + "</font>";
            text2 = Html.fromHtml(text);
            name_of_delivery_guy.setText( text2);
            name_of_delivery_guy.setVisibility(View.VISIBLE);
        }

        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }
    }

    public interface ItemClickListener {
        void itemClicked(Delivery delivery);
        void itemClicked(String index,View parent);
        void itemLongClick(String index,View tv_marked);
    }
}
package com.example.leonid.jetpack.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leonid.jetpack.R;

import java.util.ArrayList;

import Objects.Delivery;
import Objects.Packages;

public class recycleAdapterPackages extends RecyclerView.Adapter<recycleAdapterPackages.ViewHolder> {


    private ArrayList<Packages> packages;
    private ItemClickListener itemClickListener;

    public recycleAdapterPackages(ArrayList<Packages> objects, @NonNull ItemClickListener itemClickListener) {
        this.packages = objects;
        this.itemClickListener = itemClickListener;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        Context context = viewGroup.getContext();
        View parent = LayoutInflater.from(context).inflate(R.layout.adapter_packages_activity, viewGroup, false);
        return ViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Packages pack = packages.get(position);
        String addrr = pack.getCity() +" " + pack.getStreet() + " " + pack.getApartment() + "/" + pack.getBuilding();
        viewHolder.setAddresses(addrr);
        viewHolder.setDate(pack.getDate());
        viewHolder.setName(pack.getName());
        viewHolder.setPhone(pack.getPhone());
        viewHolder.setTime_arranged(pack.getTime_expected());
        viewHolder.setTime_delivered(pack.getTime_arrived());

    }

    @Override
    public int getItemCount() {
        return packages.size();
    }


    public static final class ViewHolder extends RecyclerView.ViewHolder {
        private final View parent;
        private final TextView name;
        private final TextView phone;
        private final TextView addresses;
        private final TextView time_arranged;
        private final TextView time_delivered;
        private final TextView date;


        public static ViewHolder newInstance(View parent) {

            TextView name = (TextView) parent.findViewById(R.id.name);
            TextView phone =  (TextView) parent.findViewById(R.id.phone);
            TextView addresses =  (TextView) parent.findViewById(R.id.addresses);
            TextView time_arranged = (TextView) parent.findViewById(R.id.time_arranged);
            TextView time_delivered = (TextView) parent.findViewById(R.id.time_delivered);
            TextView date = (TextView) parent.findViewById(R.id.date);

            return new ViewHolder(parent,name,phone,addresses,time_arranged,time_delivered ,date);
        }

        private ViewHolder(View parent,  TextView name, TextView phone, TextView addresses,TextView time_arranged,
                           TextView time_delivered,TextView date) {
            super(parent);
            this.parent = parent;
            this.name = name;
            this.phone = phone;
            this.time_arranged = time_arranged;
            this.time_delivered = time_delivered;
            this.addresses = addresses;
            this.date = date;

        }

        public void setName(String name)
        {
            this.name.setText( "שם לקוח: "+ name);
        }
        public void setPhone(String phone)
        {
            this.phone.setText( "טלפון: "+ phone);
        }
        public void setAddresses(String addresses)
        {
            this.addresses.setText( "כתובת: "+ addresses);
        }
        public void setTime_arranged(String time_arranged)
        {
            this.time_arranged.setText( "תיאום: "+ time_arranged);
        }
        public void setTime_delivered(String time_delivered)
        {
            this.time_delivered.setText( "נמסר: "+ time_delivered);
        }
        public void setDate(String date)
        {
            this.date.setText( date);
        }


        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }
    }

    public interface ItemClickListener {
        void itemClicked(Packages p);
    }
}

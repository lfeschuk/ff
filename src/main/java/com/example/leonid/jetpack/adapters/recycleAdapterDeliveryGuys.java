package com.example.leonid.jetpack.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.example.leonid.jetpack.R;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Objects.DataBaseManager;
import Objects.Delivery;
import Objects.DeliveryGuyWorkTime;
import Objects.DeliveryGuys;
import Objects.ProfileImage;

public class recycleAdapterDeliveryGuys extends RecyclerView.Adapter<recycleAdapterDeliveryGuys.ViewHolder> {


    private ArrayList<DeliveryGuys> deliveryGuys;
    private ItemClickListener itemClickListener;
    public static ArrayList<ProfileImage> images;
    public recycleAdapterDeliveryGuys(ArrayList<DeliveryGuys> objects,ArrayList<ProfileImage> images, @NonNull ItemClickListener itemClickListener) {
        this.deliveryGuys = objects;
        this.itemClickListener = itemClickListener;
        this.images = images;
        setHasStableIds(true);
    }

    public void set_profileImage(ProfileImage pi)
    {
        for(ProfileImage piter : images)
        {
            if (piter.getIndexString().equals(pi.getIndexString()))
            {
                images.remove(piter);
                images.add(pi);
                return;
            }
        }
        images.add(pi);
    }

    static public ProfileImage getProfile(String index)
    {
        for(ProfileImage piter : images)
        {
            if (piter.getIndexString().equals(index))
            {
               return piter;
            }
        }
        return null;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        Context context = viewGroup.getContext();
        View parent = LayoutInflater.from(context).inflate(R.layout.adapter_layout_delivery_guys, viewGroup, false);
        return ViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final DeliveryGuys d = deliveryGuys.get(position);
        viewHolder.setButton(d);
        viewHolder.setName(d.getName());
        viewHolder.setProfile(d.getIndex_string());
        viewHolder.setInfo(d.getDeliveries().size(),d.getTimeBeFree());

        viewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.itemClicked(d);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deliveryGuys.size();
    }
    @Override
    public long getItemId(int position) {
        return deliveryGuys.get(position).getIndex();
    }

    public void moveItem(int start, int end) {
        int max = Math.max(start, end);
        int min = Math.min(start, end);
        if (min >= 0 && max < deliveryGuys.size()) {
            DeliveryGuys item = deliveryGuys.remove(min);
            deliveryGuys.add(max, item);
            notifyItemMoved(min, max);
        }
    }

    public int getPositionForId(long id) {
        for (int i = 0; i < deliveryGuys.size(); i++) {
            if (deliveryGuys.get(i).getIndex() == id) {
                return i;
            }
        }
        return -1;
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {
        private final View parent;
        private final    TextView delivery_guy_name;
        private final ToggleButton delivery_guy_button;
        private final TextView delivery_guy_info;
        private final ImageView profile;


        public static ViewHolder newInstance(View parent) {

            TextView delivery_guy_name = (TextView) parent.findViewById(R.id.deliv_guys_name);
            ToggleButton delivery_guy_button =  (ToggleButton) parent.findViewById(R.id.deliv_guys_button);
            ImageView profile = parent.findViewById(R.id.profile_image);
            TextView delivery_guy_info = parent.findViewById(R.id.deliv_guys_additional_info);

            return new ViewHolder(parent, delivery_guy_name, delivery_guy_button,profile,delivery_guy_info);
        }

        private ViewHolder(View parent,  TextView delivery_guy_name, ToggleButton delivery_guy_button,ImageView profile, TextView delivery_guy_info) {
            super(parent);
            this.parent = parent;
            this.delivery_guy_name = delivery_guy_name;
            this.delivery_guy_button = delivery_guy_button;
            this.profile = profile;
            this.delivery_guy_info = delivery_guy_info;
        }


        public void setName(CharSequence text) {
            delivery_guy_name.setText(text);
        }

        public void setButton(final DeliveryGuys d) {

            delivery_guy_button.setChecked(d.getIs_active());
            delivery_guy_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean is_checked) {

                DataBaseManager dbm = new DataBaseManager();

                DeliveryGuyWorkTime d_time = new DeliveryGuyWorkTime();
                Calendar cal = Calendar.getInstance();


                Date curr_iter_date = cal.getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String date_now = df.format(curr_iter_date);
                df = new SimpleDateFormat("HH:mm");
                String time_now = df.format(curr_iter_date);
              //  String time_now = curr_iter_date.getHours() + ":" + curr_iter_date.getMinutes();
               // Log.d("ff","date now " +date_now + " time now: " + time_now);
//
//                d_time.setIndex(d.getIndex());
                d_time.setIndexString(d.getIndex_string());
                d_time.setName(d.getName());
                d_time.setTime(time_now);
//                d_time.add_times_to_date(time_now,is_checked,date_now);
                d_time.setIn_time(is_checked);
                d_time.setDate(date_now);
                d.setIs_active(is_checked);

                dbm.writeDeliveryGuy(d);
                dbm.writeDeliveryGuyTime(d_time,date_now);


                }
            });
        }
        public void setProfile(String indexString)
        {
            ProfileImage pi = getProfile(indexString);
            if (pi != null)
            {
                profile.setImageBitmap(pi.getImage());
                profile.setVisibility(View.VISIBLE);
            }

        }
        public void setInfo(int num_deliveries,String time_be_free)
        {

            Spanned text2;
            String text;
            if (num_deliveries > 0)
            {
                text =  "משלוחים פעילים: " + "<b>" + num_deliveries + "</b>" + "<br>" + "יהיה פנוי: " + "<b>" + time_be_free + "</b>";
            }
            else
            {
                text =  "<font color=\"#c40f27\">" + "השליח פנוי" + "</font>";
            }


            text2 = Html.fromHtml(text);
            delivery_guy_info.setText(text2);
        }

        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }
    }

    public interface ItemClickListener {
        void itemClicked(DeliveryGuys d);
    }
}
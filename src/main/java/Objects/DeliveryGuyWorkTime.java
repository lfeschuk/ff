package Objects;

import android.util.Log;

import java.util.ArrayList;

public class DeliveryGuyWorkTime {
    //assume same index as DeliveryGuy
//    private long index;
    private String indexString;
    private String name;
    private Boolean in_time ;
    private String time;
    private String date;
//    private ArrayList<DeliveryGuytimeByDate> working_dates = new ArrayList<>();
    public static final String TAG = "DeliveryGuyWorkTime";

    public DeliveryGuyWorkTime ( )
    {

    }
    public DeliveryGuyWorkTime (DeliveryGuyWorkTime d )
    {
        this.indexString = d.getIndexString();
        this.name  = d.getName();
        this.in_time = d.getIn_time();
        this.time = d.getTime();
        this.date = d.getDate();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getIn_time() {
        return in_time;
    }

    public void setIn_time(Boolean in_time) {
        this.in_time = in_time;
    }
    //    public long getIndex() {
//        return index;
//    }
//
//    public void setIndex(long index) {
//        this.index = index;
//    }
//

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIndexString() {
        return indexString;
    }
//
    public void setIndexString(String indexString) {
        this.indexString = indexString;
    }
//
    public String getName() {
        return name;
    }
//
    public void setName(String name) {
        this.name = name;
    }
//
//    public ArrayList<DeliveryGuytimeByDate> getWorking_dates() {
//        return working_dates;
//    }
//
//    public void setWorking_dates(ArrayList<DeliveryGuytimeByDate> working_dates) {
//        this.working_dates = working_dates;
//    }
//    public void add_times_to_date (String time,Boolean in_time,String date)
//    {
//        Log.d(TAG,"ffffffff");
//       // Boolean found = false;
//        for( DeliveryGuytimeByDate d : working_dates)
//        {
//            if (d.getDate().equals(date))
//            {
//                d.add_time(time,in_time);
//                return;
//            }
//        }
//        //assume didnt find
//        DeliveryGuytimeByDate d = new DeliveryGuytimeByDate();
//        d.setDate(date);
//        d.add_time(time,in_time);
//        working_dates.add(d);
//    }

}

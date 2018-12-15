package Objects;

import android.util.Log;

import java.util.ArrayList;

public class DeliveryGuys extends DeliveryObject {
    public static final String TAG = "DeliveryGuys";
    String timeBeFree = "";
    ArrayList<Delivery> deliveries = new ArrayList<>();
    ArrayList<Destination> destinations = new ArrayList<>();
    double latetude = 0;
    int num_of_deliveries = 0;
    int num_of_packages = 0;
    double longtitude = 0;
    Boolean sent_start_shift_report = false;

    public DeliveryGuys(String name, String timeBeFree, ArrayList<Delivery> deliveries, double latetude, double longtitude, String picture,String index_string,Boolean is_active,
                        long index,double salary,String phone,Boolean sent_start_shift_report,ArrayList<Destination> destinations) {

        this.name = name;
        this.timeBeFree = timeBeFree;
        if (deliveries != null)
        {
            this.deliveries = deliveries;
        }
        this.latetude = latetude;
        this.longtitude = longtitude;
        this.picture = picture;
        this.index_string = index_string;
        this.is_active = is_active;
        this.index = index;
        this.salary = salary;
        this.phone = phone;
        this.sent_start_shift_report = sent_start_shift_report;
        this.destinations = destinations;
    }
    public DeliveryGuys(){}

    public DeliveryGuys( DeliveryGuys d)
    {
        this.name = d.getName();
        this.timeBeFree = d.getTimeBeFree();
        this.deliveries = d.getDeliveries();
        this.latetude = d.getLatetude();
        this.longtitude = d.getLongtitude();
        this.picture = d.getPicture();
        this.index_string = d.getIndex_string();
        this.is_active = d.getIs_active();
        this.index = d.getIndex();
        this.salary = d.getSalary();
        this.phone = d.getPhone();
        this.sent_start_shift_report = d.getSent_start_shift_report();
        this.destinations = d.getDestinations();
        this.num_of_deliveries = d.getNum_of_deliveries();
        this.num_of_packages = d.getNum_of_packages();
    }

    public int getNum_of_deliveries() {
        return num_of_deliveries;
    }

    public int getNum_of_packages() {
        return num_of_packages;
    }

    public void setNum_of_deliveries(int num_of_deliveries) {
        this.num_of_deliveries = num_of_deliveries;
    }

    public void setNum_of_packages(int num_of_packages) {
        this.num_of_packages = num_of_packages;
    }
    public ArrayList<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(ArrayList<Destination> destinations) {
        this.destinations = destinations;
    }

    public Boolean getSent_start_shift_report() {
        return sent_start_shift_report;
    }

    public void setSent_start_shift_report(Boolean sent_start_shift_report) {
        this.sent_start_shift_report = sent_start_shift_report;
    }



    public ArrayList<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(ArrayList<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public void addDelivery(Delivery d)
    {
        this.deliveries.add(d);
    }
    public void removeDelivery(String delivery_index)
    {
        Delivery to_remove = null;
        for (Delivery d : deliveries)
        {
            if (to_remove != null)
            {
                deliveries.remove(to_remove);
                to_remove = null;
                return;
            }
            Log.d(TAG,"iter indx: "+ d.getIndexString() + " wanted: " +delivery_index);
            if (d.getIndexString().equals(delivery_index))
            {
                Log.d(TAG,"remove delivery from guy: " + index_string + " deliv: " + delivery_index);
                to_remove = d;
            }
        }
        if (to_remove != null)
        {
            deliveries.remove(to_remove);
            to_remove = null;
            return;
        }
    }

    public void removeDestination(String delivery_index)
    {
        Destination to_remove = null;
        for (Destination d : destinations)
        {
            if (to_remove != null)
            {
                destinations.remove(to_remove);
                to_remove = null;
            }
          //  Log.d(TAG,"iter indx: "+ d.getIndexString() + " wanted: " +delivery_index);
            if (d.getIndex_string().equals(delivery_index))
            {
                Log.d(TAG,"remove removeDestination from guy: " + index_string + " deliv: " + delivery_index);
                to_remove = d;
            }
        }
        if (to_remove != null)
        {
            destinations.remove(to_remove);
            to_remove = null;
        }
    }



    public String getTimeBeFree() {
        return timeBeFree;
    }

    public void setTimeBeFree(String timeBeFree) {
        this.timeBeFree = timeBeFree;
    }



    public double getLatetude() {
        return latetude;
    }

    public void setLatetude(double latetude) {
        this.latetude = latetude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }


}

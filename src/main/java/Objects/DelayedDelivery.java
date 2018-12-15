package Objects;

public class DelayedDelivery extends Delivery {
    String delayed_date = "";
    String delayed_hour = "";


    public DelayedDelivery(Long index, String adressTo, String adressFrom, String timeInserted, String status, String comment, String num_of_packets, String costumer_phone, String costumerName, String city, String floor, String building, String entrance, String street, String apartment, String business_name, String delivery_guy_index_assigned, double source_cord_lat, double source_cord_long, double dest_cord_lat, double dest_cord_long, String deliveryGuyName, String date, String timeArriveToRestoraunt, String deliveryGuyPhone, String costumer_another_phone, String intercum_num, String price, Boolean is_cash, String key, String restoraunt_key, String different_address, String delayed_date, String delayed_hour
            ,String restoraunt_phone,int time_bonus) {
        super(index, adressTo, adressFrom, timeInserted, status, comment, num_of_packets, costumer_phone, costumerName, city, floor, building, entrance, street, apartment, business_name, delivery_guy_index_assigned, source_cord_lat, source_cord_long, dest_cord_lat, dest_cord_long, deliveryGuyName, date, timeArriveToRestoraunt, deliveryGuyPhone, costumer_another_phone, intercum_num, price, is_cash, key, restoraunt_key, different_address,restoraunt_phone,time_bonus);
        this.delayed_date = delayed_date;
        this.delayed_hour = delayed_hour;
    }

    public DelayedDelivery(Delivery d, String delayed_date, String delayed_hour) {
        super(d);
        this.delayed_date = delayed_date;
        this.delayed_hour = delayed_hour;
    }
    public DelayedDelivery(DelayedDelivery d) {
        super((Delivery)d);
        this.delayed_date = d.getDelayed_date();
        this.delayed_hour = d.getDelayed_hour();
    }
    public DelayedDelivery(){}

    public String getDelayed_date() {
        return delayed_date;
    }

    public void setDelayed_date(String delayed_date) {
        this.delayed_date = delayed_date;
    }

    public String getDelayed_hour() {
        return delayed_hour;
    }

    public void setDelayed_hour(String delayed_hour) {
        this.delayed_hour = delayed_hour;
    }
}

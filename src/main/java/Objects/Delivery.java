package Objects;

import android.net.Uri;
import android.support.v4.util.Pair;
import android.util.Log;

import java.io.Serializable;

public class Delivery implements Serializable {

    private long index;
    private String indexString = "";
    private String adressTo = "";
    private String adressFrom = "";
    private String timeInserted = "";
    private String prepare_time = "";
    private String status = "A";
    private String comment = "";
    private String num_of_packets = "";
    private String costumer_phone = "";
    private String time_aprox_deliver = "";
    private String time_aprox_deliver_to_rest = "";
    public String costumer_another_phone = "";
    private String timeArriveToRestoraunt = "";
    private String timeTaken = "";
    private String timeDeliver = "";
    private Boolean was_late_restoraunt;
    private Boolean was_late_deliveries;
    private String costumerName = "";
    private String date = "";
    private String city = "";
    private String floor = "";
    private String entrance = "";
    private String building = "";
    private String street = "";
    private String apartment = "";
    private Boolean is_deleted = false;
    private String business_name = "";
    private String delivery_guy_index_assigned = "";
    public String deliveryGuyName = "";
    public String deliveryGuyPhone = "";
    public String intercum_num = "";
    private double source_cord_lat;
    private double source_cord_long;
    private double dest_cord_lat;
    private double dest_cord_long;
    private Boolean is_gas_sta = false;
    private String price = "";
    private Boolean is_cash ;
    private String key = "";
    private String different_address = "";
    public String restoraunt_key = "";
    public String restoraunt_phone = "";
    public Boolean just_assigned_deliv = false;
    public int time_bonus = 0;
    public int time_max_to_costumer = 0;
    public GasStation gasStation;

    public String getTime_aprox_deliver_to_rest() {
        return time_aprox_deliver_to_rest;
    }

    public void setTime_aprox_deliver_to_rest(String time_aprox_deliver_to_rest) {
        this.time_aprox_deliver_to_rest = time_aprox_deliver_to_rest;
    }

    public int getTime_max_to_costumer() {
        return time_max_to_costumer;
    }

    public String getTime_aprox_deliver() {
        return time_aprox_deliver;
    }

    public void setTime_aprox_deliver(String time_aprox_deliver) {
        this.time_aprox_deliver = time_aprox_deliver;
    }

    public void setTime_max_to_costumer(int time_max_to_costumer) {
        this.time_max_to_costumer = time_max_to_costumer;
    }

    public GasStation getGasStation() {
        return gasStation;
    }

    public void setGasStation(GasStation gasStation) {
        this.gasStation = gasStation;
    }



    public int getTime_bonus() {
        return time_bonus;
    }

    public void setTime_bonus(int time_bonus) {
        this.time_bonus = time_bonus;
    }


    public String getRestoraunt_phone() {
        return restoraunt_phone;
    }

    public void setRestoraunt_phone(String restoraunt_phone) {
        this.restoraunt_phone = restoraunt_phone;
    }

    public void setJust_assigned_deliv(Boolean just_assigned_deliv) {
        this.just_assigned_deliv = just_assigned_deliv;
    }

    public Boolean getJust_assigned_deliv() {
        return just_assigned_deliv;
    }

    public String getDifferent_address() {
        return different_address;
    }

    public void setDifferent_address(String different_address) {
        this.different_address = different_address;
    }

    public String getRestoraunt_key() {
        return restoraunt_key;
    }

    public void setRestoraunt_key(String restoraunt_key) {
        this.restoraunt_key = restoraunt_key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
    // private GasStation gasStation;


    public Boolean getIs_cash() {
        return is_cash;
    }

    public String getPrice() {
        return price;
    }

    public void setIs_cash(Boolean is_cash) {
        this.is_cash = is_cash;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIntercum_num() {
        return intercum_num;
    }

    public void setIntercum_num(String intercum_num) {
        this.intercum_num = intercum_num;
    }

    public String getCostumer_another_phone() {
        return costumer_another_phone;
    }

    public void setCostumer_another_phone(String costumer_another_phone) {
        this.costumer_another_phone = costumer_another_phone;
    }

    public Boolean getWas_late_deliveries() {
        return was_late_deliveries;
    }

    public Boolean getWas_late_restoraunt() {
        return was_late_restoraunt;
    }

    public void setWas_late_deliveries(Boolean was_late_deliveries) {
        this.was_late_deliveries = was_late_deliveries;
    }

    public void setWas_late_restoraunt(Boolean was_late_restoraunt) {
        this.was_late_restoraunt = was_late_restoraunt;
    }

    public String getTimeArriveToRestoraunt() {
        return timeArriveToRestoraunt;
    }

    public void setTimeArriveToRestoraunt(String timeArriveToRestoraunt) {
        this.timeArriveToRestoraunt = timeArriveToRestoraunt;
    }

    public Boolean getIs_gas_sta() {
        return is_gas_sta;
    }

//    public GasStation getGasStation() {
//        return gasStation;
//    }
//
//    public void setGasStation(GasStation gasStation) {
//        this.gasStation = gasStation;
//    }

    public void setIs_gas_sta(Boolean is_gas_sta) {
        this.is_gas_sta = is_gas_sta;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public String getDeliveryGuyName() {
        return deliveryGuyName;
    }

    public void setDeliveryGuyName(String deliveryGuyName) {
        this.deliveryGuyName = deliveryGuyName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getSource_cord_lat() {
        return source_cord_lat;
    }

    public void setSource_cord_lat(double source_cord_lat) {
        this.source_cord_lat = source_cord_lat;
    }

    public double getSource_cord_long() {
        return source_cord_long;
    }

    public void setSource_cord_long(double source_cord_long) {
        this.source_cord_long = source_cord_long;
    }

    public double getDest_cord_lat() {
        return dest_cord_lat;
    }

    public void setDest_cord_lat(double dest_cord_lat) {
        this.dest_cord_lat = dest_cord_lat;
    }

    public double getDest_cord_long() {
        return dest_cord_long;
    }

    public void setDest_cord_long(double dest_cord_long) {
        this.dest_cord_long = dest_cord_long;
    }

    public String getDelivery_guy_index_assigned() {
        return delivery_guy_index_assigned;
    }

    public void setDelivery_guy_index_assigned(String delivery_guy_index_assigned) {
        this.delivery_guy_index_assigned = delivery_guy_index_assigned;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    //  private DeliveryGuys dGuy;

    public Delivery() {
    }

    public String getCostumerName() {
        return costumerName;
    }

    public void setCostumerName(String costumerName) {
        this.costumerName = costumerName;
    }

    public String getCostumer_phone() {
        return costumer_phone;
    }

    public void setCostumer_phone(String costumer_phone) {
        this.costumer_phone = costumer_phone;
    }

    public String getIndexString() {
        return indexString;
    }

    public void setIndexString(String indexString) {
        this.indexString = indexString;
    }

    public String getDeliveryGuyPhone() {
        return deliveryGuyPhone;
    }

    public void setDeliveryGuyPhone(String deliveryGuyPhone) {
        this.deliveryGuyPhone = deliveryGuyPhone;
    }

    public Delivery(Long index, String adressTo, String adressFrom, String timeInserted, String status, String comment,
                    String num_of_packets, String costumer_phone, String costumerName,
                    String city, String floor, String building, String entrance, String street, String apartment,
                    String business_name, String delivery_guy_index_assigned,
                    double source_cord_lat, double source_cord_long, double dest_cord_lat, double dest_cord_long,
                    String deliveryGuyName, String date, String timeArriveToRestoraunt,String deliveryGuyPhone,String costumer_another_phone,
                    String intercum_num,String price,Boolean is_cash,String key,String restoraunt_key, String different_address,String restoraunt_phone,
                   int time_bonus) {
        this.index = index;
        indexString = index.toString();
        this.adressTo = adressTo;
        this.adressFrom = adressFrom;
        this.timeInserted = timeInserted;
        this.status = status;
        this.comment = comment;
        this.num_of_packets = num_of_packets;
        this.prepare_time = "--";
        this.timeTaken = "--" ;
        this.timeDeliver = "--";
        this.costumer_phone = costumer_phone;
        this.costumerName = costumerName;
        this.city = city;
        this.floor = floor;
        this.entrance = entrance;
        this.building = building;
        this.street = street;
        this.apartment = apartment;
        this.business_name = business_name;
        this.delivery_guy_index_assigned = delivery_guy_index_assigned;
        this.source_cord_lat = source_cord_lat;
        this.source_cord_long = source_cord_long;
        this.dest_cord_lat = dest_cord_lat;
        this.dest_cord_long = dest_cord_long;
        this.deliveryGuyName = deliveryGuyName;
        this.date = date;
        this.timeArriveToRestoraunt = timeArriveToRestoraunt;
        this.deliveryGuyPhone = deliveryGuyPhone;
        this.costumer_another_phone = costumer_another_phone;
        this.intercum_num = intercum_num;
        this.is_cash = is_cash;
        this.price = price;
        this.key = key;
        this.restoraunt_key = restoraunt_key;
        this.different_address = different_address;
        this.restoraunt_phone = restoraunt_phone;


        this.time_bonus = time_bonus;

        //  this
    }

    public Delivery( Delivery d)
    {

        this.indexString = d.getIndexString();
        this.adressTo = d.getAdressTo();
        this.adressFrom = d.getAdressFrom();
        this.timeInserted = d.getTimeInserted();
        this.status = d.getStatus();
        this.comment = d.getComment();
        this.num_of_packets = d.getNum_of_packets();
        this.prepare_time = d.getPrepare_time();
        this.timeTaken = d.getTimeTaken() ;
        this.timeDeliver = d.getTimeDeliver();
        this.costumer_phone = d.getCostumer_phone();
        this.costumerName = d.getCostumerName();
        this.building = d.getBuilding();
        this.floor = d.getFloor();
        this.entrance = d.getEntrance();
        this.city = d.getCity();
        this.street = d.getStreet();
        this.apartment = d.getApartment();
        this.business_name = d.getBusiness_name();
        this.delivery_guy_index_assigned = d.getDelivery_guy_index_assigned();
        this.source_cord_lat = d.getSource_cord_lat();
        this.source_cord_long = d.getSource_cord_long();
        this.dest_cord_lat = d.getDest_cord_lat();
        this.dest_cord_long = d.getDest_cord_long();
        this.deliveryGuyName = d.getDeliveryGuyName();
        this.index = d.getIndex();
        this.date = d.getDate();
        //  this.gasStation = d.getGasStation();
        this.is_gas_sta = d.getIs_gas_sta();
        this.timeArriveToRestoraunt = d.getTimeArriveToRestoraunt();
        this.was_late_deliveries = d.getWas_late_deliveries();
        this.was_late_restoraunt = d.getWas_late_restoraunt();
        this.deliveryGuyPhone = d.getDeliveryGuyPhone();
        this.costumer_another_phone = d.getCostumer_another_phone();
        this.intercum_num = d.getIntercum_num();
        this.price = d.getPrice();
        this.is_cash = d.getIs_cash();
        this.key = d.getKey();
        this.restoraunt_key = d.getRestoraunt_key();
        this.different_address = d.getDifferent_address();
        this.just_assigned_deliv = d.getJust_assigned_deliv();
        this.restoraunt_phone = d.getRestoraunt_phone();
         this.gasStation = d.getGasStation();
        this.time_bonus = d.getTime_bonus();
        this.time_max_to_costumer = d.getTime_max_to_costumer();
        this.time_aprox_deliver = d.getTime_aprox_deliver();
        this.time_aprox_deliver_to_rest = d.getTime_aprox_deliver_to_rest();
    }

    public String getAdressTo() {
        return adressTo;
    }

    public void setAdressTo(String adressTo) {
        this.adressTo = adressTo;
    }

    public String getAdressFrom() {
        return adressFrom;
    }

    public void setAdressFrom(String adressFrom) {
        this.adressFrom = adressFrom;
    }

    public String getPrepare_time() {
        return prepare_time;
    }

    public void setPrepare_time(String prepare_time) {
        this.prepare_time = prepare_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNum_of_packets() {
        return num_of_packets;
    }

    public void setNum_of_packets(String num_of_packets) {
        this.num_of_packets = num_of_packets;
    }

//    public DeliveryGuys getdGuy() {
//        return dGuy;
//    }
//
//    public void setdGuy(DeliveryGuys dGuy) {
//        this.dGuy = dGuy;
//    }

    public String getTimeInserted() {
        return timeInserted;
    }

    public void setTimeInserted(String timeInserted) {
        this.timeInserted = timeInserted;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getTimeDeliver() {
        return timeDeliver;
    }

    public void setTimeDeliver(String timeDeliver) {
        this.timeDeliver = timeDeliver;
    }
}

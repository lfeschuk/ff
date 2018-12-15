package Objects;

import java.util.ArrayList;

public class DeliveryObject {

    public static final String TAG = "DeliveryObject";
    String name = "";
    String picture = "";
    String index_string = "";
    double salary;
    long index;
    String phone = "";
    Boolean is_active = true;

    public DeliveryObject(String name,String picture,String index_string,Boolean is_active,
                        long index,double salary,String phone) {
        this.name = name;
        this.picture = picture;
        this.index_string = index_string;
        this.is_active = is_active;
        this.index = index;
        this.salary = salary;
        this.phone = phone;
    }
    public DeliveryObject(){}

    public DeliveryObject( DeliveryObject d)
    {
        this.name = d.getName();
        this.picture = d.getPicture();
        this.index_string = d.getIndex_string();
        this.is_active = d.getIs_active();
        this.index = d.getIndex();
        this.salary = d.getSalary();
        this.phone = d.getPhone();
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getIndex_string() {
        return index_string;
    }

    public void setIndex_string(String index_string) {
        this.index_string = index_string;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }
}

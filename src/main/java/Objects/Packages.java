package Objects;

public class Packages {
    String name = "";
    String phone = "";
    String city = "";
    String street = "";
    String apartment = "";
    String building = "";
    String time_expected = "";
    String time_arrived = "";
    Boolean time_was_arranged = false;
    String date = "";

    public Packages(String name, String phone, String city, String street, String apartment, String building, String time_expected, String time_arrived, String date) {
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.street = street;
        this.apartment = apartment;
        this.building = building;
        this.time_expected = time_expected;
        this.time_arrived = time_arrived;
        this.date = date;
    }
    public Packages(Packages p) {
        this.name = p.getName();
        this.phone = p.getPhone();
        this.city = p.getCity();
        this.street = p.getStreet();
        this.apartment = p.getApartment();
        this.building = p.getBuilding();
        this.time_expected = p.getTime_expected();
        this.time_arrived = p.getTime_arrived();
        this.date = p.getDate();
        this.time_was_arranged = p.getTime_was_arranged();
    }

    public Boolean getTime_was_arranged() {
        return time_was_arranged;
    }

    public void setTime_was_arranged(Boolean time_was_arranged) {
        this.time_was_arranged = time_was_arranged;
    }

    public Packages() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getTime_expected() {
        return time_expected;
    }

    public void setTime_expected(String time_expected) {
        this.time_expected = time_expected;
    }

    public String getTime_arrived() {
        return time_arrived;
    }

    public void setTime_arrived(String time_arrived) {
        this.time_arrived = time_arrived;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

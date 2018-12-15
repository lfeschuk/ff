package Objects;

public abstract class Business {
    String email = "";
    String name = "";
    String index = "";
    String phone = "";
    double longt = 35.0104;
    double lat = 31.8903;
    String adress = "";

    public Business(String name,String index,double longt,double lat,String email, String adress,String phone)
    {
        this.name = name;
        this.longt = longt;
        this.lat = lat;
        this.index = index;
        this.email = email;
        this.adress = adress;
        this.phone = phone;
    }
    public Business (Business b)
    {
        this.name = b.getName();
        this.longt = b.getLongt();
        this.lat = b.getLat();
        this.index = b.getIndex();
        this.email = b.getEmail();
        this.adress = b.getAdress();
        this.phone = b.getPhone();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String index) {
        this.email = index;
    }
    public Business(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongt() {
        return longt;
    }

    public void setLongt(double longt) {
        this.longt = longt;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getIndex() {
        return index;
    }
}

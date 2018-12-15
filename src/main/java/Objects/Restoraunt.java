package Objects;

public class Restoraunt extends Business {

    int time_to_prepare;
    int time_to_costumer;
    String key = "";

    public Restoraunt(String name, String index, double longt, double lat,int time_to_costumer,int time_to_prepare,String email,String adress,String key,String phone ) {
        super(name, index, longt, lat,email,adress,phone);
        this.time_to_costumer = time_to_costumer;
        this.time_to_prepare = time_to_prepare;
        this.key=key;
    }

    public Restoraunt(Restoraunt b) {
        super(b);
        this.time_to_prepare = b.getTime_to_prepare();
        this.time_to_costumer = b.getTime_to_costumer();
        this.key = b.getKey();
    }
    public Restoraunt(){}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getTime_to_costumer() {
        return time_to_costumer;
    }

    public int getTime_to_prepare() {
        return time_to_prepare;
    }

    public void setTime_to_costumer(int time_to_costumer) {
        this.time_to_costumer = time_to_costumer;
    }

    public void setTime_to_prepare(int time_to_prepare) {
        this.time_to_prepare = time_to_prepare;
    }
}

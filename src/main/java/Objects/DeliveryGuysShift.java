package Objects;

public class DeliveryGuysShift {
    public static enum KindShift {NONE,MORNING,EVENING,DOUBLE;
        public static KindShift valueOf(int n)
        {
            switch (n)
            {
                case 0:
                    return NONE;
                case 1:
                   return MORNING;

                case 2:
                    return EVENING;

                case 3:
                   return DOUBLE;

                default:
                    return NONE;
            }

        }
        public static String valueOf(KindShift k)
        {
            switch (k)
            {
                case NONE:
                    return "-";
                case MORNING:
                    return "בוקר";

                case EVENING:
                    return "ערב";

                case DOUBLE:
                    return "כפולה";

                default:
                    return "-";
            }

        }

    };



    private long index;
    private String IndexString;
    private String name;
    private String date;
    private String date_of_sunday;
    private Boolean is_approved = false;
    private KindShift sunday;
    private KindShift monday;
    private KindShift tuesday;
    private KindShift wednesday;
    private KindShift thursday;
    private KindShift friday;
    private KindShift saturday;
    private int total_morning;
    private int total_evening;

    public DeliveryGuysShift(long index, String indexString, String name, String date, KindShift sunday, KindShift monday, KindShift tuesday, KindShift wednesday, KindShift thursday, KindShift friday, KindShift saturday
            ,int total_morning,int total_evening,String date_of_sunday,Boolean is_approved) {
        this.index = index;
        IndexString = indexString;
        this.name = name;
        this.date = date;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.total_evening = total_evening;
        this.total_morning = total_morning;
        this.date_of_sunday = date_of_sunday;
        this.is_approved = is_approved;
    }
    public DeliveryGuysShift(){}

    public DeliveryGuysShift(DeliveryGuysShift d) {
        this.index = d.getIndex();
        IndexString = d.getIndexString();
        this.name = d.getName();
        this.date = d.getDate();
        this.sunday = d.getSunday();
        this.monday = d.getMonday();
        this.tuesday = d.getTuesday();
        this.wednesday = d.getWednesday();
        this.thursday = d.getThursday();
        this.friday = d.getFriday();
        this.saturday = d.getSaturday();
        this.total_morning = d.getTotal_morning();
        this.total_evening = d.getTotal_evening();
        this.date_of_sunday = d.getDate_of_sunday();
        this.is_approved = d.getIs_approved();
    }

    public Boolean getIs_approved() {
        return is_approved;
    }

    public void setIs_approved(Boolean is_approved) {
        this.is_approved = is_approved;
    }

    public void setDate_of_sunday(String date_of_sunday) {
        this.date_of_sunday = date_of_sunday;
    }

    public String getDate_of_sunday() {
        return date_of_sunday;
    }

    public int getTotal_evening() {
        return total_evening;
    }

    public int getTotal_morning() {
        return total_morning;
    }

    public void setTotal_evening(int total_evening) {
        this.total_evening = total_evening;
    }

    public void setTotal_morning(int total_morning) {
        this.total_morning = total_morning;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public String getIndexString() {
        return IndexString;
    }

    public void setIndexString(String indexString) {
        IndexString = indexString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public KindShift getSunday() {
        return sunday;
    }

    public void setSunday(KindShift sunday) {
        this.sunday = sunday;
    }

    public KindShift getMonday() {
        return monday;
    }

    public void setMonday(KindShift monday) {
        this.monday = monday;
    }

    public KindShift getTuesday() {
        return tuesday;
    }

    public void setTuesday(KindShift tuesday) {
        this.tuesday = tuesday;
    }

    public KindShift getWednesday() {
        return wednesday;
    }

    public void setWednesday(KindShift wednesday) {
        this.wednesday = wednesday;
    }

    public KindShift getThursday() {
        return thursday;
    }

    public void setThursday(KindShift thursday) {
        this.thursday = thursday;
    }

    public KindShift getFriday() {
        return friday;
    }

    public void setFriday(KindShift friday) {
        this.friday = friday;
    }

    public KindShift getSaturday() {
        return saturday;
    }

    public void setSaturday(KindShift saturday) {
        this.saturday = saturday;
    }
}

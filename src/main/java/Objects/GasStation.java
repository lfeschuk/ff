package Objects;

public class GasStation {
    public String name;
    double lon;
    double lat;

    public GasStation(String name, double lon, double lat) {
        this.name = name;
        this.lon = lon;
        this.lat = lat;
    }
    public GasStation(){}
    public GasStation(GasStation g)
    {
        this.name = g.getName();
        this.lon = g.getLon();
        this.lat = g.getLat();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}

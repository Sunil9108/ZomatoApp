package techpalle.com.zomatoapp;

public class Restaurant {

    private String image_url;
    private String hotel_name;
    private String hotel_locality;
    private String hotel_offers;
    private String hotel_rating;
    private String hotel_lat;
    private String hotel_lon;

    public Restaurant(String image_url, String hotel_name, String hotel_locality, String hotel_offers, String hotel_rating, String hotel_lat, String hotel_lon) {
        this.image_url = image_url;
        this.hotel_name = hotel_name;
        this.hotel_locality = hotel_locality;
        this.hotel_offers = hotel_offers;
        this.hotel_rating = hotel_rating;
        this.hotel_lat = hotel_lat;
        this.hotel_lon = hotel_lon;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_locality() {
        return hotel_locality;
    }

    public void setHotel_locality(String hotel_locality) {
        this.hotel_locality = hotel_locality;
    }

    public String getHotel_offers() {
        return hotel_offers;
    }

    public void setHotel_address(String hotel_offers) {
        this.hotel_offers = hotel_offers;
    }

    public String getHotel_rating() {
        return hotel_rating;
    }

    public void setHotel_rating(String hotel_rating) {
        this.hotel_rating = hotel_rating;
    }

    public String getHotel_lat() {
        return hotel_lat;
    }

    public void setHotel_lat(String hotel_lat) {
        this.hotel_lat = hotel_lat;
    }

    public String getHotel_lon() {
        return hotel_lon;
    }

    public void setHotel_lon(String hotel_lon) {
        this.hotel_lon = hotel_lon;
    }
}

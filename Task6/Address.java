public class Address {
    private String country;
    private String city;
    private String street;
    private String houseNum;

    public Address(String info){
        String[] parts;
        parts = info.split("/");
        setCountry(parts[0]);
        setCity(parts[1]);
        setStreet(parts[2]);
        setHouseNum(parts[3]);
    }

    private void setCountry(String info){
        country = info;
    }

    private void setCity(String info){
        city = info;
    }

    private void setStreet(String info){
        street = info;
    }

    private void setHouseNum(String info){
        houseNum = info;
    }

    public String getCountry(){
        return country;
    }

    public String getCity(){
        return city;
    }

    public String getStreet(){
        return street;
    }

    public String getHouseNum(){
        return houseNum;
    }
}
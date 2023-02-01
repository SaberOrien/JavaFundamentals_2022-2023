abstract class TelephoneEntry{
    private String name;
    private Address address;
    private TelephoneNumber phoneNumber;

    public abstract String description();

    public void setName(String givenName){
        name = givenName;
    }
    
    public void setAddress(String givenAddress){
        address = new Address(givenAddress);
    }

    public void setTelephoneNumber(String givenNumber){
        phoneNumber = new TelephoneNumber(givenNumber);
    }

    public String getName(){
        return name;
    }

    public Address getAddress(){
        return address;
    }

    public TelephoneNumber getNumber(){
        return phoneNumber;
    }
}
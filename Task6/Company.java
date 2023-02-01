public class Company extends TelephoneEntry{
    Company(String setName, String setAddress, String setNumber){
        setTelephoneNumber(setNumber);
        setName(setName);
        setAddress(setAddress);
    }

    @Override
    public String description(){
        return getName()+" is located in "+getAddress().getCountry()+" in "+getAddress().getCity()+" at "+getAddress().getStreet()+" "+getAddress().getHouseNum();
    }
}
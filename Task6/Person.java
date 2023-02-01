public class Person extends TelephoneEntry{
    private String lastName;
    Person(String setName, String setLastName, String setAddress, String setNumber){
        setTelephoneNumber(setNumber);
        setName(setName);
        lastName = setLastName;
        setAddress(setAddress);
    }
    @Override
    public String description(){
        return getName()+" "+lastName+" lives in "+getAddress().getCountry()+" in "+getAddress().getCity()+" at "+getAddress().getStreet()+" "+getAddress().getHouseNum();
    }

}
import java.util.*;
import java.io.*;

public class Database{
    private TreeMap<TelephoneNumber, TelephoneEntry> databaseTM;
    private ArrayList<Person> people;
    private ArrayList<Company> companies;

    Database(){
        databaseTM = new TreeMap<>();
        people = new ArrayList<>();
        companies = new ArrayList<>();
    }

    public static void main(String[] args){ 
        Database database = new Database();
        database.generatePeople();
        database.generateCompanies();
        database.fillDatabase();
        database.printDatabase();
    }
    private void generatePeople(){
        String data[][] = {
            {"Lucas", "Forgot", "USA/New Orleans/Rose Avenue/3154", "+1 8332403627"},
            {"Toby", "Bullied", "Australia/Wonbah/Delan Road/41", "+61 485872892"},
            {"Zelda", "Hyrule", "Korea/Seosan-si/Sangseongri/208-5", "+82 1055570582"},
        };
        for(int c = 0; c < 3; c++){
            people.add(new Person(data[c][0], data[c][1], data[c][2], data[c][3]));
        }
    }

    private void generateCompanies(){
        String data[][] = {{"Amazon", "USA/New York/Hoffman Avenue/2977", "+1 5054554424"},
            {"Toyota", "Japan/Happo-cho/Hachimori Kameiieushiro/358-1172", "+81 8094801562"},
            {"Benanti", "Italy/Cerasolo/Via Goffredo Mameli/107", "+39 3111182809"},
        };
        for(int c = 0; c < 3; c++){
            companies.add(new Company(data[c][0], data[c][1], data[c][2]));
        }
    }

    private void fillDatabase(){
        for(int i = 0; i < people.size(); i++){
            databaseTM.put(people.get(i).getNumber(), people.get(i));
        }
        for(int i = 0; i < companies.size(); i++){
            databaseTM.put(companies.get(i).getNumber(), companies.get(i));
        }
    }

    public void printDatabase(){
        Set<Map.Entry<TelephoneNumber, TelephoneEntry> > entries = databaseTM.entrySet();
        Iterator<Map.Entry<TelephoneNumber, TelephoneEntry> > iterator = entries.iterator();
        Map.Entry<TelephoneNumber, TelephoneEntry> entry = null;
        while(iterator.hasNext()){
            entry = iterator.next();
            System.out.print(entry.getKey().getPhoneNumber()+"\t");
            System.out.println(entry.getValue().description());
        }
    }
}
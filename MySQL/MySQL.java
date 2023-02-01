import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.*;

public class MySQL {
    private static Scanner console;
    private static String TYPE = ".txt";
    private static String SEPARATOR = "-----";
    private static Pattern CREATE_PATTERN = Pattern.compile("^CREATE\\s+TABLE\\s+(\\S+)\\s+\\((.+?)\\)$"); 
	private static Pattern SELECT_PATTERN = Pattern.compile("^SELECT\\s+(.+?)\\s+FROM\\s+(\\S+)(?:\\s+WHERE\\s+)?(.+?)?$");
    private static Pattern INSERT_PATTERN = Pattern.compile("^INSERT\\s+INTO\\s+(\\S+)\\s+VALUES\\s+\\((.+?)\\)$");
	private static Pattern UPDATE_PATTERN = Pattern.compile("^UPDATE\\s+(\\S+)\\s+SET\\s+\\((.+?)\\)(?:\\s+WHERE\\s+)?(.+?)?$");
	private static Pattern DELETE_PATTERN = Pattern.compile("^DELETE\\s+FROM\\s+(\\S+)(?:\\s+WHERE\\s+)?(.+?)?$(?:\\s+WHERE\\s+)?(.+?)?$");

    public static void main(String[] args){
        console = new Scanner(System.in);
        System.out.println("Enter SQL query: ");
        String query = console.nextLine();
        
        while(query != null && !query.equalsIgnoreCase("exit")){
            query.trim();
            Matcher createMatch = CREATE_PATTERN.matcher(query);
			Matcher selectMatch = SELECT_PATTERN.matcher(query);
			Matcher insertMatch = INSERT_PATTERN.matcher(query);
			Matcher updateMatch = UPDATE_PATTERN.matcher(query);
			Matcher deleteMatch = DELETE_PATTERN.matcher(query);
            
            findMatch(createMatch, selectMatch, insertMatch, updateMatch, deleteMatch);
            query = console.nextLine();
        }
    }

    private static void findMatch(Matcher createMatch, Matcher selectMatch, Matcher insertMatch, Matcher updateMatch, Matcher deleteMatch){
        if(createMatch.matches()){
            executeCreate(createMatch);
        }
        else if(selectMatch.matches()){
            executeSelect(selectMatch);
        }
        else if(insertMatch.matches()){
            executeInsert(insertMatch);
        }
        else if(updateMatch.matches()){
            executeUpdate(updateMatch);
        }
        else if(deleteMatch.matches()){
            executeDelete(deleteMatch);
        }
        else{
            System.out.println("Incorrect query.");
        }
    }

    private static void executeCreate(Matcher createMatch){
        try{
            Create table = new Create();
            table.createTable(createMatch.group(1)+TYPE, createMatch.group(2), SEPARATOR);
        }
        catch(FileAlreadyExistsException FAE){
            System.out.println(FAE.getMessage());
        }
    }

    private static void executeInsert(Matcher insertMatch){
        try{
            Insert toTable = new Insert();
            toTable.insertValues(insertMatch.group(1)+TYPE, insertMatch.group(2), SEPARATOR);
        }
        catch(FileNotFoundException FNF){
            System.out.println(FNF.getMessage());
        }
    }

    private static void executeSelect(Matcher selectMatch){
        try{
            String[] conditions = separateConditions(selectMatch.group(3));
            Select targets = new Select();
            targets.selectFrom(selectMatch.group(1), selectMatch.group(2)+TYPE, conditions[0], conditions[1], SEPARATOR);
        }
        catch(FileNotFoundException FNF){
            System.out.println(FNF.getMessage());
        }
    }

    private static String[] separateConditions(String givenConditions){
        String[] conditions = {null, null};
        if(givenConditions != null){
            Pattern GROUP_BY = Pattern.compile("^(.+?)GROUP\\sBY\\s(.+?)?$");
            Matcher groupMatch = GROUP_BY.matcher(givenConditions);
            if(groupMatch.matches()){
                if(!groupMatch.group(1).equals(" ")){
                    conditions[0] = groupMatch.group(1).trim();
                }
                if(!groupMatch.group(2).equals(" ")){
                    conditions[1] = groupMatch.group(2).trim();
                }
            }
            else{
                conditions[0] = givenConditions.trim();
            }
        }
        return conditions;
    }

    private static void executeUpdate(Matcher updateMatch){
        try{
            Update records = new Update();
            records.updateTable(updateMatch.group(1)+TYPE, updateMatch.group(2), updateMatch.group(3), SEPARATOR);
        }
        catch(FileNotFoundException FNF){
            System.out.println(FNF.getMessage());
        }
    }

    private static void executeDelete(Matcher deleteMatch){
        try{
            Delete records = new Delete();
            records.deleteFromTable(deleteMatch.group(1)+TYPE, deleteMatch.group(2), SEPARATOR);
        }
        catch(FileNotFoundException FNF){
            System.out.println(FNF.getMessage());
        }
    }
}

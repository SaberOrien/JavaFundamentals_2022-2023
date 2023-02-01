import java.io.*;
import java.util.*;

public class Select extends Query{
    private String[] headers;
    private String[] targetedColumns;
    private int[] indexes;
    private String[] selectedColumns;
    private String groupBy;
    private int groupByIndex;
    private Map<String, String[]> table = new HashMap<String, String[]>();

    public void selectFrom(String target, String tableName, String condition, String groupByCondition, String separator) throws FileNotFoundException{
        if(checkIfTableExists(tableName) == false){
            throw new FileNotFoundException("Table "+tableName+" does not exist");
        }
        groupBy = groupByCondition;
        targetedColumns = target.split("\\s*,\\s*");
        try{
            readFromTable(tableName, separator, condition);
        }
        catch(IOException IE){
            System.out.println("Error while selecting from "+tableName);
        }
    }

    private void readFromTable(String tableName, String separator, String condition) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(tableName));
        String line = reader.readLine();
        headers = line.split(separator);
        selectedColumns = headers;
        try{
            determineIndexes(tableName);
            if(groupBy != null){
                determineGroupByIndex(tableName);
                while(line != null){
                    selectedColumns = line.split(separator);
                    Where findCols = new Where();
                    if((condition == null) || (findCols.evaluateCondition(headers, selectedColumns, condition) == true)){
                        addToGroupBySet();
                    }
                    line = reader.readLine();
                }
                displayMap();
            }
            else{
                while(line != null){
                    selectedColumns = line.split(separator);
                    Where findCols = new Where();
                    if((condition == null) || (findCols.evaluateCondition(headers, selectedColumns, condition) == true)){
                        printTable();
                    }
                    line = reader.readLine();
                }
            }
        }
        catch(InvalidColumnException InvC){
            System.out.println(InvC.getMessage());
        }
        catch(InvalidConditionException ICE){
            System.out.println("Cannot select without valid condition");
        }
        reader.close();
    }

    private void addToGroupBySet(){
        if(table.containsKey(selectedColumns[groupByIndex])){
            List<String> listOfKeys = new ArrayList<String>(table.keySet());
            int numOfOccurrences = 0;
            for(String tempKey : listOfKeys){
                if(tempKey.contains(selectedColumns[groupByIndex])){
                    numOfOccurrences++;
                }
            }
            table.put(selectedColumns[groupByIndex]+numOfOccurrences, selectedColumns);
        }
        else{
            table.put(selectedColumns[groupByIndex], selectedColumns);
        }
    }

    private void determineIndexes(String tableName) throws InvalidColumnException{
        if(targetedColumns[0].equals("*")){
            indexes = new int[selectedColumns.length];
            for(int i = 0; i < selectedColumns.length; i++){
                indexes[i] = i;
            }
        }
        else{
            indexes = new int[targetedColumns.length];
            for(int i = 0; i < targetedColumns.length; i++){
                int index = Arrays.asList(selectedColumns).indexOf(targetedColumns[i]);
                if(index < 0){
                    throw new InvalidColumnException(tableName, targetedColumns[i]);
                }
                indexes[i] = index;
            }
        }
    }
    
    private void displayMap(){
        for(int i : indexes){
            System.out.printf("%15s", headers[i]);
            table.remove(headers[i]);
        }
        System.out.println();
        TreeMap<String, String[]> sorted = new TreeMap<>(table);
        Set<Map.Entry<String, String[]>> mappings = sorted.entrySet();
        for(Map.Entry<String, String[]> mapping : mappings){
            for(int i : indexes){
                System.out.printf("%15s", mapping.getValue()[i]);
                //System.out.printf("%s \t %s\n", mapping.getKey(), mapping.getValue()[i]);
            }
            System.out.println();
        }
        table.clear();
        sorted.clear();
    }

    private void determineGroupByIndex(String tableName) throws InvalidColumnException{
        groupByIndex = Arrays.asList(selectedColumns).indexOf(groupBy);
        if(groupByIndex < 0){
            throw new InvalidColumnException(tableName, groupBy);
        }
    }

    private void printTable(){
        for(int i : indexes){
            System.out.printf("%15s", selectedColumns[i]);
        }
        System.out.println();
    }
}

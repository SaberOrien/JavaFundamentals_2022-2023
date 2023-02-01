import java.io.*;
import java.util.*;

public class Update extends Query{
    private String[] listOfConditions;
    private Map<String, String> targets = new HashMap<>();
    private Map<Integer, String> indexesOfTargets = new HashMap<Integer, String>();

    public void updateTable(String tableName, String thingsToSet, String condition, String separator) throws FileNotFoundException{
        if(checkIfTableExists(tableName) == false){
            throw new FileNotFoundException("Table "+tableName+" does not exist");
        }
        try{
            separateTargets(thingsToSet);
            processChange(tableName, condition, separator);
        }
        catch(IOException IE){
            System.out.println("Error while updating the table "+tableName);
        } 
    }

    private void separateTargets(String thingsToSet){
        thingsToSet = thingsToSet.trim();
        listOfConditions = thingsToSet.split("\\s*,\\s*");
        for(int i = 0; i < listOfConditions.length; i++){
            String[] condition = listOfConditions[i].split("\\s*=\\s*");
            targets.put(condition[0], condition[1]);
        }
    }

    private void processChange(String tableName, String condition, String separator) throws IOException{
        File source = new File(tableName);
        File output = new File("output.txt");
        BufferedReader reader = new BufferedReader(new FileReader(source));
        BufferedWriter writer = new BufferedWriter(new FileWriter(output));

        String line = reader.readLine();
        String[] columnValues;
        String[] headers = line.split(separator);

        try{
            getIndexesOfTargetCols(tableName, separator, headers);
            writer.write(line+"\n");
            while((line = reader.readLine()) != null){
                columnValues = line.split(separator);
                Where findCols = new Where();
                if(findCols.evaluateCondition(headers, columnValues, condition)){
                    line = modifyLine(columnValues, separator);
                }
                writer.write(line+"\n");
            }
            source.delete();
            output.renameTo(source);
        }
        catch(InvalidColumnException ICE){
            System.out.println(ICE.getMessage());
        }
        catch(InvalidConditionException ICE){
            System.out.println("Cannot update without valid condition");
        }
        reader.close();
        writer.close();
    }

    private void getIndexesOfTargetCols(String tableName, String separator, String[] headers) throws InvalidColumnException{
        for(Map.Entry<String, String> entry : targets.entrySet()){
            if(Arrays.asList(headers).indexOf(entry.getKey()) < 0){
                throw new InvalidColumnException(tableName, entry.getKey());
            }
            indexesOfTargets.put(Arrays.asList(headers).indexOf(entry.getKey()), entry.getValue());
        }
    }

    private String modifyLine(String[] currentValues, String separator){
        String newLine = "";

        for(int i = 0; i < currentValues.length; i++){
            if(indexesOfTargets.containsKey(i)){
                newLine = newLine + indexesOfTargets.get(i);
            }
            else{
                newLine = newLine + currentValues[i];
            }

            if(i != currentValues.length-1){
                newLine = newLine + separator;
            }
        }
        return newLine;
    }

}

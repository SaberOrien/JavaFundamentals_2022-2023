import java.io.*;

public class Delete extends Query {
    public void deleteFromTable(String tableName, String condition, String separator) throws FileNotFoundException{
        if(checkIfTableExists(tableName) == false){
            throw new FileNotFoundException("Table "+tableName+" does not exist");
        }
        try{
            deleteData(tableName, condition, separator);
        }
        catch(Exception E){
            System.out.println("Error while deleting from the table");
        }
    }

    private void deleteData(String tableName, String condition, String separator) throws Exception{
        File source = new File(tableName);
        File output = new File("output.txt");
        BufferedReader reader = new BufferedReader(new FileReader(source));
        BufferedWriter writer = new BufferedWriter(new FileWriter(output));

        String line = reader.readLine();
        String[] headers = line.split(separator);
        String[] values;
        try{
            writer.write(line+"\n");
            
            while((line = reader.readLine()) != null){
                values = line.split(separator);
                Where findCols = new Where();
                if(findCols.evaluateCondition(headers, values, condition) == true){
                    continue;
                }
                writer.write(line+"\n");
            }

            reader.close();
            writer.close();
            source.delete();
            output.renameTo(source);
        }
        catch(InvalidConditionException ICE){
            System.out.println("Cannot delete with invalid contidion");
        }
    }

}

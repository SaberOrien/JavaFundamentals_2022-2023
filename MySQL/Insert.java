import java.io.*;

public class Insert extends Query{
    private String[] valuesToInsert;

    public void insertValues(String tableName, String columns, String separator) throws FileNotFoundException{
        if(checkIfTableExists(tableName) == false){
            throw new FileNotFoundException("Table "+tableName+" does not exist");
        }
        try{
            checkIfInputCorrect(tableName, columns, separator);
            writeToFile(tableName, valuesToInsert, separator);
        }catch(WrongNumberOfParametersException WNO){
            System.out.println(WNO.getMessage());
        }
        catch(IOException IE){
            System.out.println("Error while inserting to table "+tableName);
        }
        catch(Exception E){
            System.out.println("Error while inserting to table "+tableName);
        }
    }

    private void checkIfInputCorrect(String tableName, String columns, String separator) throws Exception{
        valuesToInsert = columns.split("\\s*,\\s*");
        BufferedReader reader = new BufferedReader(new FileReader(tableName));
        String[] headers = reader.readLine().split(separator);
        reader.close();
        if(headers.length != valuesToInsert.length){
            throw new WrongNumberOfParametersException(tableName ,headers.length, valuesToInsert.length);
        }
    }
}

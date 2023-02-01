import java.io.*;

public class Create extends Query{
    private String[] columns;
    public void createTable(String tableName, String columnString, String separator) throws FileAlreadyExistsException{
        if(checkIfTableExists(tableName)){
            throw new FileAlreadyExistsException(tableName);
        }
        columns = columnString.split("\\s*,\\s*");
        try{
            writeToFile(tableName, columns, separator);
        }
        catch(IOException IE){
            System.out.println("Exception while creating a table");
        }
    }
}

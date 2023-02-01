import java.io.IOException;
import java.nio.file.*;
import java.io.*;

public class Query {
    public boolean checkIfTableExists(String tableName){
        Path path = Paths.get(tableName);
        if(Files.exists(path)){
            return true;
        }
        return false;
    }

    public void writeToFile(String tableName, String[] columns, String separator) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(tableName, true));
        for(int col = 0; col < columns.length; col++){
            writer.write(columns[col]);
            if(col != columns.length-1){
                writer.write(separator);
            }
        }
        writer.write("\n");
        writer.close();
    }
}

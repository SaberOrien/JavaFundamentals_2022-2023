public class FileAlreadyExistsException extends Exception{
    public FileAlreadyExistsException(String tableName){
        super(tableName+" table already exists.");
    }
}

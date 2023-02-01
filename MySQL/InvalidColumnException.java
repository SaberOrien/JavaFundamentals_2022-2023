public class InvalidColumnException extends Exception{
    public InvalidColumnException(String tableName, String columnName){
        super("Table "+tableName+" does not have column '"+columnName+"'");
    }
}

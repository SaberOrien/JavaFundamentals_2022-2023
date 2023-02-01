public class WrongNumberOfParametersException extends Exception{
    public WrongNumberOfParametersException(String tableName, int expected, int given){
        super(tableName+" expects "+expected+" parameters, but "+given+" were given");
    }
}

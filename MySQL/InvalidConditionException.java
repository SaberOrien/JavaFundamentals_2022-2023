public class InvalidConditionException extends Exception{
    public InvalidConditionException(String condition){
        super(condition+" is not a valid condition");
    }
}

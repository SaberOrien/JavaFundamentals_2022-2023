import java.util.*;
public class Where extends Query{
    private boolean status;
    private List<String> operators = Arrays.asList(new String[]{"!=", "<=", ">=", "==", "<", ">"});
    private String[] partsOfCondition;

    public boolean evaluateCondition(String[] headers, String[] values, String condition) throws InvalidConditionException{
        if((Arrays.equals(headers, values)) || (condition == null)){
            return true;
        }
        try{
            int operatorIndex = getConditionIndex(condition);
            int selectedColumnIndex = getColumnIndex(headers, condition);
            status = evaluate(operatorIndex, selectedColumnIndex, values);
        }
        catch(InvalidConditionException ICE){
            System.out.println(ICE.getMessage());
            throw new InvalidConditionException(condition);
        }

        return status;
    }

    private int getConditionIndex(String condition) throws InvalidConditionException{
        int foundAt;
        for(foundAt = 0; foundAt < operators.size(); foundAt++){
            if(condition.contains(operators.get(foundAt))){
                partsOfCondition = condition.split(operators.get(foundAt));
                for(int i = 0; i < partsOfCondition.length; i++){
                    partsOfCondition[i] = partsOfCondition[i].trim();
                }
                break;
            }
        }

        if(foundAt == operators.size()){
            throw new InvalidConditionException(condition);
        }
    
        return foundAt;
    }

    private int getColumnIndex(String[] headers, String condition) throws InvalidConditionException{
        int foundAt;
        for(foundAt = 0; foundAt < headers.length; foundAt++){
            if(headers[foundAt].equals(partsOfCondition[0])){
                break;
            }
        }
        if(foundAt == headers.length){
            throw new InvalidConditionException(condition);
        }
        return foundAt;
    }

    private boolean evaluate(int opIndex, int colIndex, String[] values){
        if("==".equals(operators.get(opIndex))){
            return values[colIndex].equals(partsOfCondition[1]);
        }
        else if("!=".equals(operators.get(opIndex))){
            return !values[colIndex].equals(partsOfCondition[1]);
        }
        else if(">=".equals(operators.get(opIndex))){
            if(values[colIndex].compareTo(partsOfCondition[1]) >= 0){
                return true;
            }
        }
        else if(">".equals(operators.get(opIndex))){
            if(values[colIndex].compareTo(partsOfCondition[1]) > 0){
                return true;
            }
        }
        else if("<=".equals(operators.get(opIndex))){
            if(values[colIndex].compareTo(partsOfCondition[1]) <= 0){
                return true;
            }
        }
        else if("<".equals(operators.get(opIndex))){
            if(values[colIndex].compareTo(partsOfCondition[1]) < 0){
                return true;
            }
        }
        return false;
    }
}

import java.util.ArrayList;

public class Vector {
    private ArrayList<Integer> coords = new ArrayList<>();

    public Vector(Vector v2){
        coords = v2.coords;
    }
    
    public Vector(String input){
        String[] parts = input.split(",");
        setCoordinates(parts);
    }

    private void setCoordinates(String[] parts){
        int singleCoord;
        for(int counter = 0; counter < parts.length; counter++){
            try{
                singleCoord = Integer.parseInt(parts[counter]);
                coords.add(singleCoord);
            }
            catch(NumberFormatException Nfe){
                continue;
            }
        }
    }

    public int getSize(){
        return coords.size();
    }

    public ArrayList<Integer> getCoords(){
        return coords;
    }

    public void addVector(Vector v2) throws DifferentVectorsLengthsException{
        if(coords.size() != v2.coords.size()){
            throw new DifferentVectorsLengthsException(coords.size(), v2.coords.size());
        }
        for(int counter = 0; counter < coords.size(); counter++){
            coords.set(counter, coords.get(counter) + v2.coords.get(counter));
        }
    }
}

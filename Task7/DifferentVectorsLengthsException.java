import java.util.ArrayList;
public class DifferentVectorsLengthsException extends Exception{
    private ArrayList<Integer> sizes = new ArrayList<>();

    public DifferentVectorsLengthsException(ArrayList<Vector> givenVectors){
        sizes = setSizes(givenVectors);
    }

    public DifferentVectorsLengthsException(int firstVectSize, int secondVectSize){
        sizes.add(firstVectSize);
        sizes.add(secondVectSize);
    }

    private ArrayList<Integer> setSizes(ArrayList<Vector> givenVectors){
        ArrayList<Integer> sizesCollection = new ArrayList<>();
        for(int counter = 0; counter < givenVectors.size(); counter++){
            sizesCollection.add(givenVectors.get(counter).getSize());
        }
        return sizesCollection;
    }
    
    public ArrayList<Integer> getSize(){
        return sizes;
    }
}

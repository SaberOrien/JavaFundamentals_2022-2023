import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TestVector {
    private ArrayList<Vector> givenVectors;
    private Scanner input;
    public TestVector(){
        givenVectors = new ArrayList<>();
        input = new Scanner(System.in);
    }

    public static void main(String[] args){
        boolean incorrectRun = true;
        while(incorrectRun){
            try{
                TestVector vectors = new TestVector();
                vectors.readVectors();
                Vector result = vectors.addVectors();
                vectors.writeToFile(result);
                incorrectRun = false;
            }
            catch(DifferentVectorsLengthsException Dvle){
                System.out.println("Exception occured due to vectors having lengths: "+Dvle.getSize());
                //System.out.println("Exception due to vector1 length being "+((Dvle.getSize().get(0) > Dvle.getSize().get(1)) ? "greater" : "smaller")+" than vector 2");
                System.out.println(Dvle.getSize().get(0) * Dvle.getSize().get(1));
                System.out.println("Re-enter the vectors");
            }
            catch(IOException Ioe){
                System.out.println("Error while writing to the file.");
                System.out.println("Ending program");
                incorrectRun = false;
            }
        }
    }

    private void readVectors(){
        int numOfVectors = 0;
        String nextRun;
        do{
            numOfVectors++;
            System.out.println("Enter coordinates for the "+numOfVectors+". vector:");
            givenVectors.add(new Vector(input.nextLine()));
            //System.out.println("You inserted"+givenVectors.get(numOfVectors-1).getCoords());
            System.out.println("Do you want to enter another vector? y / n");
            nextRun = input.nextLine();
        } while(nextRun.equals("y") == true);
    }

    private Vector addVectors() throws DifferentVectorsLengthsException{
        Vector result = new Vector(givenVectors.get(0));
        for(int counter = 0; counter < givenVectors.size()-1; counter++){
            if(givenVectors.get(counter).getSize() != givenVectors.get(counter+1).getSize()){
                throw new DifferentVectorsLengthsException(givenVectors);
            }
            result.addVector(givenVectors.get(counter+1));
        }
        return result;
    }

    private void writeToFile(Vector result) throws IOException {
        int numOfVectors = givenVectors.size();
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        writer.write("Addition of "+numOfVectors+" vectors resulted in a vector: \n[");
       for(int counter = 0; counter < result.getCoords().size(); counter++){
            writer.write(result.getCoords().get(counter).toString());
            if(counter != result.getCoords().size()-1){
                writer.write(", ");
            }
       }
       writer.write("]");
       writer.close();
       input.close();
    }
}

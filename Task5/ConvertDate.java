import java.io.*;
import java.util.*;

public class ConvertDate{
	public static void main(String[] args){
		new ConvertDate().getFileInput();
	}
	private void getFileInput(){
		try{
			BufferedReader br = new BufferedReader(
				new FileReader("InputData.txt"));
			LinkedList<String> readDates = new LinkedList<String>();
			String singleData;
			while((singleData = br.readLine()) != null){
				if(singleData.trim().isEmpty() == false){
					readDates.add(singleData);
				}
			}
			br.close();
			LinkedList<MyData> inputDates = new LinkedList<MyData>();
			for(int datesCounter = 0; datesCounter < readDates.size(); datesCounter++){
				MyData dataToTest = new MyData(readDates.get(datesCounter));
				if(dataToTest.getIfDateValid() == true){
					inputDates.add(dataToTest);
				}
			}
			sortDates(inputDates);
			writeOutputFile(inputDates);
		}catch(Exception ex){
			return;
		}
	}

	private void sortDates(LinkedList<MyData> inputDates){
		int numOfCorrectDates = inputDates.size();
		for(int firstDate = 0; firstDate < numOfCorrectDates - 1; firstDate++){
			for(int secondDate = 0; secondDate < numOfCorrectDates - firstDate - 1; secondDate++){
				if(inputDates.get(secondDate).getYear() > inputDates.get(secondDate+1).getYear()){
					swapPlaces(inputDates, secondDate);
				}
				if(inputDates.get(secondDate).getYear() == inputDates.get(secondDate+1).getYear()){
					if(inputDates.get(secondDate).getMonth() > inputDates.get(secondDate+1).getMonth()){
						swapPlaces(inputDates, secondDate);
					}
					if(inputDates.get(secondDate).getMonth() == inputDates.get(secondDate+1).getMonth()){
						if(inputDates.get(secondDate).getDay() > inputDates.get(secondDate+1).getDay()){
							swapPlaces(inputDates, secondDate);
						}
					}
				}
			}
		}
	}

	private void swapPlaces(LinkedList<MyData> inputDates, int pos){
		MyData firstDate = inputDates.get(pos);
		MyData secondDate = inputDates.get(pos+1);
		inputDates.set(pos, secondDate);
		inputDates.set(pos+1, firstDate);
	}

	private void writeOutputFile(LinkedList<MyData> inputDates){
		try{
			BufferedWriter bw = new BufferedWriter(
				new FileWriter("MyData.txt"));
			int numOfOccurrences;
			int numOfDates = inputDates.size();
			for(int currentDate = 0; currentDate < numOfDates; currentDate++){
				numOfOccurrences = 1;
				for(int compareCount = currentDate+1; compareCount < numOfDates; compareCount++){
					if(inputDates.get(currentDate).getDate().equals(inputDates.get(compareCount).getDate()) == true){
						numOfOccurrences++;
					}
				}
				bw.write("day = " + inputDates.get(currentDate).getDay() + 
					", month = " + inputDates.get(currentDate).getMonth() + 
					", year = " + inputDates.get(currentDate).getYear() + 
					", weekday = " + inputDates.get(currentDate).getDayOfWeek() + 
					", occurred: " + numOfOccurrences + "\n");
				currentDate += numOfOccurrences-1;
			}
			bw.close();
		}catch(Exception ex){
			return;
		}
	}
}


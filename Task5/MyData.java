import java.io.*;
import java.time.*;
import java.text.*;
import java.util.*;

public class MyData {
	final String[] patterns = {
		"\\d{2}/\\d{2}/\\d{4}\s.*(Monday|Tuesday|Wednesday|Thursday|Friday|Saturda|Sunday).*",
		"\\d{2}/\\d{1}/\\d{4}\s.*(Monday|Tuesday|Wednesday|Thursday|Friday|Saturda|Sunday).*",
		"\\d{4}\\-\\d{2}\\-\\d{2}\s.*(Monday|Tuesday|Wednesday|Thursday|Friday|Saturda|Sunday).*",
		".*(Monday|Tuesday|Wednesday|Thursday|Friday|Saturda|Sunday).*\s\\d{2}.\\d{2}.\\d{4}"
	};

	final int[] positionWeekDay = {3, 3, 3, 0};
	
	final SimpleDateFormat[] formats = {
		new SimpleDateFormat("dd/MM/yyyy EEEE"),
		new SimpleDateFormat("dd/M/yyyy EEEE"),
		new SimpleDateFormat("yyyy-MM-dd EEEE"),
		new SimpleDateFormat("EEEE dd.MM.yyyy")
	};
	final SimpleDateFormat theEndFormat = new SimpleDateFormat("dd-MM-yyyy EEEE");
	final SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("EEEE");
	final SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
	final SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
	final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

	private String input;
	private int day;
	private int month;
	private int year;
	private String weekDay;
	private Date date;
	private boolean isValidDate;

	MyData(String inputData){
		input = inputData;
		isValidDate = convertToDate();
	}

	private int determinePattern(){
		int matchFound = 0;
		int patternNum = -1;
		int numOfPatterns = patterns.length;
		for (patternNum = 0; patternNum < numOfPatterns; patternNum++){
			if(input.matches(patterns[patternNum])){
				matchFound = 1;
				break;
			}
		}
		if(matchFound == 0){
			patternNum = -1;
		}
		return patternNum;
	}

	private boolean convertToDate(){
		int matchesPatternNo = determinePattern();
		if(matchesPatternNo >= 0){
			extractWeekday(matchesPatternNo);
			try{
				date = formats[matchesPatternNo].parse(input);
				String actuallDayOfWeek = dayOfWeekFormat.format(date);
				if(weekDay.equals(actuallDayOfWeek) == true){
					setDay();
					setMonth();
					setYear();
					setWeekday(actuallDayOfWeek);
					return true;
				}			
			}catch(Exception ex){
				return false;
			}
		}
		return false;
	}

	private void extractWeekday(int matchesPatternNo){
		String copyOfDate = input;
		String[] partsOfDate = copyOfDate.split("[/|\\-|.|\s]");
		weekDay = partsOfDate[positionWeekDay[matchesPatternNo]];
		
	}

	private void setDay(){
		try {
			day = Integer.parseInt(dayFormat.format(date));
		} catch (Exception e) {
			System.out.println("Error setting day.");
		}
	}

	private void setMonth(){
		try {
			month = Integer.parseInt(monthFormat.format(date));
		} catch (Exception e) {
			System.out.println("Error setting month.");
		}
	}

	private void setYear(){
		try {
			year = Integer.parseInt(yearFormat.format(date));
		} catch (Exception e) {
			System.out.println("Error setting year.");
		}
	}

	private void setWeekday(String correctWeekDay){
		weekDay = correctWeekDay;
	}

	public int getDay(){
		return day;
	}

	public int getMonth(){
		return month;
	}

	public int getYear(){
		return year;
	}

	public String getDayOfWeek(){
		return weekDay;
	}

	public boolean getIfDateValid(){
		return isValidDate;
	}

	public String getDate(){
		return theEndFormat.format(date);
	}
}

public class TelephoneNumber implements Comparable<TelephoneNumber>{
	private String countryCode;
	private String localNumber;
	private String fullNumber;

	public TelephoneNumber(String number) {
		String[] parts;
		fullNumber = number;
		number = number.replace("+", "");
		parts = number.split(" ");
		countryCode = parts[0];
		localNumber = parts[1];
	}

	public String getPhoneNumber(){
		return fullNumber;
	}

	private String getCountryCode(){
		return countryCode;
	}

	private String getLocalNumber(){
		return localNumber;
	}

	public int compareTo(TelephoneNumber o){
		if(this.getCountryCode().compareTo(o.getCountryCode()) == 0){
			return this.getLocalNumber().compareTo(o.getLocalNumber());
		}
		else if(!this.getCountryCode().equals(o.getCountryCode())){
			return this.getCountryCode().compareTo(o.getCountryCode());
		}
		return this.getLocalNumber().compareTo(o.getLocalNumber());
	}
}

package resources;

public class Escala {
	public String scaleName;
	public String literal;  // example '2212221"
	int[] pattern = new int[31];
	static int exeMultiplier;
	Escala (String name, String literal ){
		this.scaleName = name;
		this.literal = literal;
		//System.out.println(name+" "+literal);

		int sum = 0;
		for (int g=0; g<30; g++){
			this.pattern[g] = sum;

	//		sum = sum +	Integer.parseInt( this.literal.substring(g % literal.length(), g % literal.length()+1)) % this.literal.length();
			sum = sum +	Integer.parseInt( String.valueOf(this.literal.charAt((g*exeMultiplier) % literal.length()))) % this.literal.length();
		}

		//subPrintChars();
	}

	static void setExeMultiplier (int multiplier){
		exeMultiplier = multiplier;
	
	}

	void printChars (){
		for (int g=0; g<30; g++){
			System.out.print(pattern[g]+":" );
		}
		
		System.out.println();

	}
}

class Acorde extends Escala{
	String chordName;
	int[] scaleDegrees;
	Finger[] fingers = new Finger[4];
	
	Acorde(String name, String literal) {
		super(name, literal);
		// TODO Auto-generated constructor stub
	}
	
   Acorde(String name, String literal,Finger[] fingers){
		super(name, literal);
		this.fingers = fingers;
   }
   
}
class Finger {
	int fret;
	int stringStart;
	int stringStop;
	public Finger(int fret, int stringStart, int stringStop){
		this.fret = fret;
		this.stringStart=stringStart;
		this.stringStop = stringStop;
	}
}


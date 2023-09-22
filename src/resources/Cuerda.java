package resources;

import java.util.Arrays;

public class Cuerda {
	public String[] ordenDeNotas = {"C ","C#","D ","D#","E ","F ","F#","G ","G#","A ","A#","B "};

int stringTone;
String stringName;
int[] frets = new int [70];


Cuerda (int t, String n){
	this.stringTone = t;
	this.stringName = n;
}

public void calculate (Escala scale, int tonality){
	//System.out.println(stringName+" "+ordenDeNotas[stringTone]);
	//pattern = new int[51];
	Arrays.fill(frets,-1);
	for (int t=0;t<scale.pattern.length; t++){
		if ( (scale.pattern[t] + tonality) >= stringTone){
		int fret = ( scale.pattern[t] + tonality - (stringTone)-12 ) ;	// PROBLEMA
		if ( fret > -1 && fret<frets.length) {										// Cuando tonality es alto fret no pasa por los trastes bajos...
			frets[fret] = scale.pattern[t];
			//System.out.print(fret+" "+scale.pattern.length+" "+fret+"\n");
		}
		}
	}
	System.out.println();
}

}

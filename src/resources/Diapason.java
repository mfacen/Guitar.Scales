package resources;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.*;

import javax.swing.JPanel;

class Diapason extends JPanel{									//CLASE DIAPASON
	

	/**
	 * 
	 */
	private final GuitarScalesUI guitarScalesUI;

	/**
	 * @param guitarScalesUI
	 */
	Diapason(GuitarScalesUI guitarScalesUI) {
		this.guitarScalesUI = guitarScalesUI;
	}

	
		@Override
	    public Dimension getPreferredSize() {
	        return new Dimension(250,220);
	    }
		
		
		@Override
	public void paintComponent(Graphics gg){				//PAINT DE DIAPASON
			Graphics2D g = (Graphics2D) gg;
		super.paintComponents(g);


		g.setColor(new Color ( 120,120,120));

		g.fillRect(0,0,24*this.guitarScalesUI.FRET_SIZE+this.guitarScalesUI.X_OFFSET,7*this.guitarScalesUI.STRING_SEPARATION+this.guitarScalesUI.Y_OFFSET);
		g.setColor(Color.black);

		//       g.clearRect(X_OFFSET-CIRCLE_SIZE/2-FRET_SIZE/3,Y_OFFSET-CIRCLE_SIZE/2,24*FRET_SIZE+X_OFFSET,6*STRING_SEPARATION+Y_OFFSET);
		g.fillOval( 2*this.guitarScalesUI.FRET_SIZE+this.guitarScalesUI.X_OFFSET+this.guitarScalesUI.FRET_SIZE/2-2, this.guitarScalesUI.STRING_SEPARATION*numOfStrings-this.guitarScalesUI.STRING_SEPARATION/2+this.guitarScalesUI.Y_OFFSET-2,5, 5);
		g.fillOval( 4*this.guitarScalesUI.FRET_SIZE+this.guitarScalesUI.X_OFFSET+this.guitarScalesUI.FRET_SIZE/2-2, this.guitarScalesUI.STRING_SEPARATION*numOfStrings-this.guitarScalesUI.STRING_SEPARATION/2+this.guitarScalesUI.Y_OFFSET-2,5, 5);
		g.fillOval( 6*this.guitarScalesUI.FRET_SIZE+this.guitarScalesUI.X_OFFSET+this.guitarScalesUI.FRET_SIZE/2-2, this.guitarScalesUI.STRING_SEPARATION*numOfStrings-this.guitarScalesUI.STRING_SEPARATION/2+this.guitarScalesUI.Y_OFFSET-2,5, 5);
		g.fillOval( 8*this.guitarScalesUI.FRET_SIZE+this.guitarScalesUI.X_OFFSET+this.guitarScalesUI.FRET_SIZE/2-2, this.guitarScalesUI.STRING_SEPARATION*numOfStrings-this.guitarScalesUI.STRING_SEPARATION/2+this.guitarScalesUI.Y_OFFSET-2,5, 5);
		g.fillOval( 11*this.guitarScalesUI.FRET_SIZE+this.guitarScalesUI.X_OFFSET+this.guitarScalesUI.FRET_SIZE/3-2, this.guitarScalesUI.STRING_SEPARATION*numOfStrings-this.guitarScalesUI.STRING_SEPARATION/2+this.guitarScalesUI.Y_OFFSET-2,5, 5);
		g.fillOval( 11*this.guitarScalesUI.FRET_SIZE+this.guitarScalesUI.X_OFFSET+this.guitarScalesUI.FRET_SIZE/3*2-2, this.guitarScalesUI.STRING_SEPARATION*numOfStrings-this.guitarScalesUI.STRING_SEPARATION/2+this.guitarScalesUI.Y_OFFSET-2,5, 5);
		g.drawString(">",this.guitarScalesUI.sliderMaskMin.getValue()*this.guitarScalesUI.FRET_SIZE-this.guitarScalesUI.FRET_SIZE/2-(this.guitarScalesUI.CIRCLE_SIZE/2)+this.guitarScalesUI.X_OFFSET, numOfStrings*this.guitarScalesUI.STRING_SEPARATION+this.guitarScalesUI.Y_OFFSET-(this.guitarScalesUI.CIRCLE_SIZE/2));
		g.drawString("<",this.guitarScalesUI.sliderMaskMax.getValue()*this.guitarScalesUI.FRET_SIZE-this.guitarScalesUI.FRET_SIZE/2-(this.guitarScalesUI.CIRCLE_SIZE/2)+this.guitarScalesUI.X_OFFSET, numOfStrings*this.guitarScalesUI.STRING_SEPARATION+this.guitarScalesUI.Y_OFFSET-(this.guitarScalesUI.CIRCLE_SIZE/2));

		for (int c=0; c<numOfStrings ; c++ ){
			g.setStroke(new BasicStroke(4-(numOfStrings-c)/2+1));
			g.drawLine(this.guitarScalesUI.X_OFFSET,c*this.guitarScalesUI.STRING_SEPARATION+this.guitarScalesUI.Y_OFFSET-((0)),guitarScalesUI.getWidth(),c*this.guitarScalesUI.STRING_SEPARATION+this.guitarScalesUI.Y_OFFSET-((0)));
			g.setStroke(new BasicStroke(1));

			for ( int t=0 ; t < 24 ;t++  ){
				
				g.drawLine(t*this.guitarScalesUI.FRET_SIZE+this.guitarScalesUI.X_OFFSET, this.guitarScalesUI.Y_OFFSET, t*this.guitarScalesUI.FRET_SIZE+this.guitarScalesUI.X_OFFSET, this.guitarScalesUI.Y_OFFSET + this.guitarScalesUI.STRING_SEPARATION * (numOfStrings-1));
				//g.drawLine(fretPosition(t), Y_OFFSET, fretPosition(t), Y_OFFSET + STRING_SEPARATION * 5);
				if (GuitarScalesUI.strings[c].frets[t] != -1){
					g.setColor(new Color(30,30,30));
			//SELECT COLOR
					if (GuitarScalesUI.strings[c].frets[t] % 12 == 0){g.setColor( Color.red);}		// Root
					if (GuitarScalesUI.strings[c].frets[t] % 12 == 4){g.setColor( Color.orange);}	// Major 3rd
					if (GuitarScalesUI.strings[c].frets[t] % 12 == 3){g.setColor( Color.orange);}	// Minor 3rd
					if (GuitarScalesUI.strings[c].frets[t] % 12 == 7){g.setColor( Color.blue);}		// Fifth
					if (GuitarScalesUI.strings[c].frets[t] % 12 == 10){g.setColor( Color.magenta);}		// Minor 7th
					if (GuitarScalesUI.strings[c].frets[t] % 12 == 11){g.setColor( Color.magenta);}		// Major 7th
					
			// FLASH NOTE
					if ( (GuitarScalesUI.strings[c].frets[t] % 12 == GuitarScalesUI.getTonality() + this.guitarScalesUI.flashNote) && 
						 (this.guitarScalesUI.flashNotes == true)){ //    if flashnotes = true and note = flashnote
						  if (t >= this.guitarScalesUI.sliderMaskMin.getValue()&&
						 (t <= this.guitarScalesUI.sliderMaskMax.getValue() ))     //  if in bounds of Mask flash note.
						 {g.setColor( Color.white);}		// FlashNote
					}
			// APPLY MASK
					if (  ( t < this.guitarScalesUI.sliderMaskMin.getValue() )   ||    ( t > this.guitarScalesUI.sliderMaskMax.getValue()  )   ){ // If out of bounds from mask 
						g.setColor(new Color(g.getColor().getRed(),g.getColor().getGreen(),g.getColor().getBlue(),this.guitarScalesUI.sliderMaskOpacity.getValue()));// darken up
					}
			// SPECIAL CASE FRET 0
					if (t==0){
						g.fillOval(this.guitarScalesUI.X_OFFSET-(this.guitarScalesUI.CIRCLE_SIZE)-5, c*this.guitarScalesUI.STRING_SEPARATION+this.guitarScalesUI.Y_OFFSET-(this.guitarScalesUI.CIRCLE_SIZE/2), this.guitarScalesUI.CIRCLE_SIZE, this.guitarScalesUI.CIRCLE_SIZE);
					}
			// NORMAL NOTE
					else{

						g.fillOval(t * this.guitarScalesUI.FRET_SIZE
								- this.guitarScalesUI.FRET_SIZE / 2
								- (this.guitarScalesUI.CIRCLE_SIZE / 2)
								+ this.guitarScalesUI.X_OFFSET, c
								* this.guitarScalesUI.STRING_SEPARATION
								+ this.guitarScalesUI.Y_OFFSET
								- (this.guitarScalesUI.CIRCLE_SIZE / 2),
								this.guitarScalesUI.CIRCLE_SIZE,
								this.guitarScalesUI.CIRCLE_SIZE);
						
						// SHOW SCALE NOTES
						if ( this.guitarScalesUI.showNotes ){
							g.setColor(Color.WHITE);
							// APPLY MASK
							if (  ( t < this.guitarScalesUI.sliderMaskMin.getValue() )   ||    ( t > this.guitarScalesUI.sliderMaskMax.getValue()  )   ){ // If out of bounds from mask 
								g.setColor(new Color(g.getColor().getRed(),g.getColor().getGreen(),g.getColor().getBlue(),this.guitarScalesUI.sliderMaskOpacity.getValue()));// darken up
							}
							g.setFont(new Font("default", Font.BOLD, 12));
							g.drawString(this.guitarScalesUI.ordenDeNotas [GuitarScalesUI.strings[c].frets[t] % 12],
									t * this.guitarScalesUI.FRET_SIZE
									- this.guitarScalesUI.FRET_SIZE / 2
									- (this.guitarScalesUI.CIRCLE_SIZE / 4)
									+ this.guitarScalesUI.X_OFFSET,
									c * this.guitarScalesUI.STRING_SEPARATION
									+ this.guitarScalesUI.Y_OFFSET
									+ (this.guitarScalesUI.CIRCLE_SIZE / 2) - 2);
						}
						// SHOW SCALE GRADES
						if ( this.guitarScalesUI.showGrades ){
							g.setColor(Color.WHITE);
							g.setFont(new Font("default", Font.BOLD, 12));

							// APPLY MASK
							if (  ( t < this.guitarScalesUI.sliderMaskMin.getValue() )   ||    ( t > this.guitarScalesUI.sliderMaskMax.getValue()  )   ){ // If out of bounds from mask 
								g.setColor(new Color(g.getColor().getRed(),g.getColor().getGreen(),g.getColor().getBlue(),this.guitarScalesUI.sliderMaskOpacity.getValue()));// darken up
							}
							int grade = GuitarScalesUI.strings[c].frets[t] % 12 - guitarScalesUI.tonality;
							g.drawString(this.guitarScalesUI.grades[grade],
									t * this.guitarScalesUI.FRET_SIZE
									- this.guitarScalesUI.FRET_SIZE / 2
									- (this.guitarScalesUI.CIRCLE_SIZE / 4)
									+ this.guitarScalesUI.X_OFFSET,
									c * this.guitarScalesUI.STRING_SEPARATION
									+ this.guitarScalesUI.Y_OFFSET
									+ (this.guitarScalesUI.CIRCLE_SIZE / 2));
						}
					}
					if (this.guitarScalesUI.flagFingered){								///  PINTA ACORDES
						if (this.guitarScalesUI.fingeredList[this.guitarScalesUI.comboFingeredChord.getSelectedIndex()].fingers != null ){
							for ( int i=0 ; i<4 ; i++ ){
								if (this.guitarScalesUI.fingeredList[this.guitarScalesUI.comboFingeredChord.getSelectedIndex()].fingers[i].stringStart != 0 ) {
									g.setColor(Color.GREEN);
									g.fillOval(  (this.guitarScalesUI.fingeredList[this.guitarScalesUI.comboFingeredChord.getSelectedIndex()].fingers[i].fret-1) *
											this.guitarScalesUI.FRET_SIZE
											- this.guitarScalesUI.FRET_SIZE / 2
											- (this.guitarScalesUI.CIRCLE_SIZE / 2)
											+ this.guitarScalesUI.X_OFFSET, 
											(this.guitarScalesUI.fingeredList[this.guitarScalesUI.comboFingeredChord.getSelectedIndex()].fingers[i].stringStart+1)
											* this.guitarScalesUI.STRING_SEPARATION
											+ this.guitarScalesUI.Y_OFFSET
											- (this.guitarScalesUI.CIRCLE_SIZE / 2),
											this.guitarScalesUI.CIRCLE_SIZE,
											this.guitarScalesUI.CIRCLE_SIZE);
									
								}
							}
						}
					}
					g.setColor(Color.black);
				}
			}
		}

	}

	public int fretPosition( int fret){
		float length = 400;
		float position =  (float) (    this.guitarScalesUI.X_OFFSET +       length - ( length / Math.pow( 2 , (fret / 12))));
		return (int) position;


	}
	static int numOfStrings = 6;
	public static void setNumberOfStrings(int nStr){numOfStrings = nStr;}
}
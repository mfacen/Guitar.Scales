package resources;

//import java.awt.BorderLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.naming.Context;
import javax.sound.midi.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument.Content;

import com.sun.media.sound.SoftSynthesizer;


public class TestRun extends JFrame {
	public String[] ordenDeNotas = {"C ","C#","D ","D#","E ","F ","F#","G ","G#","A ","A#","B "};

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TestRun myWindow = new TestRun();
		myWindow.setSize(1000, 400);
		myWindow.setVisible(true);
	}
	
	public void testRun(){
		
		
		Escala escala1 = new Escala ( "Mayor" , "2212221");
		Escala escala2 = new Escala ( "Menor" , "2122212");
		final int tonality = 7;
System.out.println("1");
		Cuerda [] cuerdas = new Cuerda [7];
		  
		  cuerdas[0] = new Cuerda(4,"1st");
		  cuerdas[1] = new Cuerda(11,"2nd");
		  cuerdas[2] = new Cuerda(7,"3rd");
		  cuerdas[3] = new Cuerda(2,"4th");
		  cuerdas[4] = new Cuerda(9,"5th");
		  cuerdas[5] = new Cuerda(4,"6th");	
		  for ( int t=0; t<6; t++) {
				cuerdas[t].calculate(escala1, tonality);
			}
		  System.out.println("2");
			
		  
		  /*try{
		         Create a new Sythesizer and open it. Most of 
		         * the methods you will want to use to expand on this 
		         * example can be found in the Java documentation here: 
		         * https://docs.oracle.com/javase/7/docs/api/javax/sound/midi/Synthesizer.html
		         
		        Synthesizer midiSynth = MidiSystem.getSynthesizer(); 
		        midiSynth.open();

		        //get and load default instrument and channel lists
		        Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
		        MidiChannel[] mChannels = midiSynth.getChannels();

		        midiSynth.loadInstrument(instr[0]);//load an instrument


		        mChannels[0].noteOn(60, 100);//On channel 0, play note number 60 with velocity 100 
		        try { Thread.sleep(100); // wait time in milliseconds to control duration
		        } catch( InterruptedException e ) { }
		        mChannels[0].noteOff(60);//turn of the note


		      } catch (MidiUnavailableException e) {}*/
		  
	
	JFrame myFrame = new JFrame();
    myFrame.setSize (1000,700);
    //JPanel diapason = new JPanel();
    GuitarScalesUI diapason = new GuitarScalesUI();
    diapason.setSize(new Dimension (1000,300));
    diapason.setVisible(true);
    JTextField text = new JTextField();
    JButton button = new JButton();
    //button.addActionListener(new ActionEvent());
    BorderLayout layout = new BorderLayout();
    
    myFrame.setLayout(layout);
    //button.locate(200, 500);
    button.setVisible(true);
    myFrame.getContentPane().add(button,BorderLayout.PAGE_START);
    myFrame.getContentPane().add(text,BorderLayout.PAGE_START);
    myFrame.getContentPane().add(diapason,BorderLayout.CENTER);
    myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    System.out.println("3");

	myFrame.setVisible(true);
	diapason.requestFocus();
    myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//myFrame.repaint();
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
}
	
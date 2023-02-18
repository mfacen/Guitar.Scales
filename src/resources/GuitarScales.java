package resources;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sound.midi.Sequence;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class GuitarScales implements ActionListener{
static int tonality=2;
static 	Cuerda [] cuerdas = new Cuerda [7];
static int escala = 0;//new Escala ("MAYOR","2212221");
static Escala[] escalaList = new Escala[20];
	public GuitarScales() {
		// TODO Auto-generated constructor stub
		
	
	}

	/**
	 * @param args
	 */
	
    public static void main(String args[])
    {
        Runnable runnable = new Runnable()				///  Esto es para que corra en el Event Thread Dispatcher
        {
            public void run()
            {
                GuitarScales gui = new GuitarScales();
                gui.go();								// Llama a metodo Go de Clase GuitarScales
            }
        };      
        SwingUtilities.invokeLater(runnable);
    }
	
	public void go() {
		
	//	  createScales();
//			  createStrings();
	
		  
		// TODO Auto-generated method stub
		JFrame myWindow = new JFrame();
		
		JTextField text = new JTextField();
	    JButton button = new JButton();
	    button.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				GuitarScales.tonality++;
			}});
	    BorderLayout layout = new BorderLayout();
	    
	    myWindow.setLayout(layout);
	    
	    GuitarScalesUI guitarGui = new GuitarScalesUI();
		guitarGui.setVisible(true);
		//guitarGui.setLocation(20,80);
		guitarGui.setPreferredSize(new Dimension(800,200));
		
	    button.setSize(50, 20);
	    button.setVisible(true);
	    myWindow.getContentPane().add(button,BorderLayout.PAGE_START);
	    myWindow.getContentPane().add(text,BorderLayout.PAGE_END);
	    myWindow.getContentPane().add(guitarGui,BorderLayout.CENTER);
	    myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		myWindow.setSize(1000,500);
		myWindow.setVisible(true);		
		myWindow.add(guitarGui);
		//guitarGui.requestFocus();
		//guitarGui.savePreferences();
		//myWindow.pack();

	}
	


	
	
			
		 
	

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}

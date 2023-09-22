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
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;



public class GuitarScales implements ActionListener{


	
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

		JFrame myWindow = new JFrame();
		
		JLabel text = new JLabel("2023 - Michel Facen");
	    JLabel title = new JLabel("Guitar Scales");
	    BorderLayout layout = new BorderLayout();
	    
	    myWindow.setLayout(layout);
	    
	    GuitarScalesUI guitarGui = new GuitarScalesUI();
		guitarGui.setVisible(true);

		myWindow.setTitle("Guitar Scales");
	    myWindow.getContentPane().add(title,BorderLayout.PAGE_START);
	    myWindow.getContentPane().add(text,BorderLayout.PAGE_END);
	    myWindow.getContentPane().add(guitarGui,BorderLayout.CENTER);
	    myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		//myWindow.setSize(1000,650);
		myWindow.setVisible(true);	
		myWindow.pack();

	}
	


	
	
			
		 
	

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}

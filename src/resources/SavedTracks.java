package resources;

import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javazoom.jl.player.advanced.jlap;

//public class 
public class SavedTracks extends JPanel implements ActionListener {
    JButton btnPlay = new JButton("Play");
    JButton btnStop = new JButton("Stop");
    JButton btnMark = new JButton("Mark");
    JTextArea list = new JTextArea();

    MP3Player mp3Player;

    public SavedTracks(GuitarScalesUI guitarScalesUI) {
        mp3Player = new MP3Player();
        btnPlay.addActionListener(this);
        btnStop.addActionListener(this);
        btnMark.addActionListener(this);

        add(btnPlay);
        add(btnStop);
        add(btnMark);
        add(list);


    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnPlay)){mp3Player.run();}
        if (e.getSource().equals(btnStop)){mp3Player.stop();}
        if (e.getSource().equals(btnMark)){mp3Player.play(0);}
    }

}

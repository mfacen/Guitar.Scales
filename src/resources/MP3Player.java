package resources;

import javazoom.jl.player.advanced.jlap;
import javax.swing.JButton;
import java.io.*;

class MP3Player implements Runnable {
    //private  jlap jlPlayer;
	int frame;
    boolean running;
    public MP3Player() {
       // jlPlayer = new jlap();
    }

	public void setFile(String _fileName){media= new File(_fileName);}
	File media = new File("/home/mihel/Documents/Lecciones de Musica/12 blues tracks/12_bar_blues.mp3");  
    public void play(int _frame) {
        // frame = _frame;
        // if (thread==null) 
        // {thread = new Thread(this);
        // thread.start();}
    }
    public int getPosition(){
		return 0;
	}
    public void stop() {
        running = false;
    }

    @Override
    public void run() {
            try {

                //jlPlayer.play("/home/mihel/Documents/Lecciones de Musica/12 blues tracks/12_bar_blues.mp3");
                jlap.playMp3(media, frame, Integer.MAX_VALUE, null);
                
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }
}

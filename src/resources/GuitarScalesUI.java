package resources;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.border.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.prefs.Preferences;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class GuitarScalesUI extends JPanel implements KeyListener,ActionListener,ChangeListener,ItemListener, MetaEventListener{
	static	Cuerda[] strings = new Cuerda[6];
	public String[] ordenDeNotas = {"C ","C#","D ","D#","E ","F ","F#","G ","G#","A ","A#","B "};
	public String[] grades = {"1 ","2-","2","m3","M3","4","4+","5","6","M6","m7","7 "};
	static int tonality=0;
	static Escala scale;
	static Escala[] scaleList = new Escala[13];
	static Escala[] chordList = new Escala[10];
	static Escala[] exeList = new Escala[13]; //ATENCION AQUI SI NO TIENE el numero justo no fucniona
	static Acorde[] fingeredList = new Acorde[3];
	
	static Sequencer mySequencer;
	static Sequence sequence = null;
	boolean flashNotes = false;		// Bool to tell the flash method when there is flashing.
	boolean showNotes = false;
	boolean showGrades = false;


	int flashNote = 0;				// The note beeing flashed
	/**
	 * 
	 */
	int FRET_SIZE = 60;
	int STRING_SEPARATION = 30;
	int X_OFFSET = 50;
	int Y_OFFSET = 50;
	int CIRCLE_SIZE = FRET_SIZE/4;
	int SIXTEENTH_NOTE = 6;
	int QUARTER_NOTE = 12;
	int HALF_NOTE = 24;
	int WHOLE_NOTE = 48;
	private static final long serialVersionUID = 1L;
	private static final int VELOCITY = 80;
	private Preferences prefs = Preferences.userRoot();							// Stores preferences
	Properties prop = new Properties();

	JButton btnIncreaseTonality = new JButton("+");
	JButton btnDecreaseTonality = new JButton("-");
	JButton btnPlayScale = new JButton("Play Scale");
	JButton btnPlayExe = new JButton("Play Exersize");
	JCheckBox checkLoop = new JCheckBox("Loop");
	JCheckBox checkSwing = new JCheckBox("Swing");
	JCheckBox checkMetronome = new JCheckBox("Metronome");
	JCheckBox checkShowNotes = new JCheckBox("Metronome");
	JRadioButton radioShowNotes = new JRadioButton("Show Notes");
	JRadioButton radioShowGrades = new JRadioButton("Show Scale Grades");
	JRadioButton radioShowNone = new JRadioButton("Show None");
	JCheckBox checkLockMask = new JCheckBox("Lock Mask");
	ButtonGroup buttonGroup = new ButtonGroup();
	JComboBox comboScales = new JComboBox();
	JComboBox comboChords = new JComboBox();
	JComboBox comboExersizes = new JComboBox();
	JComboBox comboFingeredChord = new JComboBox();	
	JComboBox comboInstrument = new JComboBox();	
	JComboBox comboNotes = new JComboBox();	
	JLabel lblTonality = new JLabel();
	JLabel lblScale = new JLabel();
	JLabel lblTempo = new JLabel("Tempo");
	JLabel lblChords = new JLabel("Chords");
	JSlider sliderMaskMin = new JSlider(JSlider.HORIZONTAL, 0, 24, 0);
	JSlider sliderMaskMax = new JSlider(JSlider.HORIZONTAL, 0, 24, 12);
	JSlider sliderMaskOpacity = new JSlider(JSlider.HORIZONTAL, 0, 250, 30);
	JSlider sliderTempo = new JSlider(JSlider.HORIZONTAL, 30, 240, 120);
	MidiChannel[] mChannels;
	Border etched = BorderFactory.createEtchedBorder();
	//this.setBorder(etched);


	int playCount = 0;
	static boolean flagFingered;
	static Track track;




	public GuitarScalesUI() {
		
		// TODO Auto-generated constructor stub
		prefs = Preferences.userNodeForPackage(this.getClass());


		try {
			Synthesizer midiSynth = MidiSystem.getSynthesizer(); 

			midiSynth.open();


			//get and load default instrument and channel lists
			Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
			mChannels = midiSynth.getChannels();
			mChannels[0].programChange(50);
			//midiSynth.loadInstrument(instr[15]);//load an instrument
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//        Pattern pattern = new ChordProgression("I IV V")								En el classpath he agregado el JFugue
		//        .distribute("7%6")															que permite crear musica y ritmos en Java
		//        .allChordsAs("$0 $0 $0 $0 $1 $1 $0 $0 $2 $1 $0 $0")							el problema es que detiene la ejecucion.
		//        .eachChordAs("$0ia100 $1ia80 $2ia80 $3ia80 $4ia100 $3ia80 $2ia80 $1ia80")		pero toca acordes que me puede servir.
		//        .getPattern()
		//        .setInstrument("Acoustic_Bass")
		//        .setTempo(100);
		//        new Player().play(pattern);
		//        Rhythm rhythm = new Rhythm()
		//        .addLayer("O..oO...O..oOO..")
		//        .addLayer("..S...S...S...S.")
		//        .addLayer("````````````````")
		//        .addLayer("...............+");
		//        new Player().play(rhythm.getPattern().repeat(2));
		//        new Player().play(new ChordProgression("I IV vi V").eachChordAs("$!i $!i Ri $!i"), new Rhythm().addLayer("..X...X...X...XO"));
		File sequencerFile = new File ("sequencia.mid)");
		//Sequence sequence = null;
		
		
		this.setLayout(new BorderLayout());
		comboInstrument.addItem("Guitar");
		comboInstrument.addItem("Bass");
		comboInstrument.addItem("Ukelele");
		createScales();
		GuitarScalesUI.scale = scaleList[tonality];
		createStrings();
		
		for (int i=0; i<chordList.length ; i++){
			comboChords.addItem(chordList[i].scaleName);
		}
		
		for (int i=0; i<scaleList.length ; i++){
			comboScales.addItem(scaleList[i].scaleName);
		}
		
		for (int i=0; i<exeList.length ; i++){
			if (exeList[i].scaleName!=null) {
			comboExersizes.addItem(exeList[i].scaleName);
			}
		}
		Finger[] fingers = new Finger[4];
		fingers[0] = new Finger (2,1,1);
		fingers[1] = new Finger (4,2,2);
		fingers[2] = new Finger (5,3,3);
		fingers[3] = new Finger (0,0,0);
		fingeredList[0] = new Acorde("Major", "22122212212221",fingers);
		fingeredList[1] = new Acorde("Menor", "21222122122212");
		fingeredList[2] = new Acorde("Mixolidio", "22122122212212");
		scale.setExeMultiplier(1);
		for (int i=0; i<3 ; i++){
			comboFingeredChord.addItem(fingeredList[i].scaleName);
		}
		
		setUpComponents();
		loadPreferences();
		
		
		// Sequencer###################################  puedo directamente crear la sequencia de notas y tocarlas con midi en background
		try
		{
			sequence = new Sequence(Sequence.PPQ, QUARTER_NOTE);  // el uno es PPQ = pulses per quarter ?

		}												//  
		catch (InvalidMidiDataException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		/* Track objects cannot be created by invoking their constructor
		   directly. Instead, the Sequence object does the job. So we
		   obtain the Track there. This links the Track to the Sequence
		   automatically.
		 */
		track = sequence.createTrack();
		for (int i = 0; i < 40; i++) {
			//		track.add(createNoteOnEvent(60,i*4 , 1 , 9));   // note , tick , duration in ticks , channel

			if (i%4 == 0)	{
				//			track.add(createNoteOnEvent(40,i*4 , 1 , 9));
			}

		}

		/* Now we just save the Sequence to the file we specified.
		   The '0' (second parameter) means saving as SMF type 0.
		   Since we have only one Track, this is actually the only option
		   (type 1 is for multiple tracks).
		 */
		try
		{
			MidiSystem.write(sequence, 0, sequencerFile );   //    just for fun write it to a midi file
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		try {
			Sequencer mySeq = MidiSystem.getSequencer();
			mySeq.setSequence(sequence);
			mySequencer = mySeq;		// Static pointer to be able to change tempo
			mySeq.open();
			//mySeq.setTempoInMPQ(320000);
			mySeq.addMetaEventListener(this);
			//mySeq.start();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//######################################################################
		try {
			Synthesizer synt = MidiSystem.getSynthesizer();
			Instrument[] instruments = synt.getDefaultSoundbank().getInstruments();
			for (int j = 0; j < instruments.length; j++) {

				synt.getChannels()[0].programChange(0,12);
			}

			ShortMessage pcMessage = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 0, 5, 0); // Command, channel, patch, bank
			track.add(new MidiEvent(pcMessage, 0));

			//synt.loadInstrument(instruments[15]);
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	private void setUpComponents() {
		// TODO Auto-generated method stub
		
		Diapason diapason = new Diapason(this);
		//diapason.setSize(800,300);
		diapason.setVisible(true);
		diapason.setBorder(etched);
		addKeyListener(this);

		
		

		sliderMaskMin.setMinorTickSpacing(3);								// SLIDERS
		sliderMaskMin.setMajorTickSpacing(12);
		sliderMaskMin.setPaintTicks(true);
		sliderMaskMin.setPaintLabels(true);
		sliderMaskMin.setLabelTable(sliderMaskMin.createStandardLabels(10));
		sliderMaskMin.setPreferredSize(new Dimension(FRET_SIZE*2,STRING_SEPARATION*2));

		sliderMaskMax.setMinorTickSpacing(3);
		sliderMaskMax.setMajorTickSpacing(12);
		sliderMaskMax.setPaintTicks(true);
		sliderMaskMax.setPaintLabels(true);
		sliderMaskMax.setLabelTable(sliderMaskMax.createStandardLabels(10));
		sliderMaskMax.setPreferredSize(new Dimension(FRET_SIZE*2,STRING_SEPARATION*2));

		sliderMaskOpacity.setMinorTickSpacing(50);
		//sliderMaskOpacity.setMajorTickSpacing(20);
		sliderMaskOpacity.setPaintTicks(true);
		//sliderMaskOpacity.setPaintLabels(true);
		sliderMaskOpacity.setLabelTable(sliderMaskOpacity.createStandardLabels(50));
		sliderMaskOpacity.setPreferredSize(new Dimension(FRET_SIZE*2,STRING_SEPARATION*2));

		sliderTempo.setMinorTickSpacing(5);
		sliderTempo.setMajorTickSpacing(50);
		sliderTempo.setPaintTicks(true);
		sliderTempo.setPaintLabels(true);
		sliderTempo.setLabelTable(sliderMaskOpacity.createStandardLabels(50));
		sliderTempo.setValue(90);

		sliderMaskMax.addChangeListener(this);
		sliderMaskMin.addChangeListener(this);
		sliderMaskOpacity.addChangeListener(this);
		Border blackline = BorderFactory.createLineBorder(Color.lightGray);
		//TitledBorder title = BorderFactory.createTitledBorder(blackline, "Mask Min");
		//title.setTitleJustification(TitledBorder.CENTER);
		//sliderMaskMin.setBorder(title);
		sliderMaskMin.setBorder(BorderFactory.createTitledBorder(blackline,"Mask Min"));
		sliderMaskMax.setBorder(BorderFactory.createTitledBorder(blackline,"Mask Max"));
		sliderMaskOpacity.setBorder(BorderFactory.createTitledBorder(blackline,"Opacity"));

		buttonGroup.add(radioShowGrades);
		buttonGroup.add(radioShowNotes);
		buttonGroup.add(radioShowNone);
		JPanel panelRadio = new JPanel();
		panelRadio.add(radioShowGrades);
		panelRadio.add(radioShowNotes);
		panelRadio.add(radioShowNone);

		

		lblTonality.setFont(new Font("Serif", Font.PLAIN, 20));
		comboScales.setSelectedIndex(tonality);
		btnIncreaseTonality.addActionListener(this);
		btnDecreaseTonality.addActionListener(this);
		btnPlayScale.addActionListener(this);
		btnPlayExe.addActionListener(this);
		
		comboScales.addItemListener(this);
		comboChords.addItemListener(this);
		comboExersizes.addItemListener(this);
		comboFingeredChord.addItemListener(this);
		comboInstrument.addItemListener(this);
		checkLoop.addActionListener(this);
		radioShowGrades.addActionListener(this);
		radioShowNotes.addActionListener(this);
		radioShowNone.addActionListener(this);
		
		


		JPanel panelMask = new JPanel();
		panelMask.setBorder(BorderFactory.createBevelBorder(20));
		panelMask.setLayout(new FlowLayout());
		panelMask.add(sliderMaskMin,BorderLayout.LINE_START);
		panelMask.add(sliderMaskMax,BorderLayout.LINE_END);
		panelMask.add(sliderMaskOpacity,BorderLayout.PAGE_END);
		panelMask.add(checkLockMask);
		JPanel panelPlay = new JPanel();
		//panelPlay.setLayout(new GridLayout(4,1));
		panelPlay.add(btnPlayScale);//,BorderLayout.PAGE_START);
		panelPlay.add(checkLoop);//,BorderLayout.CENTER);
		panelPlay.add(sliderTempo);//,BorderLayout.PAGE_END);
		panelPlay.add(lblTempo);//,BorderLayout.PAGE_END);
		panelPlay.add(checkSwing);//,BorderLayout.PAGE_END);
		panelPlay.add(checkMetronome);//,BorderLayout.PAGE_END);
		panelPlay.add(panelRadio);
		lblTempo.setFont(new Font("Serif", Font.PLAIN, 20));

		//panelPlay.add(lblTempo,BorderLayout.PAGE_END);

		JPanel panelExe = new JPanel();
		panelExe.setBorder(BorderFactory.createBevelBorder(20));
		panelExe.add(btnPlayExe);
		panelExe.add(comboExersizes);


		JPanel panelEast = new JPanel();
		JPanel panelWest = new JPanel();
		JPanel panelNorth = new JPanel();
		JPanel panelSouth = new JPanel();
		JPanel panelCenter = new JPanel();

		panelNorth.setLayout(new BoxLayout(panelNorth , BoxLayout.X_AXIS));
		panelNorth.add(lblTonality);
		panelNorth.add(btnDecreaseTonality);
		panelNorth.add(btnIncreaseTonality);
		panelNorth.add(lblScale);
		panelNorth.add(comboScales);
		panelNorth.add(lblChords);
		panelNorth.add(comboChords);
		panelNorth.add(comboFingeredChord);
		
		this.add(panelNorth,BorderLayout.PAGE_START);
		
		//panelSouth.setLayout((new FlowLayout()));
		panelSouth.add(panelPlay);
		panelSouth.add(panelExe);
		panelNorth.setPreferredSize(new Dimension(1200,30));
		this.add(panelSouth,BorderLayout.PAGE_END);
		panelCenter.add(comboInstrument);
		panelCenter.add(diapason);
		panelCenter.add(panelMask);
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		//panelSouth.setBorder(BorderFactory.createEmptyBorder(0, 0 , 20,  0));
		panelSouth.setPreferredSize(new Dimension(1200,90));

		this.add(panelCenter,BorderLayout.CENTER);
		
	}

	@Override
	public void paintComponent(Graphics g){   // paint de GUITAR SCALES
		super.paintComponents(g);
		lblTonality.setText("Key: "+ordenDeNotas[tonality]);
		//lblScale.setText("Scale: "+GuitarScalesUI.scale.scaleName);
		lblTempo.setText(String.valueOf(sliderTempo.getValue()));
	}



	public void calculate(){
		int strings_num = 6;
		if (comboInstrument.getSelectedIndex()==1){strings_num=4;}
		if (comboInstrument.getSelectedIndex()==2){strings_num=4;}
		for ( int t=0; t<strings_num; t++) {
			//System.out.println(t);

			strings[t].calculate(scale, tonality);
		}
	}

	private  void createScales() {
		// TODO Auto-generated method stub

		Escala.setExeMultiplier(1);		// las escalas no tienen espacios por ahora
		scaleList[0]=new Escala("Major", "22122212212221");
		scaleList[1]=new Escala( "Minor", "21221222122122");
		scaleList[2]=new Escala( "Minor Harmonic", "21221312122131");
		scaleList[3]=new Escala( "Minor Melodic", "21222212122221");
		scaleList[4]=new Escala( "Pentatonic Major", "2232322323");
		scaleList[5]=new Escala( "Pentatonic Minor", "3223232232");
		scaleList[6]=new Escala( "Mixolidian", "22122122212212");
		scaleList[7]=new Escala( "Dorian", "21222122122212");
		scaleList[8]=new Escala( "Diminished", "21212122121212");
		scaleList[9]=new Escala( "Whole Tone", "222222222222");
		scaleList[10]=new Escala( "Phrygian", "12221221222122");
		scaleList[11]=new Escala( "Lydian", "22212212221221");
		scaleList[12]=new Escala( "Blues Pentatonic", "321132321132");

		chordList[0]=new Escala("Major","435435435435435435435435435");
		chordList[1]=new Escala("Minor","345345345345345345345345345345345");
		chordList[2]=new Escala("Seventh","4332433243324332");
		chordList[3]=new Escala("Minor Seventh","343234323432");
		chordList[4]=new Escala("Maj7","434143414341");
		chordList[5]=new Escala("Augmented","444444444");
		chordList[6]=new Escala("Diminished","333333333333");
		chordList[7]=new Escala("Suspended Fourth","525525525");
		chordList[8]=new Escala("Sixth","432343234323");
		chordList[9]=new Escala("Minor Sixth","342334233423");


		Escala.setExeMultiplier(2); // antes de cargar los ejercicios lo pongo en 2 para contar los espacios donde pongo datos
		String line = null;
		try {
			File fileExeNames = new File("src/resources/ExeNames.txt");
			File fileExeData = new File("src/resources/ExeDataSpace.txt");

			BufferedReader bufferedReader = new BufferedReader (new FileReader(fileExeNames));
			BufferedReader bufferedReader1 = new BufferedReader (new FileReader(fileExeData));
			int i=0;
			while((line = bufferedReader.readLine()) != null) {
				exeList[i] = new Escala (line,bufferedReader1.readLine()); 
				System.out.println(exeList[i].scaleName);

				i++;
			} 

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	public void createStrings(){
		if (comboInstrument.getSelectedIndex()==0){ // GUITAR
			strings[0] = new Cuerda(4,"1st");
			strings[1] = new Cuerda(11,"2nd");
			strings[2] = new Cuerda(7,"3rd");
			strings[3] = new Cuerda(2,"4th");
			strings[4] = new Cuerda(9,"5th");
			strings[5] = new Cuerda(4,"6th");	
			for ( int t=0; t<6; t++) {
				strings[t].calculate(scale, tonality);
			}
			Diapason.setNumberOfStrings(6);
		}
		else if (comboInstrument.getSelectedIndex()==1){  // BASS

			strings[0] = new Cuerda(7,"1st");
			strings[1] = new Cuerda(2,"2nd");
			strings[2] = new Cuerda(9,"3rd");
			strings[3] = new Cuerda(4,"4th");	
			for ( int t=0; t<4; t++) {
				strings[t].calculate(scale, tonality);
			}
			Diapason.setNumberOfStrings(4);
		}	
		else if (comboInstrument.getSelectedIndex()==2){   //UKELELE
System.out.println("Ukelele");
			strings[0] = new Cuerda(9,"1st");
			strings[1] = new Cuerda(4,"2nd");
			strings[2] = new Cuerda(0,"3rd");
			strings[3] = new Cuerda(7,"4th");	
			for ( int t=0; t<4; t++) {
				strings[t].calculate(scale, tonality);
			}
			Diapason.setNumberOfStrings(4);
		}
	}

	public void savePreferences(){ //  Preferences are recorded in /home/michel/.java/.userPrefs/resources
		prefs.putInt("MASK_MIN", sliderMaskMin.getValue());
		prefs.putInt("MASK_MAX", sliderMaskMax.getValue());
		prefs.putInt("TEMPO", sliderTempo.getValue());
		prefs.putInt("TONALITY", tonality);
		prefs.putInt("SCALE", comboScales.getSelectedIndex());
		prefs.putInt("CHORD", comboChords.getSelectedIndex());
		prefs.putInt("EXE", comboExersizes.getSelectedIndex());
		prefs.putInt("INSTRUMENT", comboInstrument.getSelectedIndex());
		prefs.putBoolean("LOOP", checkLoop.isSelected());
		prefs.putBoolean("METRONOME", checkMetronome.isSelected());
		prefs.putBoolean("SWING", checkSwing.isSelected());
		prefs.putBoolean("MASKLOCK", checkLockMask.isSelected());
	}

	public void loadPreferences(){



		sliderMaskMax.setValue(prefs.getInt("MASK_MAX", -1));
		sliderMaskMin.setValue(prefs.getInt("MASK_MIN", -1));
		sliderTempo.setValue(prefs.getInt("TEMPO", -1));

			//comboScales.setSelectedIndex(prefs.getInt("SCALE", -1));
		int tempChord = prefs.getInt("CHORD", -1);
		//System.out.println(ordenDeNotas[tempChord] +"___");

		comboChords.setSelectedIndex(tempChord);
		comboExersizes.setSelectedIndex(prefs.getInt("EXE", -1));
		comboInstrument.setSelectedIndex(prefs.getInt("INSTRUMENT", -1));
		tonality = (prefs.getInt("TONALITY", -1));
		checkLoop.setSelected(prefs.getBoolean("LOOP", true));
		checkMetronome.setSelected(prefs.getBoolean("METRONOME", true));
		checkSwing.setSelected(prefs.getBoolean("SWING", true));
		checkLockMask.setSelected(prefs.getBoolean("MASKLOCK", true));
		//System.out.println(prefs);
		calculate();
		repaint();
	}


	private static MidiEvent createNoteOnEvent(int nKey, long lTick, int duration , int channel)    //#####  M I D I     #########//
	{	
		track.add(createNoteEvent(ShortMessage.NOTE_ON,
				nKey,
				0,
				lTick + duration, channel));

		if (channel !=9 ){									// 	// Dont add meta message if is drums

			MetaMessage message = new MetaMessage();			//  Attach a meta message to trigger a MetaEventListener 
			byte [] bytes = new byte[1];								// at every note with msg 10 = flash note on
			byte [] bytes1 = new byte[1];
			try {												// 20 = flash note off
				bytes[0] = 10;
				message.setMessage(nKey, bytes , 1);
				track.add(new MidiEvent(message,lTick));	
				bytes[0] = 20;
				message = new MetaMessage();					// reset message and create a flash note off meta message
				message.setMessage(nKey, bytes, 1);
				track.add(new MidiEvent(message,lTick + duration));	

			} catch (InvalidMidiDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return createNoteEvent(ShortMessage.NOTE_ON,  nKey,   VELOCITY,   lTick , channel);
	}



	private static MidiEvent createNoteEvent(int nCommand, int nKey, int nVelocity,	 long lTick , int channel)
	{
		ShortMessage	message = new ShortMessage();
		try
		{
			message.setMessage(nCommand,
					channel,	// always on channel 1
					nKey,
					nVelocity);
		}
		catch (InvalidMidiDataException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		MidiEvent	event = new MidiEvent(message,
				lTick);
		return event;
	}

	private Track trackAddMetronome ( Track tempTrack , int numOfTicks){
		for (int i = 0; i < numOfTicks; i++) {
			tempTrack.add(createNoteOnEvent( 42 , (long) i*QUARTER_NOTE, 1,9 ) );
			if (i%WHOLE_NOTE == 0 )			tempTrack.add(createNoteOnEvent( 36 , i, 1,9 ) );
			if ((i+HALF_NOTE)%WHOLE_NOTE == 0 )			tempTrack.add(createNoteOnEvent( 38 , i, 1,9 ) );

			if ( i%WHOLE_NOTE == 0 ){
				tempTrack.add(createNoteOnEvent( scale.pattern[0]+48+tonality , (long) i*HALF_NOTE, QUARTER_NOTE,0 ) );		//acordes
				tempTrack.add(createNoteOnEvent( scale.pattern[4]+48+tonality , (long) i*HALF_NOTE, QUARTER_NOTE,0 ) );
				tempTrack.add(createNoteOnEvent( scale.pattern[7]+48+tonality , (long) i*HALF_NOTE, QUARTER_NOTE,0 ) );

			}
		}
		return tempTrack;
	}
	public void keyPressed(KeyEvent key) {
		// TODO Auto-generated method stub
		//this.setLocation(200,3);
		//System.out.println(key.getKeyCode());
		if (key.getKeyCode() == 37){
			FRET_SIZE-=2;
			repaint();
		}
		if (key.getKeyCode() == 39){
			FRET_SIZE+=2;
			repaint();
		}		if (key.getKeyCode() == 38){
			STRING_SEPARATION-=2;
			repaint();
		}		if (key.getKeyCode() == 40){
			STRING_SEPARATION+=2;
			repaint();
		}
	}
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


	public void actionPerformed(ActionEvent e) {
		//System.out.println("action");
		// TODO Auto-generated method stub

		if (e.getSource().equals(btnIncreaseTonality)){
			tonality = (tonality +1) % 12;
			if (!checkLockMask.isSelected()){
				sliderMaskMax.setValue((sliderMaskMax.getValue()+1)%24);
				sliderMaskMin.setValue((sliderMaskMin.getValue()+1)%24);
			}
			savePreferences();
			calculate();
			repaint();
		}

		if (e.getSource().equals(btnDecreaseTonality)){
			System.out.println("btn-");

			tonality--;
			if (tonality<0){tonality=11;}
			if (!checkLockMask.isSelected()){
				sliderMaskMax.setValue((sliderMaskMax.getValue()-1)%24);
				sliderMaskMin.setValue((sliderMaskMin.getValue()-1)%24);
			} 
			calculate();
			savePreferences();

			repaint();
		}


		if (e.getSource().equals(btnPlayScale)) {
			clearTrack();
		
			int i = 0;
			while ( scale.pattern[i]<=12){
				track.add(createNoteOnEvent(60+tonality+scale.pattern[i],i*SIXTEENTH_NOTE , SIXTEENTH_NOTE/2 , 0));   // note , tick , duration in ticks , channel
				System.out.println(60+tonality+scale.pattern[i]);
				i++;
				System.out.println();

			}
			if ( checkMetronome.isSelected() ) {trackAddMetronome(track,8);}

			mySequencer.setTickPosition(0);
			mySequencer.setTempoInBPM(sliderTempo.getValue());
			if (checkLoop.isSelected())mySequencer.setLoopCount(100);
			else mySequencer.setLoopCount(0);
			mySequencer.start();

		}


		if (e.getSource().equals(btnPlayExe)) {
			clearTrack();

			for (int i = 0; i * 2 < exeList[comboExersizes.getSelectedIndex()].literal.length() ; i++) {
				System.out.print(i+" - ");
				String tempChar = Character.toString(exeList[comboExersizes.getSelectedIndex()].literal.charAt(i*2+1));
				int noteTemp = tonality+scale.pattern[Integer.parseInt(Character.toString(exeList[comboExersizes.getSelectedIndex()].literal.charAt(i*2)))-1];
				System.out.println("NoteTemp:"+noteTemp+"  -  TempChar:"+tempChar);
				if (tempChar.equals ("U")) noteTemp+=12;
				if (tempChar.equals ("D")) noteTemp-=12;
				
				if (   (checkSwing.isSelected()) && (i%2!=0) ) {
					track.add(createNoteOnEvent(60+noteTemp,i*SIXTEENTH_NOTE + SIXTEENTH_NOTE/3, SIXTEENTH_NOTE/2 , 0));   // note , tick , duration in ticks , channel

				}
				else {
					track.add(createNoteOnEvent(60+noteTemp,i*SIXTEENTH_NOTE , SIXTEENTH_NOTE/2 , 0));   // note , tick , duration in ticks , channel
				}
			}
			if ( checkMetronome.isSelected() ) {trackAddMetronome(track,16);}

			mySequencer.setTickPosition(0);
			mySequencer.setTempoInBPM(sliderTempo.getValue());
			if (checkLoop.isSelected())mySequencer.setLoopCount(100);
			else mySequencer.setLoopCount(0);
			mySequencer.start();
		}

		if (e.getSource().equals(radioShowGrades)){
			if (radioShowGrades.isSelected()) {showNotes = false;showGrades = true;}
			else showGrades = false;
			repaint();
		}
		if (e.getSource().equals(radioShowNotes)){
			if (radioShowNotes.isSelected()) {showNotes = true;showGrades = false;}
			else showNotes = false;
			repaint();
		}
		if (e.getSource().equals(radioShowNone)){
			showNotes = false;
		   showGrades = false;
		   repaint();
	   }
		if (e.getSource().equals(checkLockMask)){
			savePreferences();
	   repaint();
   }
	   if (e.getSource().equals(comboInstrument)){
		createStrings();
		calculate();
		savePreferences();

		repaint();
	}
	}



	public void stateChanged(ChangeEvent e) {
		if (e.getSource().equals(comboExersizes)){

		}
		if (e.getSource().equals(sliderTempo)) {
			repaint();
			if (mySequencer != null) mySequencer.setTempoInBPM(sliderTempo.getValue());
			lblTempo.setText(String.valueOf(sliderTempo.getValue()));
		}
		if (e.getSource().equals(comboInstrument)){
			createStrings();
			calculate();
			savePreferences();

			repaint();
		}
		repaint();
	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getSource().equals(comboScales)){
			//			e.getActionCommand()
			scale = scaleList[comboScales.getSelectedIndex()];
			calculate();
			savePreferences();

			repaint();
		}
		if (e.getSource().equals(comboChords)){
			scale = chordList[comboChords.getSelectedIndex()];
			calculate();
			savePreferences();

			repaint();
		}
		if (e.getSource().equals(comboInstrument)){
			createStrings();
			calculate();
			savePreferences();

			repaint();
		}
		if (e.getSource().equals(comboFingeredChord)){
			System.out.println("chord");
			scale = fingeredList[comboFingeredChord.getSelectedIndex()];
			scale.printChars();
			calculate();
			savePreferences();
			flagFingered = true;
			repaint();
		}
	}
	public void meta(MetaMessage metaMessage) {								// Events fired by MIDI meta messages.
		// TODO Auto-generated method stub
		byte[] bytes = metaMessage.getData();
		if (bytes[0] == 10 ) {
			flashNotes = true;
			flashNote = ( metaMessage.getType()-60) % 12 ;
			repaint();
		}
		else{
			flashNotes = false;
			repaint();
		}
		//flashNotes = false;
	}
	public void clearTrack(){
		MidiEvent event = null;
		for (int i = track.size()-1; i != 1; i--) {
			event = track.get(i);
			track.remove(event);
		}
	}
}

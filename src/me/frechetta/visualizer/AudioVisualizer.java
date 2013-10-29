package me.frechetta.visualizer;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import me.frechetta.visualizer.visualizations.Visualization;
import me.frechetta.visualizer.visualizations.bars.Bars_SrcBot_BassLeft;
import me.frechetta.visualizer.visualizations.bars.Bars_SrcBot_BassMid;
import me.frechetta.visualizer.visualizations.grid.Grid_SrcMid_BassMid;

import org.farng.mp3.AbstractMP3Tag;
import org.farng.mp3.MP3File;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.analysis.KissFFT;
import com.badlogic.gdx.audio.io.Mpg123Decoder;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * AudioVisualizer is the main class for the whole system. It facilitates the complete process of the Visualizer.
 * This process is as follows:
 * <ul>
 * <li>Load the mp3 file to visualize and play
 * <li>Go through the song, decoding each frame to raw data samples
 * <li>Write samples to AudioDevice
 * <li>Transform samples data into spectrum data
 * <li>Visualize spectrum data and draw visualization to screen
 * </ul>
 * 
 * @author Eric
 */
public class AudioVisualizer extends Game
{
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 800;
	
	public static final int SAMPLE_SIZE = 2048;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private short[] samples;
	private float[] spectrum;
	
	private KissFFT fft;
	private Visualization visualization;
	private Mpg123Decoder decoder;
	private AudioDevice device;
	
	private Thread playbackThread;
	
	private boolean paused = true;
	private boolean playing = false;
	
	private boolean opening = false;
	
	private boolean closing = false;
	
	private boolean data = false;
	
	JFileChooser fileChooser;
	
	BitmapFont font;
	
	String songName = "";
	String songArtist = "";
	
	int songNameX;
	int songNameY;
	int songArtistX;
	int songArtistY;

	
	@Override
	public void create()
	{
		// libGDX graphics
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		batch = new SpriteBatch();
		
		samples = new short[SAMPLE_SIZE];
		spectrum = new float[SAMPLE_SIZE];
		
		fft = new KissFFT(SAMPLE_SIZE);
		
		// create visualization
		visualization = new Grid_SrcMid_BassMid(batch, spectrum);
		
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("MP3 file", "mp3"));
		
		font = new BitmapFont();
		
		openFile();
	}
	
	
	/**
	 * Opens a dialog to open a file from the file system.
	 */
	public void openFile()
	{
		opening = true;
		
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				int returnVal = fileChooser.showOpenDialog(null);
				
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					
					try
					{
						MP3File song = new MP3File(new File(path));
						
						AbstractMP3Tag tag = song.getID3v2Tag();
						
						/*if (song.hasFilenameTag())
						{
							tag = FilenameTagBuilder.createFilenameTagFromMP3File(song);
						}
						else if (song.hasID3v1Tag())
						{
							tag = song.getID3v1Tag();
						}
						else if (song.hasID3v2Tag())
						{
							tag = song.getID3v2Tag();
						}*/
												
						songName = tag.getSongTitle();
						songArtist = tag.getLeadArtist();
						
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					
					if (songName.equals(""))
					{
						songName = "Unknown title";
					}
					
					if (songArtist.equals(""))
					{
						songArtist = "Unknown artist";
					}
					
					songName = "Title: " + songName;
					songArtist = "Artist: " + songArtist;
					
					songNameX = WIDTH - 20 - (int)font.getBounds(songName).width;
					songNameY = HEIGHT - 20 - (int)font.getBounds(songName).height;
					songArtistX = WIDTH - 20 - (int)font.getBounds(songArtist).width;
					songArtistY = songNameY - 20 - (int)font.getBounds(songArtist).height;
					
					play(path);
				}
				
				opening = false;
			}
		}).start();
	}
	
	/**
	 * Creates and starts a thread for playback for the song specified by path. 
	 * The playback thread manages decoding, writing to the audio device, and transforming 
	 * with Fast Fourier Transform (FFT).
	 * 
	 * @param path
	 */
	public void play(String path)
	{
		paused = true;
		playing = false;
		
		if (playbackThread != null) while (playbackThread.isAlive()) {}
		
		FileHandle file = new FileHandle(path);
		
		// declaring objects
		decoder = new Mpg123Decoder(file);
		device = Gdx.audio.newAudioDevice(decoder.getRate(), decoder.getChannels() == 1 ? true : false);

		// start a thread for playback
		playbackThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				int readSamples = 0;

				// read until we reach the end of the file
				while (playing)
				{
					if (!paused)
					{
						if ((readSamples = decoder.readSamples(samples, 0, samples.length)) > 0)
						{
							// get audio spectrum
							fft.spectrum(samples, spectrum);
							
							// write the samples to the AudioDevice
							device.writeSamples(samples, 0, readSamples);
						}
						else
						{
							playing = false;
						}
					}
				}
				
				visualization.clear();
			}
		});
		
		paused = false;
		playing = true;
		playbackThread.setDaemon(true);
		playbackThread.start();
	}

	
	@Override
	public void render()
	{
		// clear screen each frame
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// update camera
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();	// begin draw		
		visualization.visualize();	// VISUALIZE!!!
		font.draw(batch, songName, songNameX, songNameY);
		font.draw(batch, songArtist, songArtistX, songArtistY);
		
		if (data)
		{
			visualization.drawData(font);
		}
		
		batch.end();	// end draw
		
		pollInput();
	}
	
	
	/**
	 * Detects hotkey presses and performs the appropriate actions.
	 */
	public void pollInput()
	{
		if (Gdx.input.isKeyPressed(Keys.O) && !opening)
		{
			openFile();
		}
		else if (Gdx.input.isKeyPressed(Keys.P) && !paused)
		{
			paused = true;
		}
		else if (Gdx.input.isKeyPressed(Keys.R) && paused)
		{
			paused = false;
		}
		else if (Gdx.input.isKeyPressed(Keys.ESCAPE) && !closing)
		{
			closing = true;
			
			dispose();
		}
		else if (Gdx.input.isKeyPressed(Keys.D) && !data)
		{
			data = true;
		}
		else if (Gdx.input.isKeyPressed(Keys.F) && data)
		{
			data = false;
		}
	}

	
	@Override
	public void dispose()
	{
		// synchronize with the thread
		paused = true;
		playing = false;
		
		if (playbackThread != null) while (playbackThread.isAlive()) {}
		
		// exit program
		Gdx.app.exit();
	}
	
	
	
	public static void main(String[] args)
	{
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "AudioVisualizer";
		cfg.useGL20 = false;
		cfg.width = WIDTH;
		cfg.height = HEIGHT;
		
		new LwjglApplication(new AudioVisualizer(), cfg);
	}
}
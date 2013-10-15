package me.frechetta.visualizer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import me.frechetta.visualizer.visualizations.Visualization;
import me.frechetta.visualizer.visualizations.bars.Bars_BassMid;

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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AudioVisualizer extends Game
{
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 600;
	
	public static final int FRAME_LENGTH = 2048;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private short[] samples;
	private float[] spectrum;
	
	private KissFFT fft;
	private Visualization visualization;
	private Mpg123Decoder decoder;
	private AudioDevice device;
	
	private Thread playbackThread;
	
	private boolean playing = false;
	private boolean paused = false;
	
	private boolean opening = false;
	
	private boolean closing = false;

	
	@Override
	public void create()
	{
		// libGDX graphics
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		batch = new SpriteBatch();
		
		samples = new short[FRAME_LENGTH];
		spectrum = new float[FRAME_LENGTH];
		
		fft = new KissFFT(FRAME_LENGTH);
		
		// create visualization
		visualization = new Bars_BassMid(batch, spectrum);
		
		openFile();
	}
	
	
	public void openFile()
	{
		opening = true;
		
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("MP3 file", "mp3"));
				int returnVal = fileChooser.showOpenDialog(null);
				
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					play(path);
				}
				
				opening = false;
			}
		}).start();
	}
	
	public void play(String path)
	{
		playing = false;
		paused = true;
		
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
		
		playing = true;
		paused = false;
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

		batch.end();	// end draw
		
		pollInput();
	}
	
	
	public void pollInput()
	{
		if (!opening && Gdx.input.isKeyPressed(Keys.O))
		{
			openFile();
		}
		
		if (!paused && Gdx.input.isKeyPressed(Keys.P))
		{
			paused = true;
		}
		else if (paused && Gdx.input.isKeyPressed(Keys.R))
		{
			paused = false;
		}
		
		if (!closing && Gdx.input.isKeyPressed(Keys.ESCAPE))
		{
			closing = true;
			
			dispose();
		}
	}

	
	@Override
	public void dispose()
	{
		// synchronize with the thread
		playing = false;
		paused = true;
		
		// dispose of devices
		device.dispose();
		decoder.dispose();
		batch.dispose();
		fft.dispose();
		
		// exit cleanly
		System.exit(0);
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
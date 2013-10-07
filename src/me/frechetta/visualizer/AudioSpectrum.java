package me.frechetta.visualizer;

import me.frechetta.visualizer.visualizations.*;
import me.frechetta.visualizer.visualizations.bars.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.analysis.KissFFT;
import com.badlogic.gdx.audio.io.Mpg123Decoder;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AudioSpectrum extends Game
{
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 600;

	public static String FILE = "res/the-island-part-1.mp3";
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private KissFFT fft;
	private Mpg123Decoder decoder;
	private AudioDevice device;
	private Visualization visualization;
	
	private short[] samples = new short[2048];
	private float[] spectrum = new float[2048];
	
	private boolean playing = false;

	
	@Override
	public void create()
	{
		// libGDX graphics
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		batch = new SpriteBatch();

		FileHandle file = new FileHandle(FILE);
		
		// declaring objects
		fft = new KissFFT(2048);
		decoder = new Mpg123Decoder(file);
		device = Gdx.audio.newAudioDevice(decoder.getRate(), decoder.getChannels() == 1 ? true : false);
		
		// create visualization
		visualization = new Bars_BassMid(batch, spectrum);

		// start a thread for playback
		Thread playbackThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				int readSamples = 0;

				// read until we reach the end of the file
				while (playing && (readSamples = decoder.readSamples(samples, 0, samples.length)) > 0)
				{
					// get audio spectrum
					fft.spectrum(samples, spectrum);
					
					// write the samples to the AudioDevice
					device.writeSamples(samples, 0, readSamples);
				}
			}
		});
		
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

		batch.end();	// end draw
	}

	
	@Override
	public void dispose()
	{
		// synchronize with the thread
		playing = false;
		
		// dispose of devices
		device.dispose();
		decoder.dispose();
		batch.dispose();
		fft.dispose();
		
		// exit cleanly
		System.exit(0);
	}
}
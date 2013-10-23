package me.frechetta.visualizer.visualizations;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Visualization represents how the spectrum data of a song is visualized.
 * 
 * @author Eric
 */
public abstract class Visualization
{
	protected SpriteBatch batch;
	protected float[] spectrum;
	
	
	/**
	 * Constructor
	 * 
	 * @param _batch
	 * @param _spectrum
	 */
	public Visualization(SpriteBatch _batch, float[] _spectrum)
	{
		batch = _batch;
		spectrum = _spectrum;
	}
	
	
	/**
	 * Visualizes spectrum data and draws it to the screen.
	 */
	public abstract void visualize();
	
	
	/**
	 * Sets visualization data to 0.
	 */
	public void clear()
	{
		for (int i = 0; i < spectrum.length; i++)
		{
			spectrum[i] = 0;
		}
	}
}

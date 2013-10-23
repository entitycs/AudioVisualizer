package me.frechetta.visualizer.visualizations.bars;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Bars_SrcBot_BassLeft is a type of Bars visualization 
 * with the source of the bars at the bottom of the screen 
 * and the bass on the left.
 * 
 * @author Eric
 */
public class Bars_SrcBot_BassLeft extends Bars
{
	/**
	 * Constructor
	 * 
	 * @param batch
	 * @param spectrum
	 */
	public Bars_SrcBot_BassLeft(SpriteBatch batch, float[] spectrum)
	{
		super(batch, spectrum);
	}
	
	
	@Override
	public void visualize()
	{
		for (int i = 0; i < NUM_BARS; i++)
		{
			int barNum = i;
			
			drawBar(i, barNum);
		}
	}
}

package me.frechetta.visualizer.visualizations.bars;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Bars_SrcBot_BassMid is a type of Bars visualization 
 * with the source of the bars at the bottom of the screen 
 * and the bass in the middle.
 * 
 * @author Eric
 */
public class Bars_SrcBot_BassMid extends Bars
{
	/**
	 * Constructor
	 * 
	 * @param batch
	 * @param spectrum
	 */
	public Bars_SrcBot_BassMid(SpriteBatch batch, float[] spectrum)
	{
		super(batch, spectrum);
	}
	
	
	@Override
	public void visualize()
	{
		for (int i = 0; i < NUM_BARS; i++)
		{
			int barNum = 0;
			
			if (i < NUM_BARS / 2)
			{
				barNum = NUM_BARS / 2 - i;
			}
			else
			{
				barNum = i - NUM_BARS / 2;
			}

			
			drawBar(i, barNum);
		}
	}
}

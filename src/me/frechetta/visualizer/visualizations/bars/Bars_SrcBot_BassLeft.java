package me.frechetta.visualizer.visualizations.bars;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
		float sum = 0;
		
		for (int i = 0; i < NUM_BARS; i++)
		{
			int barNum = i;
			
			sum += avg(barNum, numSamplesPerBar);
			
			if (i % (4 - 1) == 0)
			{
				displayData[i / 4] = (int)(sum / 4);
				sum = 0;
			}
			
			drawBar(i, barNum);
		}
	}
	
	
	public void drawData(BitmapFont font)
	{
		for (int i = 0; i < displayData.length; i++)
		{
			String data = Integer.toString(displayData[i]);
			int x = (int)(barWidth * 4 * i + barWidth * 4 / 2 - font.getBounds(data).width / 2);
			
			font.draw(batch, data, x, 16);
		}
	}
}

package me.frechetta.visualizer.visualizations.bars;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bars_BassMid extends Bars
{
	public Bars_BassMid(SpriteBatch batch, float[] spectrum)
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

			
			draw(i, barNum);
		}
	}
}

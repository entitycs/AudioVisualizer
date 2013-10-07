package me.frechetta.visualizer.visualizations.bars;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bars_BassLeft extends Bars
{
	public Bars_BassLeft(SpriteBatch batch, float[] spectrum)
	{
		super(batch, spectrum);
	}
	
	
	@Override
	public void visualize()
	{
		for (int i = 0; i < NUM_BARS; i++)
		{
			int barNum = i;
			
			draw(i, barNum);
		}
	}
}

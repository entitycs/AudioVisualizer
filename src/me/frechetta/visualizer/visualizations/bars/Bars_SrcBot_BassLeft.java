package me.frechetta.visualizer.visualizations.bars;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bars_SrcBot_BassLeft extends Bars
{
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

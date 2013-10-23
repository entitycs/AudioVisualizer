package me.frechetta.visualizer.visualizations.grid;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.frechetta.visualizer.AudioVisualizer;

/**
 * Grid_SrcMid_BassMid is a type of Grid visualization 
 * with the source of the bars in the middle of the screen 
 * and the bass in the middle.
 * 
 * @author Eric
 */
public class Grid_SrcMid_BassMid extends Grid
{
	/**
	 * Constructor
	 * 
	 * @param batch
	 * @param spectrum
	 */
	public Grid_SrcMid_BassMid(SpriteBatch batch, float[] spectrum)
	{
		super(batch, spectrum);
	}
	
	
	@Override
	public void visualize()
	{
		for (int i = 0; i < numBars; i++)
		{
			for (int j = 0; j < grid[0].length; j++)
			{
				grid[i][j] = 0;
			}
			
			int barNum = getBarNum(i);
			
			int jSpectrumTop = ((int)scale(avg(barNum, numSamplesPerBar)) / cellSize) + (AudioVisualizer.HEIGHT / cellSize / 2 + 1);
			int jSpectrumBot = -((int)scale(avg(barNum, numSamplesPerBar)) / cellSize) + (AudioVisualizer.HEIGHT / cellSize / 2);
			
			if (jSpectrumTop < grid[0].length)
			{
				grid[i][jSpectrumTop] = 2;
			}
			
			if (jSpectrumBot >= 0)
			{
				grid[i][jSpectrumBot] = 2;
			}
			
			while (jSpectrumTop - 5 >= (AudioVisualizer.HEIGHT / cellSize / 2 + 1))
			{
				if (jSpectrumTop - 5 < grid[0].length)
				{
					grid[i][jSpectrumTop - 5] = 1;
				}
				
				jSpectrumTop--;
			}
			
			while (jSpectrumBot + 5 <= (AudioVisualizer.HEIGHT / cellSize / 2))
			{
				if (jSpectrumBot + 5 >= 0)
				{
					grid[i][jSpectrumBot + 5] = 1;
				}
				
				jSpectrumBot++;
			}
		}
		
		draw();
	}
	
	
	/**
	 * Gets the bar number for a specific i.
	 * This is used when the bass of the visualization 
	 * is not located on the left.
	 * 
	 * @param i
	 * @return barNum
	 */
	public int getBarNum(int i)
	{
		int barNum = 0;
		
		if (i < numBars / 2)
		{
			barNum = numBars / 2 - i;
		}
		else
		{
			barNum = i - numBars / 2;
		}
		
		return barNum;
	}
}

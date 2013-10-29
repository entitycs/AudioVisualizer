package me.frechetta.visualizer.visualizations.grid;

import me.frechetta.visualizer.AudioVisualizer;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
	
	
	public void visualize()
	{
		float sum = 0;
		
		for (int i = 0; i < numBars; i++)
		{
			for (int j = 0; j < grid[0].length; j++)
			{
				grid[i][j] = 0;
			}
			
			int barNum = getBarNum(i);
			
			float avg = avg(barNum, numSamplesPerBar);
			
			int jSpectrumTop = ((int)scale(avg) / cellSize) + (AudioVisualizer.HEIGHT / cellSize / 2 + 1);
			int jSpectrumBot = -((int)scale(avg) / cellSize) + (AudioVisualizer.HEIGHT / cellSize / 2);
			
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
			
			
			sum += avg;
			
			if (i % (8 - 1) == 0)
			{
				displayData[i / 8] = (int)(sum / 8);
				sum = 0;
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
	
	
	public void drawData(BitmapFont font)
	{
		for (int i = 0; i < displayData.length; i++)
		{
			String data = Integer.toString(displayData[i]);
			int x = cellSize * 8 * i + cellSize * 8 / 2 - (int)(font.getBounds(data).width / 2);
			
			font.draw(batch, data, x, AudioVisualizer.HEIGHT / 2);
		}
	}
}

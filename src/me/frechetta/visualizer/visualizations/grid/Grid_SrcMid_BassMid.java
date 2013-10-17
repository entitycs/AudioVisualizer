package me.frechetta.visualizer.visualizations.grid;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.frechetta.visualizer.AudioVisualizer;

public class Grid_SrcMid_BassMid extends Grid
{
	
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
			
			int barNum = 0;
			
			if (i < numBars / 2)
			{
				barNum = numBars / 2 - i;
			}
			else
			{
				barNum = i - numBars / 2;
			}
			
			
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
			
			while (jSpectrumTop - 4 >= (AudioVisualizer.HEIGHT / cellSize / 2 + 1))
			{
				if (jSpectrumTop - 4 < grid[0].length)
				{
					grid[i][jSpectrumTop - 4] = 1;
				}
				
				jSpectrumTop--;
			}
			
			while (jSpectrumBot + 4 <= (AudioVisualizer.HEIGHT / cellSize / 2))
			{
				if (jSpectrumBot + 4 >= 0)
				{
					grid[i][jSpectrumBot + 4] = 1;
				}
				
				jSpectrumBot++;
			}
		}
		
		draw();
	}
	
	
	private float scale(float x)
	{
		return x / 256 * AudioVisualizer.HEIGHT * 1.5f;
	}

	
	private float avg(int barNum, int numSamplesPerBar)
	{
		int sum = 0;
		
		for (int i = 0; i < numSamplesPerBar; i++)
		{
			sum += spectrum[barNum + i];
		}

		return (float) (sum / numSamplesPerBar);
	}
}

package me.frechetta.visualizer.visualizations.bars;

import me.frechetta.visualizer.AudioSpectrum;
import me.frechetta.visualizer.visualizations.Visualization;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bars extends Visualization
{
	protected Texture colors;
	
	protected float[] topValues = new float[2048];
	protected float[] maxValues = new float[2048];
	
	protected int NUM_BARS = 128;
	protected int numSamplesPerBar = (spectrum.length / NUM_BARS);
	
	protected float barWidth = ((float) AudioSpectrum.WIDTH / (float) NUM_BARS);
	
	protected float FALLING_SPEED = (1.0f / 3.0f);
	
	
	public Bars(SpriteBatch batch, float[] spectrum)
	{
		super(batch, spectrum);
		
		colors = new Texture(new FileHandle("res/colors-borders.png"));
	}

	
	@Override
	public void visualize()
	{
		
	}
	
	
	public void draw(int i, int barNum)
	{
		if (avg(barNum, numSamplesPerBar) > maxValues[barNum])
		{
			maxValues[barNum] = avg(barNum, numSamplesPerBar);
		}

		if (avg(barNum, numSamplesPerBar) > topValues[barNum])
		{
			topValues[barNum] = avg(barNum, numSamplesPerBar);
		}

		// drawing spectrum (in blue)
		batch.draw(colors, i * barWidth, 0, barWidth, scale(avg(barNum, numSamplesPerBar)), 0, 0, 16, 5, false, false);
		// drawing top values (in red)
		batch.draw(colors, i * barWidth, scale(topValues[barNum]), barWidth, 4, 0, 5, 16, 5, false, false);
		// drawing max values (in yellow)
		batch.draw(colors, i * barWidth, scale(maxValues[barNum]), barWidth, 2, 0, 10, 16, 5, false, false);

		
		topValues[barNum] -= FALLING_SPEED;
	}
	
	private float scale(float x)
	{
		return x / 256 * AudioSpectrum.HEIGHT * 1.5f;
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
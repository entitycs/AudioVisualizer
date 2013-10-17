package me.frechetta.visualizer.visualizations.grid;

import me.frechetta.visualizer.AudioVisualizer;
import me.frechetta.visualizer.visualizations.ColorScheme;
import me.frechetta.visualizer.visualizations.Visualization;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Grid extends Visualization
{
	protected int numBars = 256;
	protected int numSamplesPerBar = (spectrum.length / numBars);
	protected int cellSize = AudioVisualizer.WIDTH / numBars;
	
	byte[][] grid = new byte[numBars][AudioVisualizer.HEIGHT / cellSize];
	
	protected float FALLING_SPEED = (1.0f / 3.0f);
	
	Sprite square;
	
	ColorScheme yellowish = new ColorScheme(1f, 1f, 0f, 1f, .7f, .7f, 0f, .7f);
	ColorScheme blueish = new ColorScheme(.3f, .5f, .9f, 1f, .1f, .3f, .7f, .7f);
	ColorScheme lighterblueish = new ColorScheme(.8f, .8f, 1f, 1f, .4f, .4f, 1f, .7f);
	ColorScheme scheme;
	

	public Grid(SpriteBatch batch, float[] spectrum)
	{
		super(batch, spectrum);
		
		if (numBars == 128)
		{
			square = new Sprite(new Texture(new FileHandle("res/square8.png")));
		}
		else if (numBars == 256)
		{
			square = new Sprite(new Texture(new FileHandle("res/square4.png")));
		}
		
		scheme = lighterblueish;
	}

	
	@Override
	public abstract void visualize();
	
	
	public void draw()
	{
		for (int j = 0; j < grid[0].length; j++)
		{
			for (int i = 0; i < grid.length; i++)
			{
				if (grid[i][j] == 0)
				{
					square.setColor(1f, 1f, 1f, .05f);
				}
				else if (grid[i][j] == 1)
				{
					if (i % 2 == 0)
					{
						square.setColor(scheme.getR(), scheme.getG(), scheme.getB(), scheme.getA());
					}
					else
					{
						square.setColor(scheme.getR2(), scheme.getG2(), scheme.getB2(), scheme.getA());
					}
				}
				else if (grid[i][j] == 2)
				{
					if (i % 2 == 0)
					{
						square.setColor(scheme.getR(), scheme.getG(), scheme.getB(), scheme.getA2());
					}
					else
					{
						square.setColor(scheme.getR2(), scheme.getG2(), scheme.getB2(), scheme.getA2());
					}
				}
				
				square.setX(i * cellSize);
				square.setY(j * cellSize);
				square.draw(batch);
			}
		}
	}
}

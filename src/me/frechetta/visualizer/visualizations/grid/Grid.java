package me.frechetta.visualizer.visualizations.grid;

import java.util.Random;

import me.frechetta.visualizer.AudioVisualizer;
import me.frechetta.visualizer.visualizations.Color;
import me.frechetta.visualizer.visualizations.ColorScheme;
import me.frechetta.visualizer.visualizations.Visualization;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Grid visualizes the spectrum data using a grid.
 * 
 * @author Eric
 */
public abstract class Grid extends Visualization
{
	protected int cellSize = 4;
	protected int numBars = AudioVisualizer.WIDTH / cellSize;
	protected int numSamplesPerBar = (spectrum.length / numBars);
	
	byte[][] grid = new byte[numBars][AudioVisualizer.HEIGHT / cellSize];
	
	Sprite square;
	
	ColorScheme yellowish = new ColorScheme(new Color(1f, 1f, 0f, 1f), new Color(.7f, .7f, 0f, .7f));
	ColorScheme blueish = new ColorScheme(new Color(.3f, .5f, .9f, 1f), new Color(.1f, .3f, .9f, .7f));
	ColorScheme lighterblueish = new ColorScheme(new Color(.8f, .8f, 1f, 1f), new Color(.4f, .4f, 1f, .7f));
	ColorScheme redish = new ColorScheme(new Color(1f, .3f, .3f, 1f), new Color(1f, 0f, 0f, .7f));
	
	ColorScheme[] schemes = {yellowish, blueish, lighterblueish, redish};
	
	ColorScheme scheme;
	ColorScheme newScheme;
	
	Random rand = new Random();
	
	
	/**
	 * Constructor
	 * 
	 * @param batch
	 * @param spectrum
	 */
	public Grid(SpriteBatch batch, float[] spectrum)
	{
		super(batch, spectrum);
		
		square = new Sprite(new Texture(new FileHandle("res/square4.png")));
		
		scheme = lighterblueish;
		//scheme = schemes[rand.nextInt(schemes.length)].copy();
		//newScheme = schemes[rand.nextInt(schemes.length)].copy();
		
		displayData = new int[numBars / 8];
	}
	
	
	/**
	 * Draws the grid to the screen.
	 */
	public void draw()
	{
		Color color1 = scheme.getColor1();
		Color color2 = scheme.getColor2();
		
		for (int j = 0; j < grid[0].length; j++)
		{
			for (int i = 0; i < grid.length; i++)
			{
				if (grid[i][j] == 0)
				{
					square.setColor(1f, 1f, 1f, .1f);
				}
				else if (grid[i][j] == 1)
				{
					if (i % 2 == 0)
					{
						square.setColor(color1.getR(), color1.getG(), color1.getB(), color1.getA());
					}
					else
					{
						square.setColor(color2.getR(), color2.getG(), color2.getB(), color1.getA());
					}
				}
				else if (grid[i][j] == 2)
				{
					if (i % 2 == 0)
					{
						square.setColor(color1.getR(), color1.getG(), color1.getB(), color2.getA());
					}
					else
					{
						square.setColor(color2.getR(), color2.getG(), color2.getB(), color2.getA());
					}
				}
				
				square.setX(i * cellSize);
				square.setY(j * cellSize);
				square.draw(batch);
			}
		}
		
		//transition();
	}
	
	
	/*public void transition()
	{
		float roc = 0.001f;
		
		
		
		
		boolean changed = false;
		
		if (newScheme.getColor1().getR() > scheme.getColor1().getR())
		{
			scheme.getColor1().setR(scheme.getColor1().getR() + roc);
			changed = true;
		}
		else if (newScheme.getColor1().getR() < scheme.getColor1().getR())
		{
			scheme.getColor1().setR(scheme.getColor1().getR() - roc);
			changed = true;
		}
		
		if (newScheme.getColor1().getG() > scheme.getColor1().getG())
		{
			scheme.getColor1().setG(scheme.getColor1().getG() + roc);
			changed = true;
		}
		else if (newScheme.getColor1().getG() < scheme.getColor1().getG())
		{
			scheme.getColor1().setG(scheme.getColor1().getG() - roc);
			changed = true;
		}
		
		if (newScheme.getColor1().getB() > scheme.getColor1().getB())
		{
			scheme.getColor1().setB(scheme.getColor1().getB() + roc);
			changed = true;
		}
		else if (newScheme.getColor1().getB() < scheme.getColor1().getB())
		{
			scheme.getColor1().setB(scheme.getColor1().getB() - roc);
			changed = true;
		}
		
		if (newScheme.getColor1().getA() > scheme.getColor1().getA())
		{
			scheme.getColor1().setA(scheme.getColor1().getA() + roc);
			changed = true;
		}
		else if (newScheme.getColor1().getA() < scheme.getColor1().getA())
		{
			scheme.getColor1().setA(scheme.getColor1().getA() - roc);
			changed = true;
		}
		
		
		if (newScheme.getColor2().getR() > scheme.getColor2().getR())
		{
			scheme.getColor2().setR(scheme.getColor2().getR() + roc);
			changed = true;
		}
		else if (newScheme.getColor2().getR() < scheme.getColor2().getR())
		{
			scheme.getColor2().setR(scheme.getColor2().getR() - roc);
			changed = true;
		}
		
		if (newScheme.getColor2().getG() > scheme.getColor2().getG())
		{
			scheme.getColor2().setG(scheme.getColor2().getG() + roc);
			changed = true;
		}
		else if (newScheme.getColor2().getG() < scheme.getColor2().getG())
		{
			scheme.getColor2().setG(scheme.getColor2().getG() - roc);
			changed = true;
		}
		
		if (newScheme.getColor2().getB() > scheme.getColor2().getB())
		{
			scheme.getColor2().setB(scheme.getColor2().getB() + roc);
			changed = true;
		}
		else if (newScheme.getColor2().getB() < scheme.getColor2().getB())
		{
			scheme.getColor2().setB(scheme.getColor2().getB() - roc);
			changed = true;
		}
		
		if (newScheme.getColor2().getA() > scheme.getColor2().getA())
		{
			scheme.getColor2().setA(scheme.getColor2().getA() + roc);
			changed = true;
		}
		else if (newScheme.getColor2().getA() < scheme.getColor2().getA())
		{
			scheme.getColor2().setA(scheme.getColor2().getA() - roc);
			changed = true;
		}
		
		if (scheme.equals(newScheme))
		{
			//scheme = newScheme.copy();
			//newScheme = schemes[rand.nextInt(schemes.length)];
			//scheme = schemes[rand.nextInt(schemes.length)];
			newScheme = schemes[rand.nextInt(schemes.length)].copy();
			System.out.println(true);
		}
	}*/
	
	
	/**
	 * Scales a bar's value (x) to fit the screen.
	 * 
	 * @param x
	 * @return scaled x
	 */
	protected float scale(float x)
	{
		return x / 256 * AudioVisualizer.HEIGHT * 1.5f;
	}

	/**
	 * Computes the average sample data for a certain bar.
	 * 
	 * @param barNum
	 * @param numSamplesPerBar
	 * @return average sample data for a certain bar
	 */
	protected float avg(int barNum, int numSamplesPerBar)
	{
		int sum = 0;
		
		for (int i = 0; i < numSamplesPerBar; i++)
		{
			sum += spectrum[barNum + i];
		}

		return (float) (sum / numSamplesPerBar);
	}
}

package me.frechetta.visualizer.visualizations;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Visualization
{
	protected SpriteBatch batch;
	protected float[] spectrum;
	
	
	public Visualization(SpriteBatch _batch, float[] _spectrum)
	{
		batch = _batch;
		spectrum = _spectrum;
	}
	
	
	public abstract void visualize();
}

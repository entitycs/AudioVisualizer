package me.frechetta.visualizer;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main
{
	public static void main(String[] args)
	{
		if (args.length == 1)
		{
			AudioSpectrum.FILE = args[0];
		}
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "AudioVisualizer-GDX";
		cfg.useGL20 = false;
		cfg.width = 1024;
		cfg.height = 600;
		
		new LwjglApplication(new AudioSpectrum(), cfg);
	}
}

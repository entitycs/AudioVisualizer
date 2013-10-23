package me.frechetta.visualizer.visualizations;

/**
 * ColorScheme contains two colors that make up a color scheme. 
 * Color schemes are used to customize the look of the visualizer.
 * 
 * @author Eric
 */
public class ColorScheme
{
	private Color color1;
	private Color color2;
	
	
	/**
	 * Constructor
	 * 
	 * @param _color1
	 * @param _color2
	 */
	public ColorScheme(Color _color1, Color _color2)
	{
		color1 = _color1;
		color2 = _color2;
	}
	
	
	/*public ColorScheme copy()
	{
		return new ColorScheme(color1.copy(), color2.copy());
	}*/


	/**
	 * 
	 * @return color1
	 */
	public Color getColor1()
	{
		return color1;
	}
	
	/**
	 * 
	 * @return color2
	 */
	public Color getColor2()
	{
		return color2;
	}


	/**
	 * 
	 * @param _color1
	 */
	public void setColor1(Color _color1)
	{
		color1 = _color1;
	}

	/**
	 * 
	 * @param _color2
	 */
	public void setColor2(Color _color2)
	{
		color2 = _color2;
	}


	/*@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color1 == null) ? 0 : color1.hashCode());
		result = prime * result + ((color2 == null) ? 0 : color2.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColorScheme other = (ColorScheme) obj;
		if (color1 == null)
		{
			if (other.color1 != null)
				return false;
		}
		else if (!color1.equals(other.color1))
			return false;
		if (color2 == null)
		{
			if (other.color2 != null)
				return false;
		}
		else if (!color2.equals(other.color2))
			return false;
		return true;
	}*/
}

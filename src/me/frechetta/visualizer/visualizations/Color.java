package me.frechetta.visualizer.visualizations;

/**
 * Color consists of RGBA values that make up a color.
 * 
 * @author Eric
 */
public class Color
{
	private float r, g, b, a;
	
	
	/**
	 * Constructor
	 * 
	 * @param _r
	 * @param _g
	 * @param _b
	 * @param _a
	 */
	public Color(float _r, float _g, float _b, float _a)
	{
		r = _r;
		g = _g;
		b = _b;
		a = _a;
	}
	
	
	/*public Color copy()
	{
		float newR = r;
		float newG = g;
		float newB = b;
		float newA = a;
		return new Color(newR, newG, newB, newA);
	}*/


	/**
	 * 
	 * @return r
	 */
	public float getR()
	{
		return r;
	}
	
	/**
	 * 
	 * @return g
	 */
	public float getG()
	{
		return g;
	}
	
	/**
	 * 
	 * @return b
	 */
	public float getB()
	{
		return b;
	}
	
	/**
	 * 
	 * @return a
	 */
	public float getA()
	{
		return a;
	}


	/**
	 * 
	 * @param _r
	 */
	public void setR(float _r)
	{
		r = _r;
	}

	/**
	 * 
	 * @param _g
	 */
	public void setG(float _g)
	{
		g = _g;
	}

	/**
	 * 
	 * @param _b
	 */
	public void setB(float _b)
	{
		b = _b;
	}

	/**
	 * 
	 * @param _a
	 */
	public void setA(float _a)
	{
		a = _a;
	}
	
	
	
	/*@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(a);
		result = prime * result + Float.floatToIntBits(b);
		result = prime * result + Float.floatToIntBits(g);
		result = prime * result + Float.floatToIntBits(r);
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
		Color other = (Color) obj;
		if (Float.floatToIntBits(a) != Float.floatToIntBits(other.a))
			return false;
		if (Float.floatToIntBits(b) != Float.floatToIntBits(other.b))
			return false;
		if (Float.floatToIntBits(g) != Float.floatToIntBits(other.g))
			return false;
		if (Float.floatToIntBits(r) != Float.floatToIntBits(other.r))
			return false;
		return true;
	}*/
}

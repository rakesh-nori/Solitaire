
public class Card 
{
	private int rank;
	private String suit;
	private boolean isFaceUp;
	public Card(int r, String s)
	{
		rank = r;
		suit = s;
		isFaceUp = false;
	}
	public int getRank()
	{
		return rank;
	}
	public String getSuit()
	{
		return suit;
	}
	public boolean isRed()
	{
		return suit.equals("d") || suit.equals("h");
	}
	public boolean isFaceUp()
	{
		return isFaceUp;
	}
	public void turnUp()
	{
		isFaceUp = true;
	}
	public void turnDown()
	{
		isFaceUp = false;
	}
	public String getFileName()
	{
		if (!isFaceUp())
		{
			return "cards/back.gif";
		}
		else
		{
			String file = "cards/";
			if (rank >= 2 && rank <= 9)
				file += rank;
			else
			{
				if (rank == 10)
					file += "t";
				else if (rank == 11)
					file += "j";
				else if (rank == 12)
					file += "q";
				else if (rank == 13)
					file += "k";
				else if (rank == 1)
					file += "a";
			}
			file += suit;
			file += ".gif";
			return file;
		}
	}
}

import java.util.*;

public class Solitaire
{
	public static void main(String[] args)
	{
		new Solitaire();
	}

	private Stack<Card> stock;
	private Stack<Card> waste;
	private Stack<Card>[] foundations;
	private Stack<Card>[] piles;
	private SolitaireDisplay display;

	public Solitaire()
	{
		foundations = new Stack[4];
		piles = new Stack[7];

		//INSERT CODE HERE




		display = new SolitaireDisplay(this);
	}

	//returns the card on top of the stock,
	//or null if the stock is empty
	public Card getStockCard()
	{
		throw new RuntimeException("IMPLEMENT ME");
	}

	//returns the card on top of the waste,
	//or null if the waste is empty
	public Card getWasteCard()
	{
		throw new RuntimeException("IMPLEMENT ME");
	}

	//precondition:  0 <= index < 4
	//postcondition: returns the card on top of the given
	//               foundation, or null if the foundation
	//               is empty
	public Card getFoundationCard(int index)
	{
		throw new RuntimeException("IMPLEMENT ME");
	}

	//precondition:  0 <= index < 7
	//postcondition: returns a reference to the given pile
	public Stack<Card> getPile(int index)
	{
		throw new RuntimeException("IMPLEMENT ME");
	}

	//called when the stock is clicked
	public void stockClicked()
	{
		//IMPLEMENT ME
		System.out.println("stock clicked");
	}

	//called when the waste is clicked
	public void wasteClicked()
	{
		//IMPLEMENT ME
		System.out.println("waste clicked");
	}

	//precondition:  0 <= index < 4
	//called when given foundation is clicked
	public void foundationClicked(int index)
	{
		//IMPLEMENT ME
		System.out.println("foundation #" + index + " clicked");
	}

	//precondition:  0 <= index < 7
	//called when given pile is clicked
	public void pileClicked(int index)
	{
		//IMPLEMENT ME
		System.out.println("pile #" + index + " clicked");
	}
}
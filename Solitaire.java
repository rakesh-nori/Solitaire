import java.util.*;

/**
 * Implementation of the Solataire game using stacks.
 * @author Rakesh Nori
 * @version 11.12.2017
 *
 */
public class Solitaire
{
	/**
	 * Instantiates the solitaire game
	 * @param args	arguments given from the input String.
	 */
	public static void main(String[] args)
	{
		new Solitaire();
	}

	private Stack<Card> stock; 
	private Stack<Card> waste; 
	private Stack<Card>[] foundations; //Array of 4 foundations.
	private Stack<Card>[] piles; //Array of 7 piles
	private SolitaireDisplay display; //The display screen.

	/**
	 * Creates a new Solitaire game.
	 */
	public Solitaire()
	{
		foundations = new Stack[4];
		piles = new Stack[7];
		
		for (int i = 0; i < foundations.length; i++)
			foundations[i] = new Stack<Card>();
		for (int i = 0; i < piles.length; i++)
			piles[i] = new Stack<Card>();

		stock = new Stack<Card>();
		createStock();
		waste = new Stack<Card>();
		deal();
		display = new SolitaireDisplay(this);
	}

	/**
	 * Gets the top card of the stock.
	 * @return the top card of the stock, 
	 * 		   or null if it's empty.
	 */
	public Card getStockCard()
	{
		if (!stock.isEmpty())
			return stock.peek();
		return null;
	}
	
	/**
	 * Creates a shuffled stock.
	 * @postcondition there are 52 unique cards in the stock.
	 */
	private void createStock()
	{
		ArrayList<Card> cards = new ArrayList<Card>();
		for (int i = 1; i <= 4; i++)
		{
			String suit = "";
			if (i == 1)
				suit = "c";
			else if (i == 2)
				suit = "d";
			else if (i == 3)
				suit = "h";
			else
				suit = "s";
			for (int j = 1; j <=13; j++)
				cards.add(new Card(j, suit));
		}
		while (cards.size() > 0)
		{
			int randIndex = (int)(Math.random() * cards.size());
			stock.push(cards.remove(randIndex));
		}
	}
	/**
	 * Deals cards from the stock into the pile.
	 * 1 card in left most pile to seven cards in the right most pile.
	 */
	private void deal()
	{
		for (int i = 0; i < piles.length; i++)
		{
			for (int j = 0; j < i+1; j++)
				piles[i].push(stock.pop());
			piles[i].peek().turnUp();
		}
	}
	/**
	 * Draws three cards from the stock and puts it into the waste.
	 * @postcondition Last card drawn from the stock is turned up.
	 */
	private void dealThreeCards()
	{
		for (int i = 0; i < 3; i++)
		{
			if (!stock.isEmpty())
			{
				Card toWaste = stock.pop();
				toWaste.turnUp();
				waste.push(toWaste);
			}
		}
	}
	
	/**
	 * Refills the stock with cards from the waste in reverse order,
	 * and the method turns down all the cards while putting them back.
	 */
	private void resetStock()
	{
		while (!waste.isEmpty())
		{
			Card cycle = waste.pop();
			cycle.turnDown();
			stock.push(cycle);
		}
	}

	/**
	 * Gets the card at the top of the waste
	 * @return the card on top of the waste,
	 * 		   or null if its empty.
	 */
	public Card getWasteCard()
	{
		if (!waste.isEmpty())
			return waste.peek();
		return null;
	}

	/**
	 * Gets top card in given foundation.
	 * @param index of the foundation you want.
	 * @return the top card of the foundation, or null if its empty.
	 */
	public Card getFoundationCard(int index)
	{
		if (!foundations[index].isEmpty())
			return foundations[index].peek();
		return null;
	}

	/**
	 * Gets the pile you want to use.
	 * @param index index of the pile
	 * @return the pile from the array.
	 * @precondition	0 <= index < 7
	 */
	public Stack<Card> getPile(int index)
	{
		return piles[index];
	}

	/**
	 * Runs through the possible actions when the stock is clicked.
	 */
	public void stockClicked()
	{
		if (!display.isWasteSelected() && !display.isPileSelected())
		{
			if (!stock.isEmpty())
				dealThreeCards();
			else
				resetStock();
			System.out.println("stock clicked");
		}
	}

	/**
	 * Runs through possible actions if waste is clicked.
	 */
	public void wasteClicked()
	{
		if (!waste.isEmpty() && !display.isWasteSelected() && !display.isPileSelected())
		{
			display.selectWaste();
			System.out.println("Waste clicked");
		}
		else if (display.isWasteSelected())
			display.unselect();
	}

	/**
	 * Runs through possible actions when foundation is clicked.
	 * @param index of the foundations array.
	 * @precondition 0 <= index < 4
	 */
	public void foundationClicked(int index)
	{
		if (display.isWasteSelected())
		{
			if (canAddToFoundation(waste.peek(), index))
				foundations[index].push(waste.pop());
		}
		else if (display.isPileSelected())
		{
			int i = display.selectedPile();
			if (canAddToFoundation(piles[i].peek(), index))
				foundations[index].push(piles[i].pop());
		}
		display.unselect();
		System.out.println("foundation #" + index + " clicked");
	}
	
	/**
	 * Runs through possible actions when a pile is clicked.
	 * @param index The given pile index of the piles index.
	 * @precondition 0 <= index < 7
	 */
	public void pileClicked(int index)
	{
		if (display.isWasteSelected())
		{
			if (canAddToPile(waste.peek(), index))
				piles[index].push(waste.pop());
			display.unselect();
		}
		else if(display.isPileSelected())
		{
			int ind = display.selectedPile();
			if (ind != index)
			{
				Stack<Card> removed = removeFaceUpCards(ind);
				if (canAddToPile(removed.peek(), index))
				addToPile(removed, index);
				else
					addToPile(removed, ind);
			}
			display.unselect();
		}
		else
		{
			if (!piles[index].peek().isFaceUp())
				piles[index].peek().turnUp();
			display.selectPile(index);
		}
		System.out.println("pile #" + index + " clicked");
	}
	/**
	 * 
	 * @param card Card that you want to add to the pile.
	 * @param index The index of the pile.
	 * @return true if the given card can be legally moved 
	 * 		   to the top of the pile; otherwise, return false.]
	 * @precondition O <= index < 7
	 */
	private boolean canAddToPile(Card card, int index)
	{
		if (piles[index].isEmpty())
			return (card.getRank() == 13);
		Card c = piles[index].peek();
		if (c.isFaceUp())
		{
			int rank = c.getRank() - 1;
			boolean color = c.isRed();
			if (card.getRank() == rank && card.isRed() != color)
				return true;
		}
		return false;
	}
	
	/**
	 * Removes all face-up cards from a pile.
	 * @param index the index of the pile you want to use.
	 * @return the removed stack of cards
	 * @precondition 0 <= index < 7
	 */
	private Stack<Card> removeFaceUpCards(int index)
	{
		Stack<Card> removed = new Stack<Card>();
		while (!piles[index].isEmpty() && piles[index].peek().isFaceUp())
		{
			removed.push(piles[index].pop());
		}
		return removed;
	}
	
	/**
	 * Adds a stack of cards to a pile.
	 * @param cards	the cards to be added
	 * @param index index of the pile you want to use.
	 * @precondition 0 <= index < 7.
	 */
	private void addToPile(Stack<Card> cards, int index)
	{
		while (!cards.isEmpty())
			piles[index].push(cards.pop());
	}
	/**
	 * Checks if a card can be added to the foundation.
	 * @param card	card that wants to be added to the foundation.
	 * @param index index of the foundation pile you're checking.
	 * @return true if the card can be added; otherwise,
	 * 		   false.
	 */
	private boolean canAddToFoundation(Card card, int index)
	{
		if (foundations[index].isEmpty())
			return (card.getRank() == 1);
		else
		{
			Card c = foundations[index].peek();
			int rank = c.getRank() + 1;
			String suit = c.getSuit();
			return (card.getRank() == rank && 
					card.getSuit().equals(suit));
		}
				
	}
	
}
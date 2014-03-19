import java.util.ArrayList;
import java.util.TreeMap;

/*
*	Clayton Salinger Ketner
*	February 11, 2014
*	CSCI 460 - Artificial Intelligence
*/

public class BreadthFirstSearch
{
	public static ArrayList<Node<City>> 
	search(ArrayList<Node<City>> nodeQueue, ArrayList<Node<City>> successors)
	{
		// Sort successors alphabetically by first letter
		Node<City>[] successorsToSort = successors.toArray(new Node[successors.size()]);
		boolean sorted = false;
		while (!sorted)
		{
			sorted = true;
			for (int i = 0; i < successorsToSort.length-1; i++)
			{
				if (successorsToSort[i].getData().name.compareTo(successorsToSort[i+1].getData().name) > 0)
				{
					Node<City> temp = successorsToSort[i];
					successorsToSort[i] = successorsToSort[i+1];
					successorsToSort[i+1] = temp;
					sorted = false;
				}
			}
		}

		for (int i = 0; i < successorsToSort.length; i++)
		{
			nodeQueue.add(successorsToSort[i]); // Add to end of queue in the sorted order
		}

		return nodeQueue;
	}
}
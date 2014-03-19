import java.util.ArrayList;
import java.util.TreeMap;

/*
*	Clayton Salinger Ketner
*	February 11, 2014
*	CSCI 460 - Artificial Intelligence
*/

public class UniformCostSearch
{
	public static ArrayList<Node<City>> 
	search(ArrayList<Node<City>> nodeQueue, ArrayList<Node<City>> successors)
	{
		for (Node<City> successor : successors)
		{
			nodeQueue.add(successor); // Add to queue, to be sorted after
		}

		// Sort the queue by lowest-highest cost using bubble sort
		Node<City>[] nodesToSort = nodeQueue.toArray(new Node[nodeQueue.size()]); // Convert to array
		boolean sorted = false;
		while (!sorted)
		{
			sorted = true;
			for (int i = 0; i < nodesToSort.length-1; i++)
			{
				int thisNodeCost = 0;
				int nextNodeCost = 0;
				Node<City> thisNode = nodesToSort[i];
				Node<City> nextNode = nodesToSort[i+1];

				// Calculate the cost of the ith node by adding up edge costs
				thisNodeCost = getEdgeCost(thisNode);

				// Calculate the cost of the i+1th node by adding up edge costs
				nextNodeCost = getEdgeCost(nextNode);

				if (thisNodeCost > nextNodeCost)
				{
					Node<City> temp = nodesToSort[i];
					nodesToSort[i] = nodesToSort[i+1];
					nodesToSort[i+1] = temp;

					sorted = false;
				}
			}
		}

		// Replace the unsorted queue with the sorted one
		nodeQueue = new ArrayList<Node<City>>();
		for (int i = 0; i < nodesToSort.length; i++)
		{
			nodeQueue.add(nodesToSort[i]);
		}
		
		return nodeQueue;
	}
	
	private static int getEdgeCost(Node<City> node)
	{
		int cost = 0;
		while (node.getParent() != null)
		{
			cost += node.getData().getCostToNeighbor(node.getParent().getData());
			node = node.getParent();
			
		}
		return cost;
	}
}
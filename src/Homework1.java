import java.util.ArrayList;
import java.util.TreeMap;

/*
*	Clayton Salinger Ketner
*	February 11, 2014
*	CSCI 460 - Artificial Intelligence
*/

public class Homework1
{
	public static Node<City> search()
	{
		final City start = CityMap.getCity("Alexandria");
		final City goal = CityMap.getCity("Luxor");
		Node<City> treeRoot = new Node<City>(null, start); // The tree, starts with the root start city
		Node<City> currentNode = null;

		ArrayList<Node<City>> nodeQueue = new ArrayList<Node<City>>(); // Queue of nodes to expand
		nodeQueue.add(treeRoot); // Add start node to queue


		// Form of GENERIC SEARCH ALGORITHM
		System.out.print("Output: ");
		while(true)
		{
			if (nodeQueue.size() == 0) { return null; } // Failure

			currentNode = nodeQueue.remove(0);
			System.out.print(currentNode.getData().name + ", ");
			if (currentNode.getData().equals(goal)) { return currentNode; }

			
			// TESTS - uncomment only 1
			nodeQueue = BreadthFirstSearch.search(nodeQueue, expand(currentNode)); // 1st test
			//nodeQueue = DepthFirstSearch.search(nodeQueue, expand(currentNode)); // 2nd test
			//nodeQueue = UniformCostSearch.search(nodeQueue, expand(currentNode)); // 3rd test
		}
	}


	/** Gets the successors of the given node, removes any cities that are ancestors
	*	to that node, and returns the result **/
	private static ArrayList<Node<City>> expand(Node<City> nodeToExpand)
	{
		TreeMap<City, Integer> cityNeighbors = nodeToExpand.getData().getNeighbors();
		ArrayList<Node<City>> successorNodes = new ArrayList<Node<City>>();

		// Wrap city neighbors into nodes, check if it is an ancestor city (loop check)
		// Then add to the return variable "successorNodes"
		for (City neighbor : cityNeighbors.keySet())
			if (!(nodeToExpand.hasAncestor(neighbor)))
				successorNodes.add(new Node<City>(nodeToExpand, neighbor));

		return successorNodes;
	}


	public static void main(String[] args)
	{
		System.out.println("Clayton Salinger Ketner\n" +
							"February 14, 2014\n" +
							"CSCI 460 - Artificial Intelligence\n" +
							"----------------------------------");
		
		CityMap.initializeMap();

		Node<City> searchResult = search();
		System.out.println();

		if (searchResult != null)
		{
			System.out.println("Search was successful!");
			
			// Go backwards up the tree to find the solution
			ArrayList<City> resultPath = new ArrayList<City>();
			do
			{
				resultPath.add(0, searchResult.getData()); // Add to beginning of arraylist
				searchResult = searchResult.getParent();
			} while (searchResult != null);

			// Now print the result
			System.out.print("Solution: ");
			for (int i = 0; i < resultPath.size()-1; i++)
				System.out.print(resultPath.get(i).name + ", "); // Print the solution, separated by commas
			System.out.println(resultPath.get(resultPath.size()-1).name + "."); // Print the last one with a period at the end

		} else {
			System.out.println("Search was unsuccessful.");
		}
	}
}
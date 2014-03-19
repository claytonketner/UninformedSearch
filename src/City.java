import java.util.TreeMap;

/*
*	Clayton Salinger Ketner
*	February 11, 2014
*	CSCI 460 - Artificial Intelligence
*/

public class City implements Comparable<City>
{
	public final String name;
	private TreeMap<City, Integer> neighbors;
	boolean debug = false;

	public City(String name)
	{
		this.name = name;
		neighbors = new TreeMap<City, Integer>();
	}

	/** Looks up the new neighbor based on the name parameter, and adds it 
	*	as a neighbor to this city, and vice versa. Only adds the neighbor 
	*	if that neighbor doesn't already exist. 
	*	@return True if newNeighbor was added or already existed, false if there was an error. **/
	public boolean addNeighbor(String newNeighborName, int cost)
	{
		City newNeighbor = CityMap.getCity(newNeighborName);
		if (newNeighbor == null)
		{
			System.err.println("Tried to add a neighbor named " + newNeighborName + " to " + this.name 
								+ ", but " + newNeighborName + " doesn't exist in the city map.");
			return false;
		}

		return this.addNeighbor(newNeighbor, cost);
	}

	/** Same as other addNeighbor method but takes a City object instead of the name. **/
	public boolean addNeighbor(City newNeighbor, int cost)
	{
		if (!neighbors.containsKey(newNeighbor))
		{
			// Add it
			neighbors.put(newNeighbor, new Integer(cost));
			newNeighbor.addNeighbor(this, cost); // This will recurse once, doing nothing
			return true;
		} else {
			// Check cost for accuracy
			if (!(neighbors.get(newNeighbor) == cost))
			{
				System.err.println("Tried to add already existing neighbor. Cost check yielded a discrepancy: "
									+ this.name + " to " + newNeighbor.name + " has two costs -- "
									+ "new: " + cost + " and old: " + neighbors.get(newNeighbor) + ".");
				return false;
			}
		}
		return true; // Already existed and cost check passed
	}

	public boolean addNeighbors(String[] neighborNames, int[] costs)
	{
		if (neighborNames.length != costs.length)
		{
			System.err.println("Adding multiple neighbors to " + this.name + " failed because the "
								+ "two array parameters have different sizes.");
			return false;
		}

		for (int i = 0; i < neighborNames.length; i++)
		{
			if (!this.addNeighbor(neighborNames[i], costs[i]))
				return false; // Means this one failed
		}

		return true; // All added successfully
	}

	public TreeMap<City, Integer> getNeighbors()
	{
		return neighbors;
	}

	public int getCostToNeighbor(City city)
	{
		if (debug)
			System.out.println("Getting cost from " + this.name + " to " + city.name);
		if (neighbors.get(city) == null) // No such connected city
			return -1;

		return neighbors.get(city).intValue();
	}

	/** Returns true if names match, regardless of case or surrounding spaces. **/
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) { return false; }
		if (!(obj instanceof City)) { return false; }

		// Return true if the names match, regardless of case and surrounding white space
		if (this.name.equals( ((City)obj).name )) 
		{
			return true; 
		}

		return false;
	}

	@Override
	public int compareTo(City arg0)
	{
		return this.name.compareTo(arg0.name);
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
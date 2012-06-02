package dmk.knapsack;

public interface Packable {
	public float getWorthRatio();
	public int getWeight();
	public Packable setWeight(int weight);
	public int getWorth();
	public Packable setWorth(int worth);
	public String getName();
	public Packable setName(String name);
}
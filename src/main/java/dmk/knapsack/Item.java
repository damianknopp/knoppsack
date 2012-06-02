package dmk.knapsack;

public class Item implements Packable{
	protected float worthRatio;
	protected int weight;
	protected int worth;
	protected String name;
	protected boolean initalized;
	
	public Item(){
		super();
		initalized = false;
	}
	
	public final float getWorthRatio() {
		if(!initalized){
			this.worthRatio = this.getWeight() / this.getWorth();
			this.initalized = true;
		}
		return worthRatio;
	}
	public final int getWeight() {
		return weight;
	}
	public final Item setWeight(int weight) {
		this.weight = weight;
		return this;
	}
	public final int getWorth() {
		return worth;
	}
	public final Item setWorth(int worth) {
		this.worth = worth;
		return this;
	}
	public final String getName() {
		return name;
	}
	public final Item setName(String name) {
		this.name = name;
		return this;
	}
}
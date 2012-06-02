package dmk.knapsack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Uses a greedy approximation approach to solving the knapsack problem.
 * Notice, an approximation may not always give the optimal answer.
 * Use this when other approaches are not available (i.e. limitations in processing, memory, etc...)
 */
public class GreedyApproximation {
	protected List<Packable> items;
	protected List<Packable> optList;
	protected boolean alreadySorted = false;
	
	public GreedyApproximation(final List<Packable> items){
		super();
		this.items = items;
	}
	
	public static void main(final String args[]){		
		final int[] w = {  3,  2,  4,  1,  3,   1 };
		final int[] v = { 55, 80, 60, 45, 105, 50 };
		final List<Packable> items = new ArrayList<Packable>();
		for(int i = 0; i < w.length; i++){
			Item item = new Item();
			item.setName("item" + i);
			item.setWeight(w[i]);
			item.setWorth(v[i]);
			items.add(item);
		}
		final GreedyApproximation greedyKp = new GreedyApproximation(items);
		final int maxSize = 10;
		final int optimal = greedyKp.optimal(maxSize);
		System.out.println("------Knapsack Optimal------");
		System.out.println("optimal value for max capacity of " + maxSize + " is " + optimal);
		System.out.println("----Opt List------");
		for(final Packable packable:greedyKp.optList()){
			System.out.println("\t" + packable.getName() + " value="  + packable.getWorth() + " weight=" + packable.getWeight());
		}
		
	}
	
	public int optimal(final int maxSize){
		if(!alreadySorted){
			Collections.sort(this.items, new CompareByWorthRatio());
			this.items = Collections.unmodifiableList(this.items);
			this.alreadySorted = true;
		}
		return this.computeOpt(maxSize);
	}
	
	protected int computeOpt(final int capacity){
		int curSize = 0;
		int curValue = 0;
		this.optList = new ArrayList<Packable>(this.items.size());
		for(final Packable packable: this.items){
			if(!(packable.getWeight() + curSize > capacity)){
				optList.add(packable);
				curSize += packable.getWeight();
				curValue += packable.getWorth();
			}
		}
		return curValue;		
	}

	public List<Packable> optList(){
		return this.optList;
	}
	
	protected class CompareByWorthRatio implements Comparator<Packable>{

		public int compare(Packable o1, Packable o2) {
			float worthRatio1 = ((Packable)o1).getWorthRatio();
			float worthRatio2 = ((Packable)o2).getWorthRatio();
			if(worthRatio1 > worthRatio2){
				return 1;
			}else if(worthRatio1 < worthRatio2){
				return -1;
			}else{
				return 0;
			}
		}
	}
}
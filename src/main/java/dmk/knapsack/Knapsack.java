package dmk.knapsack;

import java.util.Stack;

/**
 * 01 Knapsack solution implemented with dynamic programming.
 * Generate the optimal value for items with weights and a max constraint.
 */
public class Knapsack {
	protected int[] weights;
	protected int[] values;
	protected int capacity;
	protected int[][] solTable;
	
	public Knapsack(final int[] weights, final int[] values, final int capacity){
		super();
		ensureSaneArrays(weights, values);
		ensureSaneCapacity(capacity);
		this.weights = weights;
		this.values = values;
		this.capacity = capacity;
	}
	
	public static void main(final String args[]){		
		final int[] w = {  3,  2,  4,  1,  3,   1 };
		final int[] v = { 55, 80, 60, 45, 105, 50 };
		final int maxSize = 10;
		final Knapsack kp = new Knapsack(w, v, maxSize);
		kp.genSolutionTable();
		final int optimal = kp.optimial(maxSize);
		System.out.println("------Knapsack Optimal------");
		System.out.println("Weights");
		kp.dumpArray(w);
		System.out.println("Values");
		kp.dumpArray(v);
		System.out.println("opt=" + optimal);
		System.out.println();
		kp.dumpSolutionTable();
		System.out.println();
		Stack<Integer> optList = kp.optList();
		System.out.println(optList);
		while(!optList.isEmpty()){
			System.out.println(optList.pop());
		}
	}

	public int optimial(int max){
		if(this.solTable == null){
			System.out.println("generating solution table, this could take awhile...");
			genSolutionTable();
		}
		if(max < 0){
			throw new IllegalArgumentException("The knapsack cannot have a negative capacity!");
		}
		if(max > this.capacity){
			throw new IllegalArgumentException("The knapsack with a capacity of " + this.capacity + " cannot support a greater capacity of " + max);
		}
		return this.solTable[this.weights.length][max];
		
	}
	
	public int optimalForMaxCapacity(){
		//last element is the solution
		return this.solTable[this.weights.length][this.capacity];
	}
	
	/**
	 * calc optimal value based on this knapsack
	 * @return
	 */
	public void genSolutionTable(){
		if(this.solTable == null){
			initSolTable();
		}
		
		for(int i = 1; i < this.weights.length + 1; i++){
			//init first column in ever row to 0
			this.solTable[i][0] = 0;
			for(int j = 0; j < this.capacity + 1; j++){
				int currentWeight = j;
//				System.out.println("value=" + values[i-1] + " weight=" + weights[i-1] + " j=" + j);
				if(this.weights[i-1] <= currentWeight){
					//check to see if new item should be in optimal solution
					int lastOptPosition = currentWeight - this.weights[i-1];
					int tempOpt = this.values[i-1] + this.solTable[i-1][lastOptPosition];
					int curOpt = this.solTable[i-1][j];
//					System.out.println("checking i-1=" + (i-1) + ", currentWeight=" + currentWeight);
//					System.out.println("new opt=" + tempOpt + " and oldOpt=" + curOpt);
					if(tempOpt > curOpt){
						//a better opt so set it
						this.solTable[i][j] = tempOpt;
					}else{
						//nope not better so use current opt
						this.solTable[i][j] = curOpt;
					}
				}else{
					//item doesnt fit, so use the previous optimal value
					this.solTable[i][j] = this.solTable[i-1][j];
				}
			}
		}
	}
	
	public Stack<Integer> optList(){
//		final List<Integer> list = new ArrayList<Integer>();
		final Stack<Integer> stack = new Stack<Integer>();
		final int row = this.weights.length;
		int curCol = this.capacity;
		int tempCol = curCol;
		for(int curRow = row; curRow >= 1; curRow--){
//			int curValue = this.solTable[curRow][curCol];
			int prevValueExclude = this.solTable[curRow - 1][curCol];
//			System.out.println("curCol=" + curCol + " curRow=" + curRow);
			tempCol = curCol;
			curCol = curCol - this.weights[curRow - 1];
			int prevValueInclude = this.solTable[curRow - 1][curCol];
			prevValueInclude = prevValueInclude + this.values[curRow - 1];
//			System.out.println("compare curValue=" + curValue + " prevValueInclude=" + prevValueInclude + " prevExcludeValue=" + prevValueExclude);
			if(prevValueExclude >= prevValueInclude){
				curCol = tempCol;
			}else{
//				System.out.println("adding " + curRow);
				stack.push(curRow);
			}
		}
		return stack;
	}
	
	protected void dumpSolutionTable(){
		if(this.solTable == null){
			System.out.println("Solution table is empty.");
			return;
		}
		
		for(int i = 0; i < this.capacity + 1; i++){
			System.out.print("\t" + i + "\t");
		}
		System.out.println();
		for(int i = 0; i < this.capacity + 1; i++){
			System.out.print("\t" + "-" + "\t");
		}

		System.out.println("");
		for(int i = 0; i < this.weights.length + 1; i++){
			if(i != 0){
				System.out.print("[" + this.weights[i-1] + "," + this.values[i-1] + "]"); 
			}
			for(int j = 0; j < this.capacity + 1; j++){
				System.out.print("\t" + this.solTable[i][j] + "\t");
			}
			System.out.println();
		}
	}
	
	protected void dumpArray(final int[] array){
		if(array == null) return;
		for(int i = 0; i < array.length; i++){
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}
	
	protected int[][] initSolTable(){
		final int[][] table = new int[this.weights.length + 1][this.capacity + 1];
		//init first row with 0's
		for(int i = 0; i < table[0].length; i++){
			table[0][i] = 0;
		}
		this.solTable = table;
		return this.solTable;
	}

	protected void ensureSaneArrays(final int[] w, final int[] v){
		if(w == null || v == null){
			throw new IllegalArgumentException("Weights and Values arrays should not be null!");
		}
		
		if(w.length != v.length){
			throw new IllegalArgumentException("Weights and Values arrays should be of equal length "
					+ " weights.len=" + w.length + " values.len=" + v.length);
		}
	}
	
	protected void ensureSaneCapacity(final int max){
		if(max < 1){
			throw new IllegalArgumentException("Capacity should be a value > 1, found " + max);
		}
	}
}
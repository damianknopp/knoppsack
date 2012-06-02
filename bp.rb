module Optimize
class Knapsack
	# given an array of weights
	# and an array of corresponding values
	# find the optimal sum of values given their constraints and total constraint j
	def optimize(w, v, i, j, path=[])
	    if (i == 0 || j == 0)
		 return 0
	    end
	    if (w[i-1] > j)
		  return optimize(w, v, i-1, j)
	    end
	    if (w[i-1] <= j)
		 val1 = optimize(w,v, i-1, j, path)
		 val2 = v[i-1] + optimize(w,v, i-1, j - w[i-1], path)
		 opt = max(val1, val2)
		 print "found opt=#{opt}\n"
		 path.push opt
		 return opt
	    end
	end

	def max(a,b)
	    if(a >= b)
		return a
	    else
		return b
	    end
	end
end
end

w = [ 3, 2, 4, 1, 3, 1 ]
v = [ 55, 80, 60, 45, 105, 50 ]
path = []
i = w.size
j = 10

knapsack = Optimize::Knapsack.new
result = knapsack.optimize(w,v,i,j,path)
print "result = #{result}"
print "path = #{path}"
result

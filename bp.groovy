def knapsackOptimal(w, v, i, j){
    if (i == 0 || j == 0) return 0
    if (w[i-1] > j)  return knapsackOptimal(w, v, i-1, j)
    if (w[i-1] <= j) return max(knapsackOptimal(w,v, i-1, j), v[i-1] + knapsackOptimal(w,v, i-1, j - w[i-1]))
}

def max(a,b){
    if(a >= b){
        return a
    }else{
        return b
    }
}

def w = [ 3, 2, 4, 1, 3, 1 ]
def v = [ 55, 80, 60, 45, 105, 50 ]
def i = w.size
def j = 10

knapsackOptimal(w,v,i,j)


def weights = new ArrayList(1024)
def values = new ArrayList(1024)

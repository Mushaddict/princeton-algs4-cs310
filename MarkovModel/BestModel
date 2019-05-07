import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.MaxPQ;
import java.util.HashSet;
import java.lang.Math;

public class BestModel{
    private MarkovModel m1;
    private MarkovModel m2; 
    private int k;
    
    public BestModel(int order, String s1, String s2) {
		k = order; 
		m1 = new MarkovModel(k, s1); 
		m2 = new MarkovModel(k, s2);
    }		

    // A node to a PQ
    private class LikeDiff implements Comparable<LikeDiff> {
		private String str; // The substring
		private double loglike1;
		private double loglike2; 
		private double diff;
	
		LikeDiff(String s, double l1, double l2) {
	   		str = s;
	    	loglike1 = l1;
	    	loglike2 = l2; 
	    	diff = Math.abs(l1 - l2); 
		}

		public int compareTo(LikeDiff ld) {
			if(diff - ld.diff > 0) 
				return 1;
				else if (diff - ld.diff < 0) 
					return -1;
				else return 0;
		}
		public boolean equals(Object obj) 
		{
			if(obj.getClass() != getClass()) return false; 
			LikeDiff ld = (LikeDiff)obj;
	    	return (diff == ld.diff); 
		}
		public String toString() 
		{
			double signdiff = loglike1-loglike2;
			String s = String.format("%s %4.4f %4.4f %4.4f", str, loglike1 ,loglike2, signdiff);
			return s;
		}
    }

       public void getTopTen(MaxPQ<LikeDiff> ld){
	       for(int i = 0;i<10;i++) {
		       if(ld.isEmpty()) return;
		       LikeDiff d = ld.delMax();
		       System.out.println(d);
	       }
       }

    public void testLikelihood(String name, String s) {
		double likelihood1 = 0.0;
		double likelihood2 = 0.0; 
		// To store top 10
		MaxPQ<LikeDiff> diffs = new MaxPQ<LikeDiff>();
		int len = s.length(); 
		// build "circular" string
		String prefix = s.substring(0,k);
		StringBuilder b = new StringBuilder(s);
		b.append(prefix);
		for(int i = 0; i < len; i++) {
	   		String sub=b.substring(i,i+k+1);
	   		double loglap1 = Math.log(m1.laplace(sub));
			double loglap2 = Math.log(m2.laplace(sub)); 
	   		likelihood1 += loglap1;
	   		likelihood2 += loglap2; 
			LikeDiff ld = new LikeDiff(sub, loglap1, loglap2);   
			diffs.insert(ld);
		}	
		double averageLike1 = likelihood1/len;
		double averageLike2 = likelihood2/len;
		double diff = averageLike1-averageLike2;
		System.out.printf("%s %4.4f %4.4f %4.4f\n",name ,averageLike1,averageLike2 ,diff);
		// get top 10
		getTopTen(diffs);
		System.out.println("");
	 }

    // To find top 10 need priority queue with likelihood difference being priority.

    // We get at least three arguments. 
    public static void main(String[] args) {
	// The order of the models
		int k = Integer.parseInt(args[0]);
		In model1 = new In(args[1]);
		In model2 = new In(args[2]);
		// Read file as one line
		String linem = model1.readAll();
		// Trim newline in the end
		//String linem1 = linem.substring(0,linem.length()-1);
		// Convert all spaces to white spaces. 
		String s1 = linem.replaceAll("\\s"," "); 
	
		linem = model2.readAll();
		//linem1 = linem.substring(0,linem.length()-1);
		String s2 = linem.replaceAll("\\s"," "); 
		// Build the two models
		BestModel model = new BestModel(k, s1, s2);
		model1.close();
		model2.close();
		// For each other file, calculate likelihood under models
		int argc = args.length; 
		for (int i = 3; i< argc;i++) {
		    In test = new In(args[i]);
		    linem = test.readAll();
		    // Trim newline in the end
		    //linem1 = linem.substring(0,linem.length()-1);
		    // Convert all spaces to white spaces. 
		    s1 = linem.replaceAll("\\s"," "); 
			model.testLikelihood(args[i], s1);
		}
	}
}


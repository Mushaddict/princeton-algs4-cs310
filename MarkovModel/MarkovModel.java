import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.ST;
import java.util.HashSet;

public class MarkovModel{
    private ST<String,Integer> stk; // Strings of length k 
    private ST<String,Integer> stkplus1; // Strings of length k+1
    private int k; 
   // private String s; // The string. Can be removed later
    private int S; // The size of the alphabet
    
    // Constructor, k and s. You may assume k >=0 and <= string length.
    public MarkovModel(int k1, String s) {	
		// Get a temporary hash table for alphabet size as well. 
		k=k1;
		HashSet<Character> alphsize = new HashSet<Character>();
		stk = new ST<String,Integer>();
		stkplus1 = new ST<String,Integer>();
		// Build string s + k-1 initials for circular
		String prefix = s.substring(0,k); // For both k and k+1
		StringBuilder b = new StringBuilder(s);
		b.append(prefix);
		// Extract all substrings of size k and put in table.
		int len = s.length();
		for(int i=0;i<len;i++) {
		    // Go over substrings and put in tables
			char c = b.charAt(i);
			alphsize.add(c);
			String sub=b.substring(i,i+k+1);
			Integer fr = stkplus1.get(sub); // Add count of substring of size k+1.
			if(fr == null)
				stkplus1.put(sub,1);
			else
				stkplus1.put(sub,fr+1);
			String subk = b.substring(i,i+k);
			Integer frk = stk.get(subk); // Add count of substring of size k+1.
			if(frk == null)
					stk.put(subk,1);
			else
				stk.put(subk,frk+1);
		}
		S = alphsize.size();
	}	
	
	public int getK() {return k;}
	public int getS() {return S;}

	public String toString(){
		StringBuilder b = new StringBuilder(); 
		b.append(Integer.toString(S) + '\n');
			for (String s : stk.keys())	
				b.append(s + " " + stk.get(s) + '\n');
		for (String s : stkplus1.keys())	
				b.append(s + " " + stkplus1.get(s) + '\n');
		return b.toString();
	}
    // Get a string and find the laplace smoothing for a substring of size k+1
    public double laplace(String s) {
		// Assume s is of length k+1. That's ok.
		Integer freq = stkplus1.get(s);
		if(freq == null) 
			freq = 0;
		// Frequency of context
		String context = s.substring(0,k);
		Integer freqContext = stk.get(context);
		if(freqContext == null)
			freqContext = 0;
		return (freq+1.)/(freqContext+S);
    }

    public static void main(String[] args) {
        In filen = new In(args[1]);
	int k = Integer.parseInt(args[0]);
        // Read file as one line
        String line1 = filen.readAll();
	// Trim newline in the end
	String line = line1.substring(0,line1.length()-1);
	// Convert all spaces to white spaces. 
	String s = line.replaceAll("\\s"," "); 
        filen.close();
	MarkovModel model = new MarkovModel(k, s);
        System.out.println(model);
		System.out.printf("%.4f\n", model.laplace("aa"));
		System.out.printf("%.4f\n", model.laplace("ab"));
		System.out.printf("%.4f\n", model.laplace("bc"));
    }
}


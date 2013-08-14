package puzzleslove;

import java.util.ArrayList;

public class solveState {
	 public int max_length;
     public int dir_step;
     public int p;
     public ArrayList<solution> solutions;
     public solveState(int ml,int ds,int pp,ArrayList<solution> s){
    	 max_length=ml;
    	 dir_step=ds;
    	 p=pp;
    	 solutions = s;
     }
}

package puzzleslove;

import java.util.ArrayList;


public class solution{
	public int[][] board;
	public pos cursor;
	public pos initcursor;
	public ArrayList<Integer> path;
	public boolean is_done;
	public ArrayList<matchPair> matches;
	public solution(int[][] inboard,pos c,pos ic,ArrayList<Integer> p,boolean done,ArrayList<matchPair> m){
		board = new int[5][6];
		System.arraycopy(inboard, 0, board, 0, inboard.length);
		cursor = c;
		initcursor = ic;
		path = p;
		is_done = done;
		matches = m;
	}
	
}

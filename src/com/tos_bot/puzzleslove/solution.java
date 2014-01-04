package com.tos_bot.puzzleslove;

import android.annotation.SuppressLint;
import java.util.ArrayList;
import java.util.Comparator;


@SuppressLint("NewApi")
public class solution implements Comparator<solution> {
	public int[][] board;
	public int[][] currentboard;
	public pos cursor;
	public pos initcursor;
	public ArrayList<Integer> path;
	public boolean is_done;
	public ArrayList<matchPair> matches;
	public solution(int[][] inboard,pos c,pos ic,ArrayList<Integer> p,boolean done,ArrayList<matchPair> m){
		board = new int[5][6];
		for(int i =0;i<5;i++)
			for(int j=0;j<6;j++)
				board[i][j] = inboard[i][j];
		cursor = c;
		initcursor = ic;
		path = new ArrayList<Integer>();
		path.addAll(p);
		is_done = done;
		matches = m;
	}
	public solution(){
		
	}
    @Override
	public int compare(solution a, solution b) {
        return Integer.signum(a.matches.size() - b.matches.size());
    }
    public void setCB(int[][] b){
    	currentboard = new int[5][6];
		for(int i =0;i<5;i++)
			for(int j=0;j<6;j++)
				currentboard[i][j] = b[i][j];
    }
 
	
}

package com.tos_bot;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tos_bot.puzzleslove.solution;


public class jsonToSolution {
	public static boolean cmdDone;

	public static ArrayList<solution> passJsonToSol(String str) {

		ArrayList<solution> ret = new ArrayList<solution>();
		try {

				solution sol = new solution();
				JSONObject soljsonob = new JSONObject(str);
				JSONArray path = soljsonob.getJSONArray("path");
				for(int i =0 ;i<path.length();i++){
					int g = path.getInt(i);
					sol.path.add(new Integer(g));
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
}

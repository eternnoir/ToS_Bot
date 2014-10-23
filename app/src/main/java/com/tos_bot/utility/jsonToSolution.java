package com.tos_bot.utility;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tos_bot.puzzleslove.solution;

/**
 * 原本puzzle server會回傳一組json裡面包含所有solution
 * 為了節省流量將puzzle server只回傳最高分數的那組,所以這
 * 個class沒有使用了.
 * 
 * @author frankwang
 *
 */
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

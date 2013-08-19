package touchservice;

import java.util.Vector;

import puzzleslove.solution;

public interface ITouchService {
	public  void setUp(int bg,int inix,int iniy);

	public  void SendCommand(Vector<String> str);
	public  Vector<String> touchDown(int x,int y);
	public  Vector<String> touchUp();
	public  Vector<String> touchMove(int x1, int y1, int x2, int y2, int gap);
	public  Vector<String> touchMoveX(int x1,  int x2,  int gap);
	public  Vector<String> touchMoveY(int y1,  int y2, int gap);
	public  Vector<String> getCommandBySol(solution s);
	public  Vector<String> getCommandByPath(int inith,int initw,String[] pathsetp);
	
	
	
}

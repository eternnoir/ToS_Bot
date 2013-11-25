package net.atec.sender;

public class DeviceEvent {
	private String _event;
	private String _Pos_X;
	private String _Pos_Y;
	private String _TRACKING_ID;
	private String _PRESSURE;
	private String _xMax;
	private String _yMax;
	private String _tiMax;
	private String _pMax;
	private String _abs="3";
	public DeviceEvent(String e,String px,String py,String Tid,String pre, String xM,String yM,String tiM,String pM){
		_event = e;
		_Pos_X = px;
		_Pos_Y = py;
		_TRACKING_ID = Tid;
		_PRESSURE = pre;
		_xMax = xM;
		_yMax = yM;
		_tiMax = tiM;
		_pMax = pM;
	}
	
	public String getEvent(){
		return _event;
	}
	public String getBlankEvent(){
		return _event+" 0 0 0";
	}
	public String getPosXEvent(){
		return _event+" "+_abs+" "+_Pos_X+" ";
	}
	public String getPosYEvent(){
		return _event+" "+_abs+" "+_Pos_Y+" ";
	}
	public int getXid(){
		return Integer.parseInt(_Pos_X);
	}
	public int getYid(){
		return Integer.parseInt(_Pos_Y);
	}
	public int getTrackingIDid(){
		return Integer.parseInt(_TRACKING_ID);
	}
	public int getPurssureid(){
		return Integer.parseInt(_PRESSURE);
	}
	public String getScreenXMax(){
		return _xMax;
	}
	public String getScreenYMax(){
		return _yMax;
	}
	public String getTrackingEvent(){
		return _event+" "+_abs+" "+_TRACKING_ID+" ";
	}
	public String getPresureEvent(){
		return _event+" "+_abs+" "+_PRESSURE+" ";
	}
	
	public String getPressureMax(){
		return _pMax;
	}
	public String getTracningIdMax(){
		return _tiMax;
	}
	
}

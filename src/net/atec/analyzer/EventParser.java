package net.atec.analyzer;

import net.atec.sender.DeviceEvent;

public class EventParser {
	private String _fileStringlp;
	private String _fileStringp;
	private String _eventType;
	private int _PosX_ABS_Index;
	private int _PosY_ABS_Index;
	private int _TI_ABS_Index;
	private int _Pre_ABS_Index;
	private String _Pos_X;
	private String _Pos_Y;
	private String _TRACKING_ID;
	private String _PRESSURE;
	private String _xMax;
	private String _yMax;
	private String _tiMax;
	private String _pMax;
	public EventParser(String filelp,String filep){
		_fileStringlp = filelp;
		_fileStringp = filep;
	}
	public String findTouchscreenEvent(){
		_eventType = null;
		String[] stringArray = _fileStringlp.split("\n");
		for(int i=0;i<stringArray.length;i++){
			String linestr = stringArray[i];
			int absIndex= 1 ;
			int eventIndex = 1;
			int index = 1;
			if(linestr.contains("ABS_MT_POSITION_X")){	//find touch screen
				while(true){
					if(stringArray[i-index].contains("ABS (0003)")){
						_PosX_ABS_Index = absIndex;
						_PosY_ABS_Index = absIndex+1;
						_TI_ABS_Index = absIndex+2;
						_Pre_ABS_Index = absIndex+3;
					}
					if(stringArray[i-index].contains("/dev/input/event")){
						String tmp = stringArray[i-absIndex];
						int in = tmp.indexOf("/");
						_eventType = tmp.substring(in);
						break;
						
					}
					else {
						eventIndex ++;
					}
					index++;
					absIndex ++;
				}
			}
			if(_eventType!= null){
				break;
			}
		}	
		return _eventType;
	}
	public void findOtherAttr(){
		String[] stringArray = _fileStringp.split("\n");
		for(int i=0;i<stringArray.length;i++){
			String linestr = stringArray[i];
			if(linestr.contains(_eventType)){
				int index=1;
				while(true){
					if(stringArray[i+index].contains("ABS (0003)")){
						String posXstr = stringArray[i+index+_PosX_ABS_Index];
						String posYstr = stringArray[i+index+_PosY_ABS_Index];
						String posTstr = stringArray[i+index+_TI_ABS_Index];
						String posPstr = stringArray[i+index+_Pre_ABS_Index];
						String tmp[];
						tmp = findValues(posXstr);
						_Pos_X = tmp[0];
						_xMax = tmp[1];
						tmp = findValues(posYstr);
						_Pos_Y = tmp[0];
						_yMax = tmp[1];
						tmp = findValues(posTstr);
						_TRACKING_ID = tmp[0];
						_tiMax = tmp[1];
						tmp = findValues(posPstr);
						_PRESSURE = tmp[0];
						_pMax = tmp[1];
						break;
					}
					index++;
				}
			}
		}
	}
	
	private String[] findValues(String str){
		String[] ret = null;
		String valueId;
		String valueMax ="";
		String tmpX[] = str.split(":");
		tmpX[0] = tmpX[0].replace(" ", "");
		valueId = Integer.parseInt(tmpX[0], 16)+"";
		String values[] = tmpX[1].split(",");
		for(int x=0;x<values.length;x++){
			if(values[x].contains("max")){
				String mt = values[x].replace(" ", "");
				valueMax = mt.substring(3);
				break;
			}
		}
		ret = new String[]{ valueId , valueMax};
		return ret;
	}
	
	public DeviceEvent getNewDeviceEvent(){
		return new DeviceEvent(_eventType, _Pos_X, _Pos_Y, _TRACKING_ID, _PRESSURE, _xMax, _yMax, _tiMax, _pMax);
	}
}

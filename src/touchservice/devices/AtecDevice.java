package touchservice.devices;

import java.util.Vector;

import net.atec.sender.DeviceEvent;

import touchservice.AbstractTouchService;

public class AtecDevice extends AbstractTouchService {
	DeviceEvent _de;
	public AtecDevice(DeviceEvent de){
		_de = de;
	}
	@Override
		public Vector<String> touchDown(int x, int y) {
			Vector<String> cl = new Vector<String>();
			cl.add("sendevent "+_de.getTrackingEvent()+"12345\n");
			cl.add("sendevent "+_de.getPosXEvent() + x + "\n");
			cl.add("sendevent "+_de.getPosYEvent() + y + "\n");
			cl.add("sendevent "+_de.getPresureEvent()+ "105\n");
			cl.add("sendevent "+_de.getBlankEvent()+"\n");
			return cl;
		}
	
		@Override
		public Vector<String> touchUp() {
			Vector<String> cl = new Vector<String>();
			cl.add("sendevent "+_de.getPresureEvent()+_de.getPMax()+"\n");
			cl.add("sendevent "+_de.getBlankEvent()+"\n");
			cl.add("sendevent "+_de.getTrackingEvent()+"4294967295\n");
			cl.add("sendevent "+_de.getBlankEvent()+"\n");
			return cl;
		}
	
		@Override
		public Vector<String> touchMove(int x1, int y1, int x2, int y2, int gap) {
			Vector<String> cl = new Vector<String>();
			int x = x1;
			int y = y1;
			int xGap = (x2 - x1) / gap;
			int yGap = (y2 - y1) / gap;
			for (int g = 0; g < gap; g++) {
				x = x1 + g * xGap;
				y = y1 + g * yGap;
				// send x command
				cl.add("sendevent "+_de.getPosXEvent() + x + "\n");
				cl.add("sendevent "+_de.getBlankEvent()+"\n");
				// send y command
				cl.add("sendevent "+_de.getPosYEvent() + y + "\n");
				cl.add("sendevent "+_de.getBlankEvent()+"\n");
	
			}
			return cl;
		}
	
		@Override
		public Vector<String> touchMoveX(int x1, int x2, int gap) {
			Vector<String> cl = new Vector<String>();
			cl.add("sendevent "+_de.getPosXEvent() + x2 + "\n");
			cl.add("sendevent "+_de.getBlankEvent()+"\n");
	
			return cl;
		}
	
		@Override
		public Vector<String> touchMoveY(int y1, int y2, int gap) {
			Vector<String> cl = new Vector<String>();
			cl.add("sendevent "+_de.getPosYEvent() + y2 + "\n");
			cl.add("sendevent "+_de.getBlankEvent()+"\n");
			return cl;
		}
	

}

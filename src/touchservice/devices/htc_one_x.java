package touchservice.devices;

import java.util.Vector;

import touchservice.AbstractTouchService;

public class htc_one_x extends AbstractTouchService {

	@Override
	public Vector<String> touchDown(int x, int y) {
		Vector<String> cl = new Vector<String>();
		cl.add("sendevent /dev/input/event2 3 53 " + x + "\n");
		cl.add("sendevent /dev/input/event2 3 54 " + y + "\n");
		cl.add("sendevent /dev/input/event2 3 57 105\n");
		cl.add("sendevent /dev/input/event2 0 0 0\n");
		return cl;
	}

	@Override
	public Vector<String> touchUp() {
		Vector<String> cl = new Vector<String>();
		cl.add("sendevent /dev/input/event2 3 57 4294967295\n");
		cl.add("sendevent /dev/input/event2 0 0 0 \n");
		return cl;
	}

	@Override
	public Vector<String> touchMove(int x1, int y1, int x2, int y2, int gap) {
		Vector<String> cl = new Vector<String>();
		int x = x1;
		int y = y1;
		int xGap = (x2 - x1) / gap;
		int yGap = (y2 - y1) / gap;
		for(int g = 0; g < gap; g++){
			x = x1 + g * xGap;
			y = y1 + g * yGap;
			// send x command
			cl.add("sendevent /dev/input/event2 3 53 "+ x +"\n");
			cl.add("sendevent /dev/input/event2 0 0 0 \n");
			//send y command
			cl.add("sendevent /dev/input/event2 3 54 "+ y +"\n");
			cl.add("sendevent /dev/input/event2 0 0 0 \n");

		}
		return cl;
	}

	@Override
	public Vector<String> touchMoveX(int x1, int x2, int gap) {
        Vector<String> cl = new Vector<String>();
        cl.add("sendevent /dev/input/event2 3 53 " + x2 + "\n");
        cl.add("sendevent /dev/input/event2 0 0 0 \n");

        return cl;
	}

	@Override
	public Vector<String> touchMoveY(int y1, int y2, int gap) {
        Vector<String> cl = new Vector<String>();
        cl.add("sendevent /dev/input/event2 3 54 " + y2 + "\n");
        cl.add("sendevent /dev/input/event2 0 0 0 \n");

        return cl;
	}

}

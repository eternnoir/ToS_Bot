package com.tos_bot;

import java.io.FileReader;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class xmlParser {
	public xmlParser() {

	}

	public String parserXmlByID(String _path, String ID) {
		String ret = "";
		XmlPullParserFactory parserFactory;
		XmlPullParser pullParser;
		try {
			parserFactory = XmlPullParserFactory.newInstance();
			parserFactory.setNamespaceAware(true);
			pullParser = parserFactory.newPullParser();

			FileReader fileReader = new FileReader(_path);
			pullParser.setInput(fileReader);

			int eventType = pullParser.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {

				if (eventType == XmlPullParser.START_TAG) {
					Log.d("Test", "the tag name is :" + pullParser.getName());
					if (pullParser.getName().equals("string")) {
						if (pullParser.getAttributeValue(0).equals(ID)) {
							eventType = pullParser.next();
							if (eventType == XmlPullParser.TEXT) {
								ret = pullParser.getText();
							}
						}
					}
				}
				eventType = pullParser.next();

			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			return "Can't Find File";
		}
		return ret;
	}
}

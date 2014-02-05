package com.tos_bot;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class httpService {
	public String httpServiceGet(String url, String value) throws ClientProtocolException, IOException {
		return httpServiceGet(url,value,10);
	}

	public String httpServiceGet(String url, String value, int timeOutSec) throws ClientProtocolException, IOException {
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		// The default value is zero, that means the timeout is not used. 
		int timeoutConnection = timeOutSec*10;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = timeOutSec*10;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		String _ret = new String();
		HttpClient client = new DefaultHttpClient(httpParameters);
		HttpGet get = new HttpGet(url + "?" + value);
		HttpResponse response = client.execute(get);
		HttpEntity resEntity = response.getEntity();
		_ret = EntityUtils.toString(resEntity);
		return _ret;
	}
}
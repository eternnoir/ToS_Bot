package com.tos_bot;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class httpService {

	public String httpServiceGet(String url, String value) throws ClientProtocolException, IOException {
		String _ret = new String();
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url + "?" + value);
		HttpResponse response = client.execute(get);
		HttpEntity resEntity = response.getEntity();
		_ret = EntityUtils.toString(resEntity);
		return _ret;
	}
}
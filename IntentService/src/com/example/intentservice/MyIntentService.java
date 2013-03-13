package com.example.intentservice;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.ByteArrayBuffer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

public class MyIntentService extends IntentService {

	String responce;
    String response;
    String response4;
    String response1;
    String response2;
    String response3;
    String response31;
    String data ="";
    
    Handler mMainThreadHandler;
    
	public MyIntentService() {
		super("MyIntentService");
		mMainThreadHandler = new Handler();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub


		ResultReceiver rec = intent.getParcelableExtra("receiverTag");
		String recName= intent.getStringExtra("nameTag");
		Log.d("vinoth","received name="+recName);

		Log.d("vinoth","sending data back to activity");

		response = getElement();
		getData();
		
		Log.d("vinoth", "received"+response);
		
		
		mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
            	Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_LONG).show();
            }
        });
		
		Bundle b= new Bundle();
		b.putString("ServiceTag",response);
		rec.send(0, b);
	}


	public String getElement() {
		String url = "http://railsboxtech.com/singing/viewallsong.php"; 
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		
		Log.d("vinoth",url);
		
		HttpResponse response = null;
		String text = null;
		
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

			nameValuePairs.add(new BasicNameValuePair("userid",
					"126"));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			response = httpclient.execute(httppost);
			InputStream is = response.getEntity().getContent();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(20);

			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			text = new String(baf.toByteArray());

		} catch (ClientProtocolException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}

	public void getData() {


		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader reader = new StringReader(response);
			InputSource inputSrc = new InputSource(reader);
			Document doc = db.parse(inputSrc);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("response");

			Node node1 = nodeList.item(0);
			Element fstElement = (Element) node1;
			NodeList nameList11 = fstElement.getElementsByTagName("post");

			for (int i = 0; i < nameList11.getLength(); i++) {

				Node node = nameList11.item(i);
				Element fstElmnt = (Element) node;
				NodeList nameList1 = fstElmnt.getElementsByTagName("songid");
				Element nameElement1 = (Element) nameList1.item(0);
				nameList1 = nameElement1.getChildNodes();
				response4 = ((Node) nameList1.item(0)).getNodeValue();


				NodeList nameList2 = fstElmnt.getElementsByTagName("songname");
				Element nameElement2 = (Element) nameList2.item(0);
				nameList2 = nameElement2.getChildNodes();
				response1 = ((Node) nameList2.item(0)).getNodeValue();


				NodeList nameList3 = fstElmnt.getElementsByTagName("rating");
				Element nameElement3 = (Element) nameList3.item(0);
				nameList3 = nameElement3.getChildNodes();
				response2 = ((Node) nameList3.item(0)).getNodeValue();


				NodeList nameList4 = fstElmnt.getElementsByTagName("singer");
				Element nameElement4 = (Element) nameList4.item(0);
				nameList4 = nameElement4.getChildNodes();
				response3 = ((Node) nameList4.item(0)).getNodeValue();

				NodeList nameList41 = fstElmnt.getElementsByTagName("singerid");
				Element nameElement41 = (Element) nameList41.item(0);
				nameList41 = nameElement41.getChildNodes();
				response31 = ((Node) nameList41.item(0)).getNodeValue();

				data+=response1;
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
}
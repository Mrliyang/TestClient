package com.test.testclient;

import java.util.HashMap;

import com.test.utils.HttpUtil;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class ConnectService extends IntentService {

	private static final String ACTION_RECV_MSG = "com.test.testclient.action.RECEIVE_MESSAGE";

	public ConnectService() {
		super("TestIntentService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		/**
		 * �����ԣ�IntentService�����ǿ��Խ��к�ʱ�Ĳ����� IntentServiceʹ�ö��еķ�ʽ�������Intent������У�
		 * Ȼ����һ��worker thread(�߳�)����������е�Intent
		 * �����첽��startService����IntentService�ᴦ�����һ��֮���ٴ���ڶ���
		 */
		Boolean flag = false;
		// ͨ��intent��ȡ���̴߳������û����������ַ���
		String username = intent.getStringExtra("username");
		String password = intent.getStringExtra("password");
		flag = doLogin(username, password);
		Log.v("��¼���", flag.toString());

		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(ACTION_RECV_MSG);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra("result", flag.toString());
		sendBroadcast(broadcastIntent);
	}

	// ���巢������ķ���
	private Boolean doLogin(String username, String password) {
		String strFlag = "";
		// ʹ��Map��װ�������
		Log.v("kkkk", "5555555555");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", password);
		// ���巢�������URL
		//String url = HttpUtil.BASE_URL + "queryOrder?username=" + username
				//+ "&password=" + password; // GET��ʽ
		 String url = HttpUtil.BASE_URL + "TestServlet"; //POST��ʽ
		Log.d("url", url);
		Log.d("username", username);
		Log.d("password", password);
		try {
			// ��������
			strFlag = HttpUtil.postRequest(url, map); // POST��ʽ
			//strFlag = HttpUtil.getRequest(url); // GET��ʽ
			Log.d("����������ֵ", strFlag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (strFlag.trim().equals("true")) {
			return true;
		} else {
			return false;
		}

	}

}

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
		 * 经测试，IntentService里面是可以进行耗时的操作的 IntentService使用队列的方式将请求的Intent加入队列，
		 * 然后开启一个worker thread(线程)来处理队列中的Intent
		 * 对于异步的startService请求，IntentService会处理完成一个之后再处理第二个
		 */
		Boolean flag = false;
		// 通过intent获取主线程传来的用户名和密码字符串
		String username = intent.getStringExtra("username");
		String password = intent.getStringExtra("password");
		flag = doLogin(username, password);
		Log.v("登录结果", flag.toString());

		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(ACTION_RECV_MSG);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra("result", flag.toString());
		sendBroadcast(broadcastIntent);
	}

	// 定义发送请求的方法
	private Boolean doLogin(String username, String password) {
		String strFlag = "";
		// 使用Map封装请求参数
		Log.v("kkkk", "5555555555");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", password);
		// 定义发送请求的URL
		//String url = HttpUtil.BASE_URL + "queryOrder?username=" + username
				//+ "&password=" + password; // GET方式
		 String url = HttpUtil.BASE_URL + "TestServlet"; //POST方式
		Log.d("url", url);
		Log.d("username", username);
		Log.d("password", password);
		try {
			// 发送请求
			strFlag = HttpUtil.postRequest(url, map); // POST方式
			//strFlag = HttpUtil.getRequest(url); // GET方式
			Log.d("服务器返回值", strFlag);
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

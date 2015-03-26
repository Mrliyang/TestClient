package com.test.testclient;



import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String ACTION_RECV_MSG = "com.test.testclient.action.RECEIVE_MESSAGE";
	private Button loginBtn;
	private EditText et_username;
	private EditText et_password;
	private String userName;
	private String passWord;
	private MessageReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		// 动态注册receiver
		IntentFilter filter = new IntentFilter(ACTION_RECV_MSG);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new MessageReceiver();
		registerReceiver(receiver, filter);
	}

	private void initView() {
		// TODO Auto-generated method stub
		et_username = (EditText) findViewById(R.id.et_user);
		et_password = (EditText) findViewById(R.id.et_psw);
		loginBtn = (Button) findViewById(R.id.btn_login);
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//if (matchLoginMsg()) {
					// 如果校验成功
					Intent msgIntent = new Intent(MainActivity.this,
							ConnectService.class);
					Log.v("error", "111111");
					msgIntent.putExtra("username", et_username.getText()
							.toString().trim());
					msgIntent.putExtra("password", et_password.getText()
							.toString().trim());
					startService(msgIntent);
				//}

			}
		});
	}

	protected boolean matchLoginMsg() {
		// TODO Auto-generated method stub
		userName = et_username.getText().toString().trim();
		passWord = et_password.getText().toString().trim();
		if (userName.equals("")) {
			Toast.makeText(MainActivity.this, "账号不能为空", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if (passWord.equals("")) {
			Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;
	}

	// 接收广播类
	public class MessageReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String message = intent.getStringExtra("result");
			Log.i("MessageReceiver", message);
			// 如果登录成功
			if (message.equals("true")) {
				// 启动Main Activity
				Intent nextIntent = new Intent(MainActivity.this,
						AnotherActivity.class);
				startActivity(nextIntent);
				// 结束该Activity
				finish();
				// 注销广播接收器
				context.unregisterReceiver(this);
			} else {
				Toast.makeText(MainActivity.this, "用户名或密码错误，请重新输入!",
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

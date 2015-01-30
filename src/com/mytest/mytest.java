package com.mytest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class mytest extends Activity {//继承自Activity类
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {//实现onCreate方法
        super.onCreate(savedInstanceState); //继承自父类的onCreate方法
        setContentView(R.layout.main);      //默认加载第一个布局文件，显示main.xml界面
        
        //通过findViewById()方法得到第一个布局文件中的Button对象
       // b1 = (Button)findViewById(R.id.buttondenglu);
        
        //获取界面中的“登录”按钮对象
        final Button b_denglu = (Button)findViewById(R.id.buttondenglu);
        
      //获取界面中的“取消”按钮对象
        final Button b_quxiao = (Button)findViewById(R.id.button2);
        
        final EditText uid = (EditText)findViewById(R.id.username);
        final EditText pwd = (EditText)findViewById(R.id.password);
        
        b_denglu.setOnClickListener(              //负责监听鼠标单击事件
        	new View.OnClickListener() {          //为“登录”按钮添加监听器
				
				@Override
				public void onClick(View v) {
					
					Intent intent = new Intent();  //创建Intent对象
					
					// TODO Auto-generated method stub
					String uidStr = uid.getText().toString().trim();  //得到uid的字符串,trim()为清空两边的空格
					String pwdStr = pwd.getText().toString().trim();  //得到pwd的字符串
					
					//判断是否符合条件
					if(uidStr.equals("xide")&&pwdStr.equals("123456")){
						//弹出Toast
						//Toast.makeText(mytest.this, "恭喜您登录成功", Toast.LENGTH_SHORT).show();
						intent.setClass(mytest.this,secondtest.class); //设置当前的mytest类和需要启动的类
						startActivity(intent);  //启动Activity
						mytest.this.finish();   //关闭当前的Activity
					}else{
						Toast.makeText(mytest.this, "请输入正确的用户名或密码", Toast.LENGTH_SHORT).show();
					}
				}
			});
        
        	b_quxiao.setOnClickListener(
        			new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							uid.setText("");
							pwd.setText("");
						}
					});
    }
}
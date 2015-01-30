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

public class mytest extends Activity {//�̳���Activity��
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {//ʵ��onCreate����
        super.onCreate(savedInstanceState); //�̳��Ը����onCreate����
        setContentView(R.layout.main);      //Ĭ�ϼ��ص�һ�������ļ�����ʾmain.xml����
        
        //ͨ��findViewById()�����õ���һ�������ļ��е�Button����
       // b1 = (Button)findViewById(R.id.buttondenglu);
        
        //��ȡ�����еġ���¼����ť����
        final Button b_denglu = (Button)findViewById(R.id.buttondenglu);
        
      //��ȡ�����еġ�ȡ������ť����
        final Button b_quxiao = (Button)findViewById(R.id.button2);
        
        final EditText uid = (EditText)findViewById(R.id.username);
        final EditText pwd = (EditText)findViewById(R.id.password);
        
        b_denglu.setOnClickListener(              //���������굥���¼�
        	new View.OnClickListener() {          //Ϊ����¼����ť��Ӽ�����
				
				@Override
				public void onClick(View v) {
					
					Intent intent = new Intent();  //����Intent����
					
					// TODO Auto-generated method stub
					String uidStr = uid.getText().toString().trim();  //�õ�uid���ַ���,trim()Ϊ������ߵĿո�
					String pwdStr = pwd.getText().toString().trim();  //�õ�pwd���ַ���
					
					//�ж��Ƿ��������
					if(uidStr.equals("xide")&&pwdStr.equals("123456")){
						//����Toast
						//Toast.makeText(mytest.this, "��ϲ����¼�ɹ�", Toast.LENGTH_SHORT).show();
						intent.setClass(mytest.this,secondtest.class); //���õ�ǰ��mytest�����Ҫ��������
						startActivity(intent);  //����Activity
						mytest.this.finish();   //�رյ�ǰ��Activity
					}else{
						Toast.makeText(mytest.this, "��������ȷ���û���������", Toast.LENGTH_SHORT).show();
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
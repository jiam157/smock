package com.mytest;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.util.Hashtable;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class secondtest extends TabActivity {
	/*����������*/
	
	private Context mContext = null;
	private ImageView imView = null;
	private Handler messageHandler =null;

	private Boolean isStop = true;
	private Boolean flag = true;
	private String conStr1 = "192.168.1.13,50000";                               //��һ����ǩ���ð�ť��ʾ���ı�
    private Thread mThread = null;                                                //���յ���1�����߳�
    private Thread mThread1 = null;                                               //���յ���2�����߳�
    int buf_msg;
    boolean ms = false;
    boolean Th = false;
    boolean ms1 = false;
    boolean Th1 = false;
    
    boolean mthread_start = true;
    boolean mthread1_start = true;
	
	/*��һ����ǩ�������*/
    private Socket server = null;
    private InputStream reader;
    private OutputStream writer;
    private Socket server1 = null;
    private InputStream reader1;
    private OutputStream writer1;
	private Button Button_one_1 = null;             //�¶�
	private Button Button_one_4 = null;             //LED״̬
	private Button Button_one_6 = null;             //������״̬
	
	
    
	
	/*�ڶ�����ǩ�������*/
    private Socket socket = null;
    private BufferedWriter writerMsg;
    private int Port = 50000;
    private String IP = "192.168.1.13";


	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);  //���õ�ǰ��TabActivity����

        //tabHost��һ����ǩ����
        TabHost tabHost = this.getTabHost();
        
        //ÿһ��TabSpec������Ǹ���ǩ
        //TabSpec.setIndicator()���������ñ�ǩ��ʾ��ʽ
        //TabSpec.setContent()������ʾ��ǩ�·���������ʾ
        
        //�����һ����ǩ
        tabHost.addTab(tabHost.newTabSpec("����").setIndicator("����",getResources().getDrawable(R.drawable.shebei)).setContent(R.id.linearLayout1));
        
      //����ڶ�����ǩ
        tabHost.addTab(tabHost.newTabSpec("����").setIndicator("����",getResources().getDrawable(R.drawable.yuntai)).setContent(R.id.linearLayout2));
        
     
      
        /* ��Tab��ǩ�Ķ��� */  
        TabWidget mTabWidget = tabHost.getTabWidget();
        for(int i = 0; i < mTabWidget.getChildCount(); i++)
        {
        	
        	/* ����Tab�ĸ߶� */
        	mTabWidget.getChildAt(i).getLayoutParams().height = 90;
        	
        	/* ����Tab���������ɫ */
        	TextView tv = (TextView)mTabWidget.getChildAt(i).findViewById(android.R.id.title);
        	tv.setTextColor(Color.rgb(255,0,255));
        	
        	/* ���������С */
        	tv.setTextSize(17);
        }
        
        
        /* ------------���õ�һ����ǩ�������------------ */
        //��ʾ�������ݵ�Button����Button��TestView�ÿ�Щ
        Button_one_1 = (Button)findViewById(R.id.Button_one_1);
       
        Button_one_4 = (Button)findViewById(R.id.Button_one_2);
       
        Button_one_6 = (Button)findViewById(R.id.Button_one_3);
       
        //ѡ�������1
        RadioButton radiobutton1 = (RadioButton)findViewById(R.id.radiobutton_1);  //�õ�radiobutton������
        radiobutton1.setOnClickListener(new View.OnClickListener() {               //���ü����¼�
			
			@Override
			public void onClick(View v) {       //��д�ķ���
				// TODO Auto-generated method stub
				buf_msg = 1;

					try {
						if(mthread_start){                              //mthread_start��֤������ťʱֻ����һ��socket
							server = new Socket(IP,Port);               //����socket����
							writer = server.getOutputStream();          //���socket I/O��
							reader = server.getInputStream();
						}
						writer.write(buf_msg);                          //�������1������ʾ��ȡ����1������
					}catch (Exception e){
						e.printStackTrace();
					}
					try {
						if(mthread_start){                              //mthread_start��֤������ťʱֻ����һ���߳�
							mThread = new Thread(mRunnable);            //�����̣߳��߳�����mRunnable
							mThread.start();                            //�����߳�
						}
        			}catch (Exception e1){
        				e1.printStackTrace();	
        			}

			}
		});
        
        //ѡ�������2
        RadioButton radiobutton2 = (RadioButton)findViewById(R.id.radiobutton_2); 
        radiobutton2.setOnClickListener(new View.OnClickListener() {  
			
			@Override
			public void onClick(View v) {    
				buf_msg= 2;
				
				try {
					System.out.println("activity start!");
					if(mthread1_start){
					server1 = new Socket(IP,Port);               //����socket����
        			
					writer1 = server1.getOutputStream();         //���socket I/O��
					reader1 = server1.getInputStream();
					}
					writer1.write(buf_msg);
        		}catch (Exception e){
        			e.printStackTrace();
        		}
        		try {
        			if(mthread1_start){
						mThread1 = new Thread(mRunnable1);      //�����̣߳��߳�����mRunnable1
						mThread1.start();                       //�����߳�
        			}	                                       
        		}catch (Exception e1){
        			e1.printStackTrace();	
        		}
				

			}
		});
        
        
        //��һ����ǩʵ�֡����á���ť����
        final Button button_shezhi = (Button)findViewById(R.id.button_shezhi);
        mContext = this;
        button_shezhi.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				//��������:������ӵ�ַ�Ķ���
				LayoutInflater factory=LayoutInflater.from(mContext);
				final View v1=factory.inflate(R.layout.setting,null);
				AlertDialog.Builder dialog=new AlertDialog.Builder(mContext);
				dialog.setTitle("���ӵ�ַ");
				dialog.setView(v1);
				EditText et = (EditText)v1.findViewById(R.id.connectionurl);
		    	et.setText(conStr1);

		        dialog.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {  //��ȷ�������¼���Ӧ
		            public void onClick(DialogInterface dialog, int whichButton) {
		            	EditText qet = (EditText)v1.findViewById(R.id.connectionurl);
		            	conStr1 = qet.getText().toString();
		            	int len = conStr1.indexOf(",");                    //ȡ��,����ƫ����
		            	IP = conStr1.substring(0, len);                    //ȡƫ����Ϊ0��len�����ַ���
		            	try{
		            	Port = Integer.parseInt(conStr1.substring(len, conStr1.length()));  //������ַ���ת��Ϊ���͵�ʱ���м�һ��Ҫ�׳��쳣
		            	}catch(Exception e){
		            		return;
		            	}
		            	System.out.print(Port);
		            	System.out.print(IP);
		            	
		            	Toast.makeText(mContext, "���óɹ�!", Toast.LENGTH_SHORT).show(); 
		            }
		        });
		        dialog.setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
		        dialog.show();
				
			}});
        

        
        
        /* ------------���õڶ�����ǩ�������------------ */
        final ImageView imageview_on_off_01 = (ImageView)findViewById(R.id.on_off_01);
        final ImageView imageview_on_off_02 = (ImageView)findViewById(R.id.on_off_02);
       
        final ImageView imageview_on_off_04 = (ImageView)findViewById(R.id.on_off_04);
        
        final ToggleButton togglebutton01 = (ToggleButton)findViewById(R.id.togglebutton_1);
        final ToggleButton togglebutton02 = (ToggleButton)findViewById(R.id.togglebutton_2);
       
        final ToggleButton togglebutton04 = (ToggleButton)findViewById(R.id.togglebutton_4);
        final EditText edittext_5_shezhi = (EditText)findViewById(R.id.edittext_5_shezhi);
        final Button Button_5_shezhi = (Button)findViewById(R.id.Button_5_shezhi);
        
        //�ܿ��ؿ��ư�ť(���˲��ܿ������浥�������)
        togglebutton01.setOnCheckedChangeListener(
        		new OnCheckedChangeListener(){

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						togglebutton01.setChecked(isChecked);  //����ѡ��״̬
						
						//�ж�isChecked��ֵ
						imageview_on_off_01.setImageResource(isChecked?R.drawable.bulb_on:R.drawable.bulb_off);
						SendCmd1(isChecked?(byte)5:(byte)6);
					}
        });
        //�����ƿ��ư�ť
        togglebutton02.setOnCheckedChangeListener(
        		new OnCheckedChangeListener(){

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						togglebutton02.setChecked(isChecked);  //����ѡ��״̬
						
						//�ж�isChecked��ֵ
						imageview_on_off_02.setImageResource(isChecked?R.drawable.bulb_on:R.drawable.bulb_off);
						SendCmd1(isChecked?(byte)7:(byte)8);
					}
        });
       
        //���������ư�ť
        togglebutton04.setOnCheckedChangeListener(
        		new OnCheckedChangeListener(){

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						togglebutton04.setChecked(isChecked);  //����ѡ��״̬
						
						//�ж�isChecked��ֵ
						imageview_on_off_04.setImageResource(isChecked?R.drawable.bulb_on:R.drawable.bulb_off);
						SendCmd1(isChecked?(byte)11:(byte)12);
					}
        });
        //�����¶����ð�ť
        Button_5_shezhi.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				String s = new String(edittext_5_shezhi.getText().toString());
				try {
		    		socket = new Socket(IP,Port);
        			writerMsg = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
           			writerMsg.write(13);
           			writerMsg.write(Byte.parseByte(s));
           			writerMsg.flush();
           			socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
        });
        
        
      //ѡ�������1
        RadioButton radiobutton2_1 = (RadioButton)findViewById(R.id.radiobutton_2_1);  //�õ�radiobutton������
        radiobutton2_1.setOnClickListener(new View.OnClickListener() {   //���ü����¼�
			
			public void onClick(View v) {

				SendCmd1((byte)3);
				
			}});
        
        //ѡ�������2
        RadioButton radiobutton2_2 = (RadioButton)findViewById(R.id.radiobutton_2_2); 
        radiobutton2_2.setOnClickListener(new View.OnClickListener() {  
			
			public void onClick(View v) {

				SendCmd1((byte)4);
				
			}
		});
        
        System.out.println("activity start 1!");


      //�õ�һ��Looper���̵߳���Ϣ������ȡ����Ϣ,������ַ���Ϣ
        Looper looper = Looper.myLooper();
        messageHandler = new MessageHandler(looper);//������ʼ���Զ����Handler����
        
        
    }
	
	 //�Զ�����һ��Handler��,�ڻ����Ϣ��ʵ�ֽ������������ImageView
    class MessageHandler extends Handler {
        public MessageHandler(Looper looper) {
            super(looper);
        }
        public void handleMessage(Message msg) {
        	switch (msg.arg1) {
			case 0:
				imView.setImageBitmap((Bitmap)msg.obj);
				break;
			default:
				break;
			}
        	
        }
    }
	
	
    
  //������Ƶ������
    public void SendCmd1(final byte cmd){
    	new Thread() {
			public void run() {
				
		    	try {
		    		socket = new Socket(IP,Port);
        			writerMsg = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
           			writerMsg.write(cmd);
           			writerMsg.flush();
           			socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
    	}.start();
    }
    
    	
	//��һ����ǩ���ݻ�ȡ����ʾ(1)
	/*��ȡ�ƶ���Ϣ*/
    private Runnable mRunnable = new Runnable()
    {
    	public void run()
    	{
    		while(!ms)
    		{    	        		
    			ms = true;
    			Message msg = Message.obtain();		//��ȡ��Ϣ
    			mHandler.sendMessage(msg);   		//������Ϣ
    		}
    	}
    };     
    
    Handler	mHandler = new Handler()
    {
    	public void handleMessage(Message msg)
    	{
    		super.handleMessage(msg);		//ˢ�� 
    		if(!Th){
    			
    			try {					
	    			byte[] bufferin = new byte[36];
	    			reader.read(bufferin, 0, 36);
					if(buf_msg == 1){                               //buf_msg����֤���²�����1ʱ��ʾ���ǵ���1������
						//�¶ȵĶ�ȡ (��������ÿ�����ݶ���4��byte)
						String msg1 = new String(bufferin,0,4);
						System.out.println(msg1);
						Button_one_1.setText(msg1);

						//LED״̬�Ķ�ȡ(�����������ݶ��Ƿ�����ͷ��㣬ת�������ǿ��Ķ����ַ���)
						String msg4 = new String(bufferin,12, 4);
						System.out.println(msg4);
						if("0\0\0\0".equals(msg4)){	
							Button_one_4.setText("on");
						}
						else{
							Button_one_4.setText("off");
						}



						//������״̬�Ķ�ȡ
						String msg6 = new String(bufferin, 20, 4);
						System.out.println(msg6);
						if("0\0\0\0".equals(msg6)){						
							Button_one_6.setText("on");
						}
						else{
							Button_one_6.setText("off");
						}

					}

	    		}catch (IOException e){
					e.printStackTrace();
				
	    		}
	    		ms = false;
	    		mthread_start = false;
    			mRunnable.run();//�ص���ʵ��ѭ��
	    		
	    		
    		}    		
    	}
    };
    
	//��һ����ǩ���ݻ�ȡ����ʾ(2)
	/*��ȡ�ƶ���Ϣ*/
    private Runnable mRunnable1 = new Runnable()
    {
    	public void run()
    	{
    		while(!ms1)
    		{    	        		
    			ms1 = true;
    			Message msg_two = Message.obtain();		//��ȡ��Ϣ
    			mHandler1.sendMessage(msg_two);   		//������Ϣ
    		}
    	}
    };     
    
    Handler	mHandler1 = new Handler()
    {
    	public void handleMessage(Message msg_two)
    	{
    		super.handleMessage(msg_two);		//ˢ�� 
    		if(!Th1){
    			
    			try {					
	    			byte[] bufferin = new byte[36];
	    			reader1.read(bufferin, 0, 36);
					if(buf_msg == 2){
						//�¶ȵĶ�ȡ 
						String msg1 = new String(bufferin,0,4);
						System.out.println(msg1);
						Button_one_1.setText(msg1);
					
						//LED״̬�Ķ�ȡ
						String msg4 = new String(bufferin,12, 4);
						System.out.println(msg4);
							if("0\0\0\0".equals(msg4)){	
								Button_one_4.setText("on");
							}
							else {
								Button_one_4.setText("off");
							}


						//������״̬�Ķ�ȡ
						String msg6 = new String(bufferin, 20, 4);
						System.out.println(msg6);
							if("0\0\0\0".equals(msg6)){						
								Button_one_6.setText("on");
							}
							else{
								Button_one_6.setText("off");
							}
					}

	    		}catch (IOException e){
					e.printStackTrace();
				
	    		}
	    		ms1 = false;
	    		mthread1_start = false;
    			mRunnable1.run();//�ص���ʵ��ѭ��
    		}    		
    	}
    };
    
  
}







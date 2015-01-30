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
	/*主程序数据*/
	
	private Context mContext = null;
	private ImageView imView = null;
	private Handler messageHandler =null;

	private Boolean isStop = true;
	private Boolean flag = true;
	private String conStr1 = "192.168.1.13,50000";                               //第一个标签设置按钮显示的文本
    private Thread mThread = null;                                                //接收单板1数据线程
    private Thread mThread1 = null;                                               //接收单板2数据线程
    int buf_msg;
    boolean ms = false;
    boolean Th = false;
    boolean ms1 = false;
    boolean Th1 = false;
    
    boolean mthread_start = true;
    boolean mthread1_start = true;
	
	/*第一个标签相关数据*/
    private Socket server = null;
    private InputStream reader;
    private OutputStream writer;
    private Socket server1 = null;
    private InputStream reader1;
    private OutputStream writer1;
	private Button Button_one_1 = null;             //温度
	private Button Button_one_4 = null;             //LED状态
	private Button Button_one_6 = null;             //蜂鸣器状态
	
	
    
	
	/*第二个标签相关数据*/
    private Socket socket = null;
    private BufferedWriter writerMsg;
    private int Port = 50000;
    private String IP = "192.168.1.13";


	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);  //设置当前的TabActivity界面

        //tabHost是一个标签容器
        TabHost tabHost = this.getTabHost();
        
        //每一个TabSpec对象就是个标签
        //TabSpec.setIndicator()方法是设置标签显示样式
        //TabSpec.setContent()方法显示标签下方的内容显示
        
        //定义第一个标签
        tabHost.addTab(tabHost.newTabSpec("数据").setIndicator("数据",getResources().getDrawable(R.drawable.shebei)).setContent(R.id.linearLayout1));
        
      //定义第二个标签
        tabHost.addTab(tabHost.newTabSpec("设置").setIndicator("设置",getResources().getDrawable(R.drawable.yuntai)).setContent(R.id.linearLayout2));
        
     
      
        /* 对Tab标签的定制 */  
        TabWidget mTabWidget = tabHost.getTabWidget();
        for(int i = 0; i < mTabWidget.getChildCount(); i++)
        {
        	
        	/* 设置Tab的高度 */
        	mTabWidget.getChildAt(i).getLayoutParams().height = 90;
        	
        	/* 设置Tab内字体的颜色 */
        	TextView tv = (TextView)mTabWidget.getChildAt(i).findViewById(android.R.id.title);
        	tv.setTextColor(Color.rgb(255,0,255));
        	
        	/* 设置字体大小 */
        	tv.setTextSize(17);
        }
        
        
        /* ------------设置第一个标签里的内容------------ */
        //显示单板数据的Button键，Button比TestView好看些
        Button_one_1 = (Button)findViewById(R.id.Button_one_1);
       
        Button_one_4 = (Button)findViewById(R.id.Button_one_2);
       
        Button_one_6 = (Button)findViewById(R.id.Button_one_3);
       
        //选择测量点1
        RadioButton radiobutton1 = (RadioButton)findViewById(R.id.radiobutton_1);  //得到radiobutton的引用
        radiobutton1.setOnClickListener(new View.OnClickListener() {               //设置监听事件
			
			@Override
			public void onClick(View v) {       //重写的方法
				// TODO Auto-generated method stub
				buf_msg = 1;

					try {
						if(mthread_start){                              //mthread_start保证反复按钮时只创建一个socket
							server = new Socket(IP,Port);               //建立socket连接
							writer = server.getOutputStream();          //获得socket I/O流
							reader = server.getInputStream();
						}
						writer.write(buf_msg);                          //发送命令“1”，表示读取单板1的数据
					}catch (Exception e){
						e.printStackTrace();
					}
					try {
						if(mthread_start){                              //mthread_start保证反复按钮时只创建一个线程
							mThread = new Thread(mRunnable);            //定义线程，线程启动mRunnable
							mThread.start();                            //启动线程
						}
        			}catch (Exception e1){
        				e1.printStackTrace();	
        			}

			}
		});
        
        //选择测量点2
        RadioButton radiobutton2 = (RadioButton)findViewById(R.id.radiobutton_2); 
        radiobutton2.setOnClickListener(new View.OnClickListener() {  
			
			@Override
			public void onClick(View v) {    
				buf_msg= 2;
				
				try {
					System.out.println("activity start!");
					if(mthread1_start){
					server1 = new Socket(IP,Port);               //建立socket连接
        			
					writer1 = server1.getOutputStream();         //获得socket I/O流
					reader1 = server1.getInputStream();
					}
					writer1.write(buf_msg);
        		}catch (Exception e){
        			e.printStackTrace();
        		}
        		try {
        			if(mthread1_start){
						mThread1 = new Thread(mRunnable1);      //定义线程，线程启动mRunnable1
						mThread1.start();                       //启动线程
        			}	                                       
        		}catch (Exception e1){
        			e1.printStackTrace();	
        		}
				

			}
		});
        
        
        //第一个标签实现“设置”按钮功能
        final Button button_shezhi = (Button)findViewById(R.id.button_shezhi);
        mContext = this;
        button_shezhi.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				//功能设置:完成连接地址的定义
				LayoutInflater factory=LayoutInflater.from(mContext);
				final View v1=factory.inflate(R.layout.setting,null);
				AlertDialog.Builder dialog=new AlertDialog.Builder(mContext);
				dialog.setTitle("连接地址");
				dialog.setView(v1);
				EditText et = (EditText)v1.findViewById(R.id.connectionurl);
		    	et.setText(conStr1);

		        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //按确定键的事件响应
		            public void onClick(DialogInterface dialog, int whichButton) {
		            	EditText qet = (EditText)v1.findViewById(R.id.connectionurl);
		            	conStr1 = qet.getText().toString();
		            	int len = conStr1.indexOf(",");                    //取“,”的偏移量
		            	IP = conStr1.substring(0, len);                    //取偏移量为0到len的子字符串
		            	try{
		            	Port = Integer.parseInt(conStr1.substring(len, conStr1.length()));  //这里从字符串转换为整型的时候切记一定要抛出异常
		            	}catch(Exception e){
		            		return;
		            	}
		            	System.out.print(Port);
		            	System.out.print(IP);
		            	
		            	Toast.makeText(mContext, "设置成功!", Toast.LENGTH_SHORT).show(); 
		            }
		        });
		        dialog.setNegativeButton("取消",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
		        dialog.show();
				
			}});
        

        
        
        /* ------------设置第二个标签里的内容------------ */
        final ImageView imageview_on_off_01 = (ImageView)findViewById(R.id.on_off_01);
        final ImageView imageview_on_off_02 = (ImageView)findViewById(R.id.on_off_02);
       
        final ImageView imageview_on_off_04 = (ImageView)findViewById(R.id.on_off_04);
        
        final ToggleButton togglebutton01 = (ToggleButton)findViewById(R.id.togglebutton_1);
        final ToggleButton togglebutton02 = (ToggleButton)findViewById(R.id.togglebutton_2);
       
        final ToggleButton togglebutton04 = (ToggleButton)findViewById(R.id.togglebutton_4);
        final EditText edittext_5_shezhi = (EditText)findViewById(R.id.edittext_5_shezhi);
        final Button Button_5_shezhi = (Button)findViewById(R.id.Button_5_shezhi);
        
        //总开关控制按钮(打开了才能控制下面单板的内容)
        togglebutton01.setOnCheckedChangeListener(
        		new OnCheckedChangeListener(){

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						togglebutton01.setChecked(isChecked);  //设置选中状态
						
						//判断isChecked的值
						imageview_on_off_01.setImageResource(isChecked?R.drawable.bulb_on:R.drawable.bulb_off);
						SendCmd1(isChecked?(byte)5:(byte)6);
					}
        });
        //警报灯控制按钮
        togglebutton02.setOnCheckedChangeListener(
        		new OnCheckedChangeListener(){

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						togglebutton02.setChecked(isChecked);  //设置选中状态
						
						//判断isChecked的值
						imageview_on_off_02.setImageResource(isChecked?R.drawable.bulb_on:R.drawable.bulb_off);
						SendCmd1(isChecked?(byte)7:(byte)8);
					}
        });
       
        //蜂鸣器控制按钮
        togglebutton04.setOnCheckedChangeListener(
        		new OnCheckedChangeListener(){

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						togglebutton04.setChecked(isChecked);  //设置选中状态
						
						//判断isChecked的值
						imageview_on_off_04.setImageResource(isChecked?R.drawable.bulb_on:R.drawable.bulb_off);
						SendCmd1(isChecked?(byte)11:(byte)12);
					}
        });
        //报警温度设置按钮
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
        
        
      //选择测量点1
        RadioButton radiobutton2_1 = (RadioButton)findViewById(R.id.radiobutton_2_1);  //得到radiobutton的引用
        radiobutton2_1.setOnClickListener(new View.OnClickListener() {   //设置监听事件
			
			public void onClick(View v) {

				SendCmd1((byte)3);
				
			}});
        
        //选择测量点2
        RadioButton radiobutton2_2 = (RadioButton)findViewById(R.id.radiobutton_2_2); 
        radiobutton2_2.setOnClickListener(new View.OnClickListener() {  
			
			public void onClick(View v) {

				SendCmd1((byte)4);
				
			}
		});
        
        System.out.println("activity start 1!");


      //得到一个Looper从线程的消息队列中取出消息,并负责分发消息
        Looper looper = Looper.myLooper();
        messageHandler = new MessageHandler(looper);//用来初始化自定义的Handler对象
        
        
    }
	
	 //自定义了一个Handler类,在获得消息后实现将调整后的流绑定ImageView
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
	
	
    
  //单板控制的命令发送
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
    
    	
	//第一个标签数据获取与显示(1)
	/*获取云端信息*/
    private Runnable mRunnable = new Runnable()
    {
    	public void run()
    	{
    		while(!ms)
    		{    	        		
    			ms = true;
    			Message msg = Message.obtain();		//获取信息
    			mHandler.sendMessage(msg);   		//发送消息
    		}
    	}
    };     
    
    Handler	mHandler = new Handler()
    {
    	public void handleMessage(Message msg)
    	{
    		super.handleMessage(msg);		//刷新 
    		if(!Th){
    			
    			try {					
	    			byte[] bufferin = new byte[36];
	    			reader.read(bufferin, 0, 36);
					if(buf_msg == 1){                               //buf_msg来保证按下测量点1时显示的是单板1的数据
						//温度的读取 (发过来的每个数据都是4个byte)
						String msg1 = new String(bufferin,0,4);
						System.out.println(msg1);
						Button_one_1.setText(msg1);

						//LED状态的读取(以下三个数据都是发的零和非零，转换成我们看的懂的字符串)
						String msg4 = new String(bufferin,12, 4);
						System.out.println(msg4);
						if("0\0\0\0".equals(msg4)){	
							Button_one_4.setText("on");
						}
						else{
							Button_one_4.setText("off");
						}



						//蜂鸣器状态的读取
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
    			mRunnable.run();//回调，实现循环
	    		
	    		
    		}    		
    	}
    };
    
	//第一个标签数据获取与显示(2)
	/*获取云端信息*/
    private Runnable mRunnable1 = new Runnable()
    {
    	public void run()
    	{
    		while(!ms1)
    		{    	        		
    			ms1 = true;
    			Message msg_two = Message.obtain();		//获取信息
    			mHandler1.sendMessage(msg_two);   		//发送消息
    		}
    	}
    };     
    
    Handler	mHandler1 = new Handler()
    {
    	public void handleMessage(Message msg_two)
    	{
    		super.handleMessage(msg_two);		//刷新 
    		if(!Th1){
    			
    			try {					
	    			byte[] bufferin = new byte[36];
	    			reader1.read(bufferin, 0, 36);
					if(buf_msg == 2){
						//温度的读取 
						String msg1 = new String(bufferin,0,4);
						System.out.println(msg1);
						Button_one_1.setText(msg1);
					
						//LED状态的读取
						String msg4 = new String(bufferin,12, 4);
						System.out.println(msg4);
							if("0\0\0\0".equals(msg4)){	
								Button_one_4.setText("on");
							}
							else {
								Button_one_4.setText("off");
							}


						//蜂鸣器状态的读取
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
    			mRunnable1.run();//回调，实现循环
    		}    		
    	}
    };
    
  
}







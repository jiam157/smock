package com.mytest;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
//����ʵ�ַ��������������requet����
public class HttpRequest{
	public String doPost(String url , List<NameValuePair> nameValuePairs){  
        //�½�HttpClient����    
        HttpClient httpclient = new DefaultHttpClient();  
        //����POST����  
        HttpPost httppost = new HttpPost(url);  
        try {  
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));  
            HttpResponse response = httpclient.execute(httppost);
            return EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
            return "";
        } catch (IOException e) {  
            e.printStackTrace();
            return "";
        }
    }  
      
    /** 
     *Get���� 
     */  
    public String doGet(String url){  
        HttpParams httpParams = new BasicHttpParams();  
        HttpConnectionParams.setConnectionTimeout(httpParams,30000);    
        HttpConnectionParams.setSoTimeout(httpParams, 30000);    
              
        HttpClient httpClient = new DefaultHttpClient(httpParams);    
        // GET  
        HttpGet httpGet = new HttpGet(url);  
        try {  
            HttpResponse response = httpClient.execute(httpGet);  
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){  
            	return EntityUtils.toString(response.getEntity()); 
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
            return "";
        }  
        return "";
    } 
}
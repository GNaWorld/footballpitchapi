package com.common.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtils {
	/**
	 * get请求数据
	 * @param strUrl
	 */
	public static void DoGet(String strUrl){
//		String urlString="http://localhost:8080/ElevenAugMyServer/MyServerLet?username=ni&password=123456";
		try {
			URL url=new URL(strUrl); 											//生成URL链接
			URLConnection urlConnection=url.openConnection(); 					//打开URL链接
			HttpURLConnection httpConnection=(HttpURLConnection) urlConnection; //强制造型成为HttpURLConnection
			httpConnection.setRequestMethod("GET"); 							//设置请求方法
			httpConnection.setConnectTimeout(3000); 							//设置链接超时时间
			httpConnection.setReadTimeout(3000); 								//读取时间超时
			// 设置接受的数据类型
			httpConnection.setRequestProperty("Accept-Charset", "utf-8");
			// 设置可以接受序列化的java对象
			httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			int code=httpConnection.getResponseCode(); 							//获取HTTP状态码
			System.out.println("HTTP状态码"+code);
			if (code==HttpURLConnection.HTTP_OK) {
				InputStream is=httpConnection.getInputStream();
				BufferedReader br=new BufferedReader(new InputStreamReader(is));
				String line=br.readLine();
				while(line!=null){
					System.out.println(line);
					line=br.readLine();
				}
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}catch(SocketTimeoutException e1){
			System.out.println("链接超时");
		} catch(ConnectException e1){
			System.out.println("服务器拒绝链接");
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}

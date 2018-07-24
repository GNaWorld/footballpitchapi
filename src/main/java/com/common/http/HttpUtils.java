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
	 * get��������
	 * @param strUrl
	 */
	public static void DoGet(String strUrl){
//		String urlString="http://localhost:8080/ElevenAugMyServer/MyServerLet?username=ni&password=123456";
		try {
			URL url=new URL(strUrl); 											//����URL����
			URLConnection urlConnection=url.openConnection(); 					//��URL����
			HttpURLConnection httpConnection=(HttpURLConnection) urlConnection; //ǿ�����ͳ�ΪHttpURLConnection
			httpConnection.setRequestMethod("GET"); 							//�������󷽷�
			httpConnection.setConnectTimeout(3000); 							//�������ӳ�ʱʱ��
			httpConnection.setReadTimeout(3000); 								//��ȡʱ�䳬ʱ
			// ���ý��ܵ���������
			httpConnection.setRequestProperty("Accept-Charset", "utf-8");
			// ���ÿ��Խ������л���java����
			httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			int code=httpConnection.getResponseCode(); 							//��ȡHTTP״̬��
			System.out.println("HTTP״̬��"+code);
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
			System.out.println("���ӳ�ʱ");
		} catch(ConnectException e1){
			System.out.println("�������ܾ�����");
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}

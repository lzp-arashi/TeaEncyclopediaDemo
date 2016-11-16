package com.example.administrator.teaencyclopediademo.util;

import android.os.Handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpUtils {
    private static ExecutorService executorService;
    private static HttpUtils httpUtils;
    private Handler handler;
    private HttpUtils() {
        executorService = Executors.newFixedThreadPool(4);
        handler=new Handler();
    }
    private HttpUtils(int threadCount) {
        executorService = Executors.newFixedThreadPool(threadCount);
        handler=new Handler();
    }
    //网络请求下载数据
    public void sendRequest(final String url, final Listener listener){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                final String s = RequestUtils.get(url);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onresponse(s);
                    }
                });
            }
        });
    }
    public interface Listener{
        public void onresponse(String result);
    }
    public static HttpUtils getInstance(){
        if(httpUtils==null){
            httpUtils=new HttpUtils();
        }
        return httpUtils;
    }
    public static HttpUtils getInstance(int threadCount){
        if (httpUtils==null) {
            httpUtils=new HttpUtils(threadCount);
        }
        return httpUtils;
    }
    public static String getJsonStringFromInternet(String urlString) {
        String jsonString=null;
        InputStream inputStream=null;
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        try {
            URL url=new URL(urlString);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5*1000);
            connection.connect();

            if (connection.getResponseCode() == 200) {
                inputStream=connection.getInputStream();

                int length=-1;
                byte[] buffer=new byte[1024];

                while ((length=inputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                baos.flush();
                buffer=null;

                jsonString=baos.toString();
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                baos.close();
                if(inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return jsonString;
    }
}

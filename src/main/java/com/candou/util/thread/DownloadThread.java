/**
 * 
 */
package com.candou.util.thread;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Vector;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 分段下载线程
 * @author ZYWANG
 */
public class DownloadThread extends Thread {
	private String url = "";
	private long startPosition = 0;
	private long endPosition = 0;
	private File file = null;
	private boolean isRange = true;
	
	private Vector<DownloadThreadListener> listeners = new Vector<DownloadThreadListener>();
	
	/**
	 * 构建下载线程
	 * @param url 目标URL
	 * @param startPosition 开始字节位置
	 * @param endPosition 结束字节位置
	 * @param file 保存文件位置
	 */
	public DownloadThread(String url, long startPosition, long endPosition, File file) {
		this(url,startPosition,endPosition,file,true);
	}
	/**
	 * 构建下载线程
	 * @param url 目标URL
	 * @param startPosition 开始字节位置
	 * @param endPosition 结束字节位置
	 * @param file 保存文件位置
	 * @param isRange 是否使用分段下载
	 */
	public DownloadThread(String url, long startPosition, long endPosition, File file, boolean isRange) {
		this.url = url;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.file = file;
		this.isRange = isRange;
	}
	
	/**
	 * 现在过程代码
	 */
	public void run() {
		if(DownloadTask.getDebug()){
			System.out.println("Start:" + startPosition + "-" +endPosition);
		}
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpGet httpGet = new HttpGet(url);
			if(isRange){//多线程下载
				httpGet.addHeader("Range", "bytes="+startPosition+"-"+endPosition);
			}
			HttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if(DownloadTask.getDebug()){
				for(Header header : response.getAllHeaders()){
					System.out.println(header.getName()+":"+header.getValue());
				}
				System.out.println("statusCode:" + statusCode);
			}
			if(statusCode == 206 || (statusCode == 200 && !isRange)){
				InputStream inputStream = response.getEntity().getContent();
				//创建随机读写类
				RandomAccessFile outputStream = new RandomAccessFile(file, "rw");
				//跳到指定位置
				outputStream.seek(startPosition);
				int count = 0;byte[] buffer=new byte[1024];
				while((count = inputStream.read(buffer, 0, buffer.length))>0){
					outputStream.write(buffer, 0, count);
					//触发下载事件
					fireAfterPerDown(new DownloadThreadEvent(this,count));
				}
				outputStream.close();
			}
			httpGet.abort();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//触发下载完成事件
			fireDownCompleted(new DownloadThreadEvent(this, endPosition));
			if(DownloadTask.getDebug()){
				System.out.println("End:" + startPosition + "-" +endPosition);
			}
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	private void fireAfterPerDown(DownloadThreadEvent event){
		if(listeners.isEmpty()) return;
		 
		for(DownloadThreadListener listener:listeners){
			listener.afterPerDown(event);
		}
	}
	private void fireDownCompleted(DownloadThreadEvent event){
		if(listeners.isEmpty()) return;
		 
		for(DownloadThreadListener listener:listeners){
			listener.downCompleted(event);
		}
	}
	public void addDownloadListener(DownloadThreadListener listener){
		listeners.add(listener);
	}
}

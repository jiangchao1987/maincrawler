/**
 * 
 */
package com.candou.util.thread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author ZYWANG
 *
 */
public class DownloadTask {
	private String url = "";
	private int threadCount = 5;
	private String localPath = "";
	
	private boolean acceptRanges = false;
//	private String ranges = "";
	private long contentLength = 0;
	private long receivedCount = 0;
	private Vector<DownloadThread> threads = new Vector<DownloadThread>();
	
	private long lastCount = 0;
	private long beginTime = 0;
	private long endTime = 0;
	private Object object = new Object();
	private long autoCallbackSleep = 1000;
	
	private Vector<DownloadTaskListener> listeners = new Vector<DownloadTaskListener>();
	
	private static boolean DEBUG = false;
	/**
	 * 构造下载任务对象
	 * @param url 目标地址
	 */
	@Deprecated
	public DownloadTask(String url) {
		this(url,5);
	}
	/**
	 * 构造下载任务对象
	 * @param url 目标地址
	 * @param threadCount 线程数量
	 */
	@Deprecated
	public DownloadTask(String url, int threadCount) {
		this(url,"",threadCount);
	}
	/**
	 * 构造下载任务对象
	 * @param url 目标地址
	 * @param localPath 本地保存路径
	 */
	public DownloadTask(String url, String localPath) {
		this(url,localPath,5);
	}
	/**
	 * 构造下载任务对象
	 * @param url 目标地址
	 * @param localPath 本地保存路径
	 * @param threadCount 线程数量
	 */
	public DownloadTask(String url, String localPath, int threadCount) {
		this.url = url;
		this.threadCount = threadCount;
		this.localPath = localPath;
	}
	
	public void setAutoCallbackSleep(long autoCallbackSleep) {
		this.autoCallbackSleep = autoCallbackSleep;
	}
	public long getAutoCallbackSleep() {
		return this.autoCallbackSleep;
	}
	
	public static void setDebug(boolean debug){
		DEBUG = debug;
	}
	public static boolean getDebug(){
		return DEBUG;
	}
	
	public void setLocalPath(String localPath){
		this.localPath = localPath;
	}
	
	/**
	 * 开始下载
	 * @throws Exception
	 */
	public void startDown() throws Exception{
		HttpClient httpClient = new DefaultHttpClient();
		try {
			//获取下载文件信息
			System.out.println("获取下载文件信息");
			getDownloadFileInfo(httpClient);
			//启动多个下载线程
			System.out.println("启动多个下载线程");
			startDownloadThread();
			//开始监视下载数据
			System.out.println("开始监视下载数据");
			monitor();
		} catch (Exception e) {
			throw e;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

	/**
	 * 获取下载文件信息
	 */
	private void getDownloadFileInfo(HttpClient httpClient) throws IOException,
			ClientProtocolException, Exception {
		HttpHead httpHead = new HttpHead(url);
		HttpResponse response = httpClient.execute(httpHead);
		//获取HTTP状态码
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println("statusCode------"+statusCode);

		if(statusCode != 200) throw new Exception("资源不存在!");
		if(getDebug()){
			for(Header header : response.getAllHeaders()){
				System.out.println(header.getName()+":"+header.getValue());
			}
		}

		//Content-Length
		Header[] headers = response.getHeaders("Content-Length");
		if(headers.length > 0)
			contentLength = Long.valueOf(headers[0].getValue());
			System.out.println("资源长度contentLength:------"+contentLength);
		//Accept-Ranges
//		headers = response.getHeaders("Accept-Ranges");
//		if(headers.length > 0)
//			acceptRanges = true;

		httpHead.abort();
		
//		if(!acceptRanges){
			httpHead = new HttpHead(url);
			httpHead.addHeader("Range", "bytes=0-"+contentLength);
			response = httpClient.execute(httpHead);
			if(response.getStatusLine().getStatusCode() == 206){
				acceptRanges = true;
			}
			httpHead.abort();
//		}
	}

	/**
	 * 启动多个下载线程
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void startDownloadThread() throws IOException,
			FileNotFoundException {
		//创建下载文件
		File file = new File(localPath);
		file.createNewFile();
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		raf.setLength(contentLength);
		raf.close();
		
		//定义下载线程事件实现类
		System.out.println("定义下载线程事件实现类");
		DownloadThreadListener listener = new DownloadThreadListener() {
			public void afterPerDown(DownloadThreadEvent event) {
				//下载完一个片段后追加已下载字节数
				synchronized (object) {
					DownloadTask.this.receivedCount += event.getCount();
				}
			}

			public void downCompleted(DownloadThreadEvent event) {
				//下载线程执行完毕后从主任务中移除
				threads.remove(event.getTarget());
				if(getDebug()){
					System.out.println("剩余线程数："+threads.size());
				}
			}
		};
		
		//不支持多线程下载时
		if (!acceptRanges) {
			System.out.println("不支持多线程下载时");
			if(getDebug()){
				System.out.println("该地址不支持多线程下载");
			}
			//定义普通下载
			DownloadThread thread = new DownloadThread(url, 0, contentLength, file, false);
			thread.addDownloadListener(listener);
			thread.start();
			threads.add(thread);
			return;
		}
		
		//每个请求的大小
		long perThreadLength = contentLength / threadCount + 1;
		long startPosition = 0;
		long endPosition = perThreadLength;
		//循环创建多个下载线程
		do{
			System.out.println("循环创建多个下载线程");
			if(endPosition > contentLength)
				endPosition = contentLength;

			DownloadThread thread = new DownloadThread(url, startPosition, endPosition, file);
			thread.addDownloadListener(listener);
			thread.start();
			threads.add(thread);

			startPosition = endPosition + 1;//此处加 1,从结束位置的下一个地方开始请求
			endPosition += perThreadLength;
		} while (startPosition < contentLength);
	}
	
	/**
	 * 监视数据下载过程
	 */
	private void monitor() {
		new Thread() {
			public void run() {
				beginTime = System.currentTimeMillis();
				//当已下载字节数>=所有字节数 或者 下载线程都已关闭的时候结束循环
				while(receivedCount < contentLength && !threads.isEmpty()) {
					showInfo(false);
					
					try {
						Thread.sleep(autoCallbackSleep);
					} catch (InterruptedException e) { }
				}
				showInfo(true);
			}
		}.start();
	}

	/**
	 * 计算进度信息并触发事件
	 */
	private void showInfo(boolean complete) {
		//System.out.println("计算进度信息并触发事件");
		long currentTime = System.currentTimeMillis();
		double realTimeSpeed = (receivedCount - lastCount) * 1.0 / ((currentTime - endTime) / 1000.0);
		double globalSpeed = receivedCount * 1.0 / ((currentTime - beginTime) / 1000.0);
		lastCount = receivedCount;
		endTime = currentTime;
		//触发下载进度回调事件
		fireAutoCallback(new DownloadTaskEvent(receivedCount, contentLength, formatSpeed(realTimeSpeed), formatSpeed(globalSpeed),complete));
	};
	
	private void fireAutoCallback(DownloadTaskEvent event){
		if(listeners.isEmpty()) return;
		
		for(DownloadTaskListener listener:listeners){
			listener.autoCallback(event);
		}
	}
	public void addTaskListener(DownloadTaskListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * 测试被下载文件的名字
	 */
	public String guessFileName() throws Exception{
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpHead httpHead = new HttpHead(url);
			HttpResponse response = httpClient.execute(httpHead);
			String contentDisposition = null;
			if(response.getStatusLine().getStatusCode() == 200){
				//Content-Disposition
				Header[] headers = response.getHeaders("Content-Disposition");
				if(headers.length > 0)
					contentDisposition = headers[0].getValue();
			}
			httpHead.abort();
			
			if (contentDisposition!=null && contentDisposition.startsWith("attachment")) {
				return contentDisposition.substring(contentDisposition.indexOf("=")+1);
			} else if (Pattern.compile("(/|=)([^/&?]+\\.[a-zA-Z]+)").matcher(url).find()) {
				Matcher matcher = Pattern.compile("(/|=)([^/&?]+\\.[a-zA-Z]+)").matcher(url);
				String s = "";
				while(matcher.find())
					//将最后一个URL上的可能文件名作为本次猜测的结果
					s = matcher.group(2);
				return s;
			}
		} catch (Exception e) {
			throw e;
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		return "UnknowName.temp";
	}
	
	/**
	 * 格式化下载进度 为 B/s,K/s,M/s,G/s,T/s
	 */
	private String formatSpeed(double speed){
		DecimalFormat format = new DecimalFormat("#,##0.##");
		if(speed<1024){
			return format.format(speed)+" B/s";
		}
		
		speed /= 1024;
		if(speed<1024){
			return format.format(speed)+" K/s";
		}
		
		speed /= 1024;
		if(speed<1024){
			return format.format(speed)+" M/s";
		}
		
		speed /= 1024;
		if(speed<1024){
			return format.format(speed)+" G/s";
		}
		
		speed /= 1024;
		if(speed<1024){
			return format.format(speed)+" T/s";
		}
		
		return format.format(speed) + "B/s";
	}

}

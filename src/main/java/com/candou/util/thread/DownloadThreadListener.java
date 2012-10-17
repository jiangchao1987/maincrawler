/**
 * 
 */
package com.candou.util.thread;

/**
 * 下载线程事件
 * @author ZYWANG
 */
public interface DownloadThreadListener {

	/**
	 * 每次下载完一个字节数组后触发
	 * @param event
	 */
	public void afterPerDown(DownloadThreadEvent event);
	
	/**
	 * 下载完成时触发
	 * @param event
	 */
	public void downCompleted(DownloadThreadEvent event);
}

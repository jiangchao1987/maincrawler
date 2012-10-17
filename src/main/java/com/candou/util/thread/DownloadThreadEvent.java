/**
 * 
 */
package com.candou.util.thread;

/**
 * 下载线程的事件参数
 * @author ZYWANG
 */
public class DownloadThreadEvent {
	private DownloadThread target = null;
	private long count = 0;
	public DownloadThreadEvent(DownloadThread target, long count){
		this.target = target;
		this.count = count;
	}
	
	/**
	 * 获取下载事件源
	 * @return
	 */
	public DownloadThread getTarget() {
		return target;
	}
	
	/**
	 * 获取本次下载的字节大小
	 * @return
	 */
	public long getCount() {
		return count;
	}
}

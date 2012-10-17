/**
 * 
 */
package com.candou.util.thread;

/**
 * @author ZYWANG
 *
 */
public class DownloadTaskEvent {
	private long receivedCount = 0;
	private long totalCount = 0;
	
	private String realTimeSpeed = "";
	private String globalSpeed = "";
	
	private boolean complete = false;
	
	public DownloadTaskEvent(long ReceivedCount, long totalCount,
			String realTimeSpeed, String globalSpeed) {
		this(totalCount, totalCount, globalSpeed, globalSpeed, false);
	}
	
	public DownloadTaskEvent(long ReceivedCount, long totalCount,
			String realTimeSpeed, String globalSpeed, boolean complete) {
		this.receivedCount = ReceivedCount;
		this.totalCount = totalCount;
		this.realTimeSpeed = realTimeSpeed;
		this.globalSpeed = globalSpeed;
		this.complete = complete;
	}

	/**
	 * 获取文件已接收大小(字节数)
	 * @return
	 */
	public long getReceivedCount() {
		return receivedCount;
	}

	/**
	 * 获取文件总大小(字节数)
	 * @return
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * 获取实时速度
	 * @return
	 */
	public String getRealTimeSpeed() {
		return realTimeSpeed;
	}

	/**
	 * 获取全局速度
	 * @return
	 */
	public String getGlobalSpeed() {
		return globalSpeed;
	}
	
	public boolean isComplete() {
		return complete;
	}
}

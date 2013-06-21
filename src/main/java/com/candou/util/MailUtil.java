package com.candou.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.candou.conf.Configure;

public class MailUtil {
	private static Logger log = Logger.getLogger(ImageUtil.class);
	private static String fromMail = "CandouCrawler@163.com";
	private static String smtpPath = "smtp.163.com";
	private static String userName = "CandouCrawler";
	private static String password = "candou123!@#";
	private static String subject = "爬虫程序异常提醒";// 邮件主题
	private static String ip = "";
	private static String address="";
	
   
	/**
	 * 测试Mail功能
	 * 
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException {
		try {
			int a = 1/0;
		} catch (Exception e) {
			MailUtil.sendMail("aifengliu@candou.com","PPMain",ExceptionUtils.getStackTrace(e));
		}
		
	}

	public static void sendMail(String toMail,String projectName,String Exceptiontext) {
		try {
			InetAddress  addr = InetAddress.getLocalHost();
			ip=addr.getHostAddress();//获得本机IP
			address=addr.getHostName();//获得本机名称
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}  
		

		String Contenttext = "时间 :"
                +DateTimeUtil.nowDateTime()
				+"\n程序 : "+projectName
				+"\n机器IP地址 : "+ip
				+"\n机器名称 : "+address
				+"\n目录 : "
				+System.getProperty("user.dir")
				+"\n数据库 :"
				+Configure.getProperty("db_name")
				+"\n异常信息 :\n"
				.concat(Exceptiontext);// 邮件内容

		// 创建Properties对象
		Properties properties = new Properties();
		// 设置传输协议
		properties.setProperty("mail.transport.protocol", "smtp");
		// 设置发信邮箱的smtp地址
		properties.put("mail.smtp.host", smtpPath);
		// 验证
		properties.setProperty("mail.smtp.auth", "true");
		// 使用验证，创建一个Authenticator
		Authenticator auth = new AjavaAuthenticator(userName, password);
		// 根据Properties，Authenticator创建Session
		Session session = Session.getDefaultInstance(properties, auth);
		try {
			// Message存储发送的电子邮件信息
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromMail));
			// 设置收信邮箱
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(
					toMail));
			message.setSubject(subject);// 设置主题
			message.setText(Contenttext);// 设置内容
			Transport.send(message);// 发送
			log.info("Mail Sent Successfully!");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}

// 创建传入身份验证信息的 Authenticator类
class AjavaAuthenticator extends Authenticator {
	private final String user;
	private final String pwd;

	public AjavaAuthenticator(String user, String pwd) {
		this.user = user;
		this.pwd = pwd;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, pwd);
	}
}

package com.candou.ic.navigation.wxdh.imgload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import com.candou.ic.navigation.wxdh.dao.AppDao;
import com.candou.ic.navigation.wxdh.dao.JobDao;
import com.candou.ic.navigation.wxdh.dao.PhotoDao;
import com.candou.ic.navigation.wxdh.vo.App;
import com.candou.ic.navigation.wxdh.vo.Job;
import com.candou.ic.navigation.wxdh.vo.Photo;
import com.candou.util.BrowserUtil;
import com.candou.util.FileUtil;
import com.candou.util.TextUtils;


public class PhotoDownloader
{
  private static Logger log = Logger.getLogger(PhotoDownloader.class.getName());
  private static FileOutputStream fos = null;
/**
 * 下载photo图片
 * 描述
 * @param photo
 * @throws IOException
 */
  public static void downloadPhoto(Photo photo)
    throws IOException
  {
    log.info(photo.toString());
    //创建photo的主目录
    String imgDirStr = checkImgFolder("app_it");
    URL url = null;
    try {
      url = new URL(photo.getPhoto_url());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    DefaultHttpClient httpclient = new DefaultHttpClient();
    HttpParams params = httpclient.getParams();
    HttpConnectionParams.setConnectionTimeout(params, 30000);
    HttpConnectionParams.setSoTimeout(params, 30000);
    httpclient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
    try
    {
      HttpGet httpget = new HttpGet(url.toString());
      HttpProtocolParams.setUserAgent(httpclient.getParams(), BrowserUtil.getRandomBrowserUserAgent());
      HttpResponse response = httpclient.execute(httpget);
      if (response.getStatusLine().getStatusCode() == 200) {
        String filename = FileUtil.generateFileName("jpg");
        String partfoldername = FileUtil.generatePartFolderName(photo.getPhoto_url(), getGfanUrl(photo.getPhoto_url()));
        String partfilename = partfoldername + File.separator + filename;
        photo.setFilename(partfilename);
        //把文件名进行MD5处理
        String filenames = TextUtils.encryptedByMD5("wxdh_app_it"+partfilename);
        //生成二级目录
        File fMkis = new File(imgDirStr.concat(filenames.substring(0,2)),filenames.substring(2,4));
	    if(!fMkis.exists()){
	    	fMkis.mkdirs();
	    }
	    File f = new File(fMkis.getAbsolutePath(),filenames);
	    log.info(String.format("native save: %s", f.getAbsolutePath()));
	    fos = new FileOutputStream(f);
	    response.getEntity().writeTo(fos);
	    fos.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (fos != null) {
        fos.close();
      }
    }
    PhotoDao.UpdatePhotoNames(photo);
    log.info("batch updated ...");
  }



  /**
   * 下载photo图片
   * 描述
   * @param photo
   * @throws IOException
   */
    public static void downloadPhoto_im(Photo photo)
      throws IOException
    {
      log.info(photo.toString());
      //创建photo的主目录
      String imgDirStr = checkImgFolder("app_im");
      URL url = null;
      try {
        url = new URL(photo.getPhoto_url());
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }
      DefaultHttpClient httpclient = new DefaultHttpClient();
      HttpParams params = httpclient.getParams();
      HttpConnectionParams.setConnectionTimeout(params, 30000);
      HttpConnectionParams.setSoTimeout(params, 30000);
      httpclient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
      try
      {
        HttpGet httpget = new HttpGet(url.toString());
        HttpProtocolParams.setUserAgent(httpclient.getParams(), BrowserUtil.getRandomBrowserUserAgent());
        HttpResponse response = httpclient.execute(httpget);
        if (response.getStatusLine().getStatusCode() == 200) {
          String filename = FileUtil.generateFileName("jpg");
          String partfoldername = FileUtil.generatePartFolderName(photo.getPhoto_url(), getGfanUrl(photo.getPhoto_url()));
          String partfilename = partfoldername + File.separator + filename;
          photo.setFilename(partfilename);
          //把文件名进行MD5处理
          String filenames = TextUtils.encryptedByMD5("wxdh_app_im"+partfilename);
          //生成二级目录
          File fMkis = new File(imgDirStr.concat(filenames.substring(0,2)),filenames.substring(2,4));
          if(!fMkis.exists()){
              fMkis.mkdirs();
          }
          File f = new File(fMkis.getAbsolutePath(),filenames);
          log.info(String.format("native save: %s", f.getAbsolutePath()));
          fos = new FileOutputStream(f);
          response.getEntity().writeTo(fos);
          fos.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (fos != null) {
          fos.close();
        }
      }
      PhotoDao.UpdatePhotoNames(photo);
      log.info("batch updated ...");
    }
  /**
   * 下载icon
   * 描述
   * @param job
   * @throws IOException
   */
  public static void downloadIcon_job(Job job) throws IOException
  {
    log.info(job.toString());
    String imgDirStr = checkImgFolder("icon");
    URL url = null;
    try {
      url = new URL(job.getIcon());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    DefaultHttpClient httpclient = new DefaultHttpClient();
    HttpParams params = httpclient.getParams();
    HttpConnectionParams.setConnectionTimeout(params, 30000);
    HttpConnectionParams.setSoTimeout(params, 30000);
    httpclient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
    try
    {
      HttpGet httpget = new HttpGet(url.toString());
      HttpProtocolParams.setUserAgent(httpclient.getParams(), BrowserUtil.getRandomBrowserUserAgent());
      HttpResponse response = httpclient.execute(httpget);
      if (response.getStatusLine().getStatusCode() == 200)
      {
        String filename = FileUtil.generateFileName("jpg");

        String partfoldername = FileUtil.generatePartFolderName(job.getIcon(), getGfanUrl(job.getIcon()));
        String partfilename = partfoldername + File.separator + filename;
        job.setIcon_name(filename);
        //进行md5处理
        String filenames = TextUtils.encryptedByMD5("wxdh_job_icon"+job.getId());
        File fMkis = new File(imgDirStr.concat(filenames.substring(0,2)),filenames.substring(2,4));
	    if(!fMkis.exists()){
	    	fMkis.mkdirs();
	    }
	    File f = new File(fMkis.getAbsolutePath(),filenames);
	    fos = new FileOutputStream(f);
	    response.getEntity().writeTo(fos);
	    fos.close();
      } else {
        return;
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (fos != null)
        fos.close();
    }
    if (fos != null) {
      fos.close();
    }

    System.out.println("icon ");
    JobDao.updateAppIconUrl(job);
    log.info("batch updated ...");
  }


  /**
   * 下载icon
   * 描述
   * @param app
   * @throws IOException
   */
  public static void downloadIcon_app(App app) throws IOException
  {
    log.info(app.toString());
    String imgDirStr = checkImgFolder("icon");
    URL url = null;
    try {
      url = new URL(app.getIcon());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    DefaultHttpClient httpclient = new DefaultHttpClient();
    HttpParams params = httpclient.getParams();
    HttpConnectionParams.setConnectionTimeout(params, 30000);
    HttpConnectionParams.setSoTimeout(params, 30000);
    httpclient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
    try
    {
      HttpGet httpget = new HttpGet(url.toString());
      HttpProtocolParams.setUserAgent(httpclient.getParams(), BrowserUtil.getRandomBrowserUserAgent());
      HttpResponse response = httpclient.execute(httpget);
      if (response.getStatusLine().getStatusCode() == 200)
      {
        String filename = FileUtil.generateFileName("jpg");

        String partfoldername = FileUtil.generatePartFolderName(app.getIcon(), getGfanUrl(app.getIcon()));
        String partfilename = partfoldername + File.separator + filename;
        app.setIcon_name(partfilename);
        //进行md5处理
        String filenames = TextUtils.encryptedByMD5("wxdh_app_icon"+app.getId());
        File fMkis = new File(imgDirStr.concat(filenames.substring(0,2)),filenames.substring(2,4));
        if(!fMkis.exists()){
            fMkis.mkdirs();
        }
        File f = new File(fMkis.getAbsolutePath(),filenames);
        fos = new FileOutputStream(f);
        response.getEntity().writeTo(fos);
        fos.close();
      } else {
        return;
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (fos != null)
        fos.close();
    }
    if (fos != null) {
      fos.close();
    }

    System.out.println("icon ");
    AppDao.updateAppIconUrl(app);
    log.info("batch updated ...");
  }




  /**
   * 下载imc 二维码
   * 描述
   * @param app
   * @throws IOException
   */
  public static void downloadIcon_app_imc(App app) throws IOException
  {
    log.info(app.toString());
    String imgDirStr = checkImgFolder("imc");
    URL url = null;
    try {
      url = new URL(app.getImc());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    DefaultHttpClient httpclient = new DefaultHttpClient();
    HttpParams params = httpclient.getParams();
    HttpConnectionParams.setConnectionTimeout(params, 30000);
    HttpConnectionParams.setSoTimeout(params, 30000);
    httpclient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
    try
    {
      HttpGet httpget = new HttpGet(url.toString());
      HttpProtocolParams.setUserAgent(httpclient.getParams(), BrowserUtil.getRandomBrowserUserAgent());
      HttpResponse response = httpclient.execute(httpget);
      if (response.getStatusLine().getStatusCode() == 200)
      {
        String filename = FileUtil.generateFileName("jpg");

        String partfoldername = FileUtil.generatePartFolderName(app.getImc(), getGfanUrl(app.getImc()));
        String partfilename = partfoldername + File.separator + filename;
        app.setImc_name(partfilename);
        //进行md5处理
        String filenames = TextUtils.encryptedByMD5("wxdh_app_imc"+app.getId());
        File fMkis = new File(imgDirStr.concat(filenames.substring(0,2)),filenames.substring(2,4));
        if(!fMkis.exists()){
            fMkis.mkdirs();
        }
        File f = new File(fMkis.getAbsolutePath(),filenames);
        fos = new FileOutputStream(f);
        response.getEntity().writeTo(fos);
        fos.close();
      } else {
        return;
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (fos != null)
        fos.close();
    }
    if (fos != null) {
      fos.close();
    }

    System.out.println("imc二维码 ");
    //AppDao.updateAppIconUrl(app);
    log.info("batch updated ...");
  }



  public static String getGfanUrl(String url) {
    String s = "";
    String regex = "http://[a-z]{3}\\d{1}.image.apk.gfan.com";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(url);
    String tt = null;
    while (matcher.find()) {
      tt = matcher.group();
      s = tt;
    }
    if (tt == null) {
      s = "http://image.apk.gfan.com";
    }
    return s;
  }

  public static String getIP() throws SocketException {
    Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
    InetAddress ip = null;
    while (allNetInterfaces.hasMoreElements()) {
      NetworkInterface netInterface =
        (NetworkInterface)allNetInterfaces
        .nextElement();
      Enumeration addresses = netInterface.getInetAddresses();
      while (addresses.hasMoreElements()) {
        ip = (InetAddress)addresses.nextElement();
        if ((ip != null) && ((ip instanceof Inet4Address))) {
          return ip.getHostAddress();
        }
      }
    }
    return "";
  }
//}
///**
// * 图片下载类
// *
// *
// * @author Austemer
// * @version 1.0
// * @created 2011-12-30 下午6:34:48
// */
//public class GFanUpdatePhotoDownloader {
//	private static Logger log = Logger.getLogger(GFanUpdatePhotoDownloader.class.getName());
//	private static FileOutputStream fos = null;
//
//	/* 下载大图方法*/
//	public static void downloadPhoto(Photo photo) throws IOException{
////		JavaMessageDao.removeJavaMessage("GFanApkData");
////		JavaMessage javaMessage = new JavaMessage();
////		String name = ManagementFactory.getRuntimeMXBean().getName();
////		InetAddress inet = InetAddress.getLocalHost();
////		name = name.substring(0,name.indexOf("@"));
////		javaMessage.setPid(Integer.valueOf(name.trim()));
////		javaMessage.setHost(getIP());
////		javaMessage.setHostName(inet.getHostName());
////		javaMessage.setJava_name("GFanApkData");
////		javaMessage.setStatus(1);
////		JavaMessageDao.add(javaMessage);
//		log.info(photo.toString());
//		String imgDirStr = checkImgFolder("imgfolder");
//        URL url = null;
//        try {
//            url = new URL(photo.getOriginalUrl());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        DefaultHttpClient httpclient = new DefaultHttpClient();
//        HttpParams params = httpclient.getParams();
//        HttpConnectionParams.setConnectionTimeout(params, 30000);
//        HttpConnectionParams.setSoTimeout(params, 30000);
//        httpclient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
//
//        try {
//        	System.out.println(url.toString());
//            HttpGet httpget = new HttpGet(url.toString());
//            HttpProtocolParams.setUserAgent(httpclient.getParams(), BrowserUtil.getRandomBrowserUserAgent());
//            HttpResponse response = httpclient.execute(httpget);
//            if(response.getStatusLine().getStatusCode()==200){
//	            String filename = FileUtil.generateFileName("jpg");    //生成单独文件名
//	            String partfoldername = FileUtil.generatePartFolderName(photo.getOriginalUrl(),getGfanUrl(photo.getOriginalUrl()));   //生成上级目录名       asdf/PImages/2010/5
//	            File folder = new File(imgDirStr.concat(partfoldername));
////	            if (!folder.exists()) {
////	                folder.mkdirs();
////	            }
//
//	            String partfilename = partfoldername + File.separator + filename;      //生成数据库存储文件名
//	            photo.setFilename(partfilename);
//	            File f = new File(imgDirStr,partfilename);
////	            String filenames = TextUtils.encryptedByMD5(partfilename);
////	            File fMkis = new File();
////	            if(!fMkis.exists()){
////	            	fMkis.mkdirs();
////	            }
////	            File f = new File(fMkis.getAbsolutePath(),filenames);
//
//	            fos = new FileOutputStream(f);
//	            response.getEntity().writeTo(fos);
//	            fos.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally{
//			if(fos!=null){
//				fos.close();
//			}
//		}
//            PhotoDAO.UpdatePhotoNames(photo);
//            log.info("batch updated ...");
//	}
//
//	//应用logo图片下载
//	public static void downloadIcon(App app) throws IOException{
//		log.info(app.toString());
//		String imgDirStr = checkImgFolder("imgfolder");
//        URL url = null;
//        try {
//            url = new URL(app.getIconUrl());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        DefaultHttpClient httpclient = new DefaultHttpClient();
//        HttpParams params = httpclient.getParams();
//        HttpConnectionParams.setConnectionTimeout(params, 30000);
//        HttpConnectionParams.setSoTimeout(params, 30000);
//        httpclient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
//
//        try {
//            HttpGet httpget = new HttpGet(url.toString());
//            HttpProtocolParams.setUserAgent(httpclient.getParams(), BrowserUtil.getRandomBrowserUserAgent());
//            HttpResponse response = httpclient.execute(httpget);
//            if(response.getStatusLine().getStatusCode()==200){
//	//            String filename = FileUtil.generateFileName("jpg");
//	//            app.setIconUrl(filename);
//	//            filename = imgDirStr.concat(filename);
//	            String filename = FileUtil.generateFileName("jpg");    //生成单独文件名
//
//	            String partfoldername = FileUtil.generatePartFolderName(app.getIconUrl(),getGfanUrl(app.getIconUrl()));   //生成上级目录名       asdf/PImages/2010/5
//	            File folder = new File(imgDirStr.concat(partfoldername));
////	            if (!folder.exists()) {
////	                folder.mkdirs();
////	            }
//
//	            String partfilename = partfoldername + File.separator + filename;
//	            System.out.println(partfilename);//生成数据库存储文件名
//	            app.setIconName(partfilename);
////	            String filenames = TextUtils.encryptedByMD5("android"+app.getAppId());
////	            File fMkis = new File();
////	            if(!fMkis.exists()){
////	            	fMkis.mkdirs();
////	            }
//
//	            File f = new File(imgDirStr,partfilename);
//	            if(f.)
//	            fos = new FileOutputStream(f);
//	            response.getEntity().writeTo(fos);
//	            fos.close();
//            }else {
//            	return;
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally{
//			if(fos!=null){
//				fos.close();
//			}
//		}
//        System.out.println("icon ");
//            AppDAO.updateAppIconUrl(app);
//            log.info("batch updated ...");
//	}
//
	  public static String checkImgFolder(String name) {
	        String userDirStr = System.getProperty("user.dir");//用户的当前工作目录
	        String imgDirStr = userDirStr.concat(File.separator).concat(name).concat(File.separator);

	        File imgDir = new File(imgDirStr);
	        if (!imgDir.exists()) {
	            imgDir.mkdirs();
	            log.info(imgDirStr + " created ...");
	        } else {
	            log.info(imgDirStr + " existed ...");
	        }


	        return imgDirStr;
	    }
//
//	  public static String getGfanUrl(String url){
//		  String s = "";
//          String regex = "http://[a-z]{3}\\d{1}.image.apk.gfan.com";
//  		Pattern pattern = Pattern.compile(regex);
//  		Matcher matcher = pattern.matcher(url);
//  		String tt = null;
//  		while (matcher.find()) {
//  			tt = matcher.group();
//  			s =tt;
//  		}
//  		if(tt==null){
//  			s ="http://image.apk.gfan.com";
//  		}
//  		return s;
//	  }
//
//	  public static String getIP() throws SocketException{
//			Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
//			InetAddress ip = null;
//			while (allNetInterfaces.hasMoreElements()) {
//				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces
//						.nextElement();
//				Enumeration addresses = netInterface.getInetAddresses();
//				while (addresses.hasMoreElements()) {
//					ip = (InetAddress) addresses.nextElement();
//					if (ip != null && ip instanceof Inet4Address) {
//						return ip.getHostAddress();
//					}
//				}
//			}
//			return "";
//		}
//
//
	  public static void main(String[] args) throws IOException {
//		App app = new App();
//		app.setAppId(146763);
//		app.setIconUrl("http://cdn2.image.apk.gfan.com/asdf/PImages/2013/3/8/ldpi_511008_7f98e704-86c2-45c0-b5da-eae249bcde56_icon.png");
//		downloadIcon(app);

		/*Photo photo = new Photo();
		photo.setPhotoId(1065043);
		photo.setAppId(146999);
		photo.setOriginalUrl("http://cdn2.image.apk.gfan.com/asdf/PImages/2013/3/11/512307_203fa060b-9466-49df-a9d2-395399e918b2.png");*/
		//downloadPhoto(photo);
	}
}

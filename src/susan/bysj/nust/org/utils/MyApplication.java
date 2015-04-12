package susan.bysj.nust.org.utils;

import net.tsz.afinal.FinalDb;
import android.app.Application;

public class MyApplication extends Application
{
	private FinalDb finalDb;// 数据库全局变量

	private AsyncImageLoader asyncImageLoader;
	
	private String serverUrl = "http://localhost:8080/updateUrl";

	public FinalDb getFinalDb()
	{
		return finalDb;
	}

	public void setFinalDb(FinalDb finalDb)
	{
		this.finalDb = finalDb;
	}

	public String getServerUrl()
	{
		return serverUrl;
	}

	public void setServerUrl(String serverUrl)
	{
		this.serverUrl = serverUrl;
	}

	public AsyncImageLoader getAsyncImageLoader()
    {
	    return asyncImageLoader;
    }

	public void setAsyncImageLoader(AsyncImageLoader asyncImageLoader)
    {
	    this.asyncImageLoader = asyncImageLoader;
    }

}

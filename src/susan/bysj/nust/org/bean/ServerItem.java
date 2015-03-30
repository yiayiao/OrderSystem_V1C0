package susan.bysj.nust.org.bean;

public class ServerItem implements IGetVersionAndId
{
	private int serverId;

	private String version;

	public ServerItem(int serverId, String version)
	{
		this.serverId = serverId;
		this.version = version;
	}

	public int getServerId()
	{
		return serverId;
	}

	public void setServerId(int serverId)
	{
		this.serverId = serverId;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

}

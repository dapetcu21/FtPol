package grepit.ftpol;

public class FuteClientThread extends Thread
{
	private String serverName=null;
	private int serverPort;
	
	public FuteClientThread(String server, int port)
	{super();
		serverName=server;
		serverPort=port;
	}
	
	public void run()
	{
		FuteClient client=new FuteClient();
		client.setHost(serverName);
		client.setPort(serverPort);
	}
}

package grepit.ftpol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class FuteClient {
	
	private DatagramChannel socket = null;
	private boolean running=false;
	private String hostString;
	private int port;
	
	public void setHost(String host)
	{hostString=host;}
	public String getHost()
	{return hostString;}
	
	public void setPort(int port)
	{ this.port=port; }
	public int getPort()
	{ return port; }
	
	public void start() throws Exception{
		stop();
		InetSocketAddress addr=new InetSocketAddress(hostString,port);
		socket = DatagramChannel.open();
		socket.connect(addr);
		running=true;
	}
	public void stop()
	{
		if(!running)return;
		running=false;
		try
		{
			socket.close();
		}
		catch(IOException e)
		{
			
		}
		socket=null;
	}
	public void send(byte protocol,byte[] bu,int size) throws Exception
	{
		byte[] buff=new byte[size+1];
		buff[0]=protocol;
		for(int i=1;i<=size;i++)
			buff[i]=bu[i-1];
			
		ByteBuffer buffer=ByteBuffer.wrap(buff, 0, size+1);
		socket.write(buffer);
	}
}

package grepit.ftpol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.LinkedList;
import java.util.LinkedList;
import java.util.ListIterator;

public class MTftpolClient {
	
	private DatagramChannel socket = null;
	private boolean running=false;
	private String hostString;
	private String portString;
	
	private void writeToBuffer32(byte[] d, int o, int n)
	{
		d[0+o] = (byte) ((n & 0xFF000000L) >> 24);
		d[1+o] = (byte) ((n & 0x00FF0000L) >> 16);
		d[2+o] = (byte) ((n & 0x0000FF00L) >> 8);
		d[3+o] = (byte) ( n & 0x000000FFL);
	}
	
	private void writeToBuffer64(byte[] d, int o, long n)
	{

		d[0+o] = (byte) ((n & 0xFF00000000000000L) >> 56);
		d[1+o] = (byte) ((n & 0x00FF000000000000L) >> 48);
		d[2+o] = (byte) ((n & 0x0000FF0000000000L) >> 40);
		d[3+o] = (byte) ((n & 0x000000FF00000000L) >> 32);
		d[4+o] = (byte) ((n & 0x00000000FF000000L) >> 24);
		d[5+o] = (byte) ((n & 0x0000000000FF0000L) >> 16);
		d[6+o] = (byte) ((n & 0x000000000000FF00L) >> 8);
		d[7+o] = (byte) ( n & 0x00000000000000FFL);
	}
	
	public void start() throws Exception{
		stop();
		int port=Integer.parseInt(portString);
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

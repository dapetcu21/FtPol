package grepit.ftpol;


import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;

public class FUTEftpol_server {
	public final static int PORT = 7777;
	
	public static void main(String[] args) throws IOException {
		int port = 7777;
		
		
		ByteBuffer in = ByteBuffer.allocate(1024);
		ByteBuffer out = ByteBuffer.allocate(1024);
		
		out.order(ByteOrder.BIG_ENDIAN);
		
		SocketAddress srvaddr = new InetSocketAddress(port);
	    DatagramChannel dgramchannel = DatagramChannel.open();
	    DatagramSocket socket = dgramchannel.socket();
	    socket.bind(srvaddr);
	    System.err.println("Debug :: Bound to: " + srvaddr);
	    
	    while (true) {
	    	try{
	    		in.clear();
	    		SocketAddress client = dgramchannel.receive(in);
	    		System.err.println("Debug :: Client connected: " +client);
	    		out.clear();
	    		 
	    		
	    		
	    		
	    	}catch (Exception ex) {
	        System.err.println("Debug:: Exception occured: " +ex);
	        
	        
	    }
	}

}
}
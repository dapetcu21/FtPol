package grepit.ftpol;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;

public class FtPolActivity extends Activity {
	
	private RootView view;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new RootView(this, null);
        setContentView(view);
       //srv_init sv = new srv_init();
       // sv.start();
    }
    

	public class srv_init extends Thread{
		
		public void run(){
			try {
				FuteServer.listen(null);
			} catch (IOException e) {
				System.err.println("Debug :: " + e.toString());
			}
			
		}
	}
}

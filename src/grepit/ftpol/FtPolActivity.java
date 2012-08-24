package grepit.ftpol;

import android.app.Activity;
import android.os.Bundle;

public class FtPolActivity extends Activity {
	
	private RootView view;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new RootView(this, null);
        setContentView(view);
        
    }
}
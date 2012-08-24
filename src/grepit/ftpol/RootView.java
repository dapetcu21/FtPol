package grepit.ftpol;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;


public class RootView extends GLSurfaceView {

	private FuteRenderer _renderer;
	
	public RootView(Context context, AttributeSet attrs) {
		super(context, attrs);
		_renderer = new FuteRenderer();
		setRenderer(_renderer);
		
		
	}
	
	
}

package grepit.ftpol;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class RootView extends GLSurfaceView {

	private FuteRenderer _renderer;
	
	public RootView(Context context, AttributeSet attrs) {
		super(context, attrs);
		_renderer = new FuteRenderer();
		_renderer.setRootView(this);
		setRenderer(_renderer);
		
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent evt)
	{
    	int state;
        int pointer = evt.getActionIndex();
        
        switch(evt.getActionMasked())
        {
        case MotionEvent.ACTION_DOWN:
        case MotionEvent.ACTION_POINTER_DOWN:
            	state = MotionEvent.ACTION_DOWN;
        	break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_POINTER_UP:
        case MotionEvent.ACTION_OUTSIDE:
            	state = MotionEvent.ACTION_UP;
        	break;
        case MotionEvent.ACTION_MOVE:
        	state = MotionEvent.ACTION_MOVE;
        	final int pc = evt.getPointerCount();
        	for (int p=0; p<pc; p++)
        		_renderer.onTouch(state, evt.getPointerId(p), evt.getX(p), evt.getY(p));
        	return true;
        case MotionEvent.ACTION_CANCEL:
        	state = MotionEvent.ACTION_CANCEL;
        	break;
        default:
        	return false;
        }
        _renderer.onTouch(state, pointer, evt.getX(pointer), evt.getY(pointer));
		return true;
	}
}

package grepit.ftpol;

import java.util.LinkedList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;
import android.view.MotionEvent;

public class FuteRenderer implements GLSurfaceView.Renderer {

	FuteView v;
	GL10 context;
	RootView root;
	int _width;
	int _height;
	void setRootView(RootView r) { root = r; }
	GL10 getContext() { return context; }
	

	public FuteNavigation vc;
	long tm;
	
	public void entryPoint(rect r)
	{
		r.origin.x = r.origin.y = 0;
		FuteViewController vcc = new FuteGameController(this, r);
		vc.pushViewController(vcc);
	}
	
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		
		_width = width;
		_height = height;
		
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		float ar = (float)width/height;
		gl.glScalef(1/ar, 1, 1);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		rect r = new rect(-ar, -1, 2*ar, 2);
		vc.setFrame(r);
		v = vc.getView();
		v.setBackgroundColor(new vec4(1, 1, 1, 1));
		entryPoint(r);
	}
	
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		context = gl;
		
		float ar = 4.0f/3.0f;
		vc = new FuteNavigation(this, new rect(-ar, -1, 2*ar, 2));
				
		gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		gl.glEnable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_DEPTH_TEST);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		tm = System.nanoTime();
		events = new LinkedList<FuteEvent>();
		touches = new FuteView[30];
		origs = new vec2[30];
	}

	private List<FuteEvent> events;
	private FuteView touches[];
	private vec2 origs[];
	
	void processEvent(FuteEvent evt)
	{
		evt.x -= _width/2;
		evt.y -= _height/2;
		evt.x /= _height/2;
		evt.y /= -_height/2;
		
		if (evt.type == MotionEvent.ACTION_DOWN)
		{
			evt.v = v.eventDepthFirst(evt, new vec2(0,0));
			origs[evt.index] = evt.orig;
			touches[evt.index] = evt.v; 
		} else {
			evt.v = touches[evt.index];
			evt.orig = origs[evt.index];
			if (evt.v != null)
			{
				switch (evt.type)
				{
				case MotionEvent.ACTION_MOVE:
					evt.v.onTouchMoved(evt);
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					touches[evt.index] = null; 
					evt.v.onTouchUp(evt);
					break;
				}
			}
		}
	}
	
	public void onTouch(int state, int pointer, float x, float y) {
		FuteEvent e = new FuteEvent();
		e.type = state;
		e.index = pointer;
		e.x = x;
		e.y = y;
		
		synchronized(events) {
			events.add(e);
		}
	}
	
	public void onDrawFrame(GL10 gl) {
		synchronized(events)
		{
			for (FuteEvent evt : events)
				processEvent(evt);
			events.clear();
		}
		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		v.render(gl);
		
		long t = System.nanoTime();
		vc.updateScene((float)(t- tm)/1000000000.0f);
		tm = t;
	}
	
	private boolean verArray = false;
	
	public void setVertexArray(GL10 gl, boolean b) {
		if (b == verArray) return;
		verArray = b;
		if (b)
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		else
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	private boolean txcArray = false;
	
	public void setTextureCoordArray(GL10 gl, boolean b) {
		if (b == txcArray) return;
		txcArray = b;
		if (b)
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		else
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}
	
	private boolean clrArray = false;
	
	public void setColorArray(GL10 gl, boolean b) {
		if (b == clrArray) return;
		clrArray = b;
		if (b)
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		else
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
	
}

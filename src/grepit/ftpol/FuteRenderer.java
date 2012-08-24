package grepit.ftpol;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

public class FuteRenderer implements GLSurfaceView.Renderer {

	FuteView v;
	GL10 context;
	RootView root;
	void setRootView(RootView r) { root = r; }
	GL10 getContext() { return context; }
	
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		System.out.println("changed futut");
		
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		float ar = (float)width/height;
		gl.glScalef(1/ar, 1, 1);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		
		v.setFrame(new rect(-ar, -1, 2*ar, 2*ar));
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		System.out.println("create futut");
		context = gl;
		
		v = new FuteView(this);
		v.setBackgroundColor(new vec4(1, 0, 0, 1));
		FuteImage img = new FuteImage(this, root.getResources(), R.drawable.ic_launcher);
		FuteImageView iv = new FuteImageView(this);
		//iv.setImage(img);
		iv.setFrame(new rect(0.5f, 0.5f, 1, 1));
		iv.setBackgroundColor(new vec4(0, 0, 1, 1));
		v.addChild(iv);
		
		gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		gl.glEnable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_DEPTH_TEST);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	}

	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		v.render(gl);
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

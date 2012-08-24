package grepit.ftpol;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

public class FuteRenderer implements GLSurfaceView.Renderer {

	
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		System.out.println("changed futut");
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glScalef((float)height/width, 1, 1);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		
		gl.glClearColor(1, 0, 0, 1);
	}

	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		System.out.println("create futut");
	}

	
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}

	
}

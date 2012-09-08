package grepit.ftpol;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class FuteImageView extends FuteView {

	FuteImageView(FuteRenderer r) {
		super(r);
	}
	
	private FuteImage img = null;
	
	FuteImage image() { return img; }
	void setImage(FuteImage im) {
		img = im; 
	}
	
	vec4 _tint = null;
	vec4 tint() { return new vec4(_tint); }
	void setTint(vec4 v) { _tint = new vec4(v); }
	
	void draw(GL10 gl)
	{
		if (img == null) return;
		float b [] = {
				0, 0, 0, 	            		 0, 1,
				_frame.size.x, 0 , 0, 			 1, 1,
				0, _frame.size.y, 0, 			 0, 0,
				_frame.size.x, _frame.size.y, 0, 1, 0
		};
		
		ByteBuffer vbb = ByteBuffer.allocateDirect(b.length * 4);
	    vbb.order(ByteOrder.nativeOrder());
	    FloatBuffer fb = vbb.asFloatBuffer();
	    fb.put(b);
	    fb.position(0);
		
	    _renderer.setVertexArray(gl, true);
	    _renderer.setTextureCoordArray(gl, true);
	    _renderer.setColorArray(gl, false);
	    if (_tint == null)
	    	gl.glColor4f(1, 1, 1, 1);
	    else
	    	gl.glColor4f(_tint.x, _tint.y, _tint.z, _tint.w);
	    gl.glVertexPointer(3, GL10.GL_FLOAT, 5*4, fb);
	    fb.position(3);
	    gl.glTexCoordPointer(2, GL10.GL_FLOAT, 5*4, fb);
	    
	    gl.glBindTexture(GL10.GL_TEXTURE_2D, img.getID());
	    gl.glEnable(GL10.GL_TEXTURE_2D);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	    gl.glDisable(GL10.GL_TEXTURE_2D);
	}

}

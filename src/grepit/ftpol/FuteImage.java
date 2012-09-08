package grepit.ftpol;

import java.nio.ByteBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class FuteImage {
	FuteRenderer _renderer;
	
	int id;
	int _width;
	int _height;
	
	FuteImage(FuteRenderer r, Resources res, int rid)
	{
		_renderer = r;
		
		Bitmap b = BitmapFactory.decodeResource(res, rid);
		GL10 gl = _renderer.getContext();
		
		int tx[] = new int[1];
		gl.glGenTextures(1, tx, 0);
		id = tx[0];

		gl.glBindTexture(GL10.GL_TEXTURE_2D, id);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
	    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
	    GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, b, 0);	
		gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
		
		_width = b.getWidth();
		_height = b.getHeight();
	}
	
	int getID() { return id; }
	int width() { return _width; }
	int height() { return _height; }
	
};

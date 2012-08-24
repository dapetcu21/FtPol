package grepit.ftpol;

import java.nio.ByteBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FuteImage {
	FuteRenderer _renderer;
	
	int refcount = 1; //pentru că java n-are destructors;
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

		gl.glTexParameterx(id, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterx(id, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterx(id, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterx(id, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		
		_width = b.getWidth();
		_height = b.getHeight();
		
		ByteBuffer bb = ByteBuffer.allocateDirect(_width*_height*4);
		b.copyPixelsToBuffer(bb);
		
		gl.glTexImage2D(id, 0, GL10.GL_RGBA, _width, _height, 0, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, bb);
	}
	
	int getID() { return id; }
	int width() { return _width; }
	int height() { return _height; }
	
	void release() { 
		refcount--;
		if (refcount <= 0)
		{
			GL10 gl = _renderer.getContext();
			int tx[] = { id };
			gl.glDeleteTextures(1, tx, 0);
		}
	}
	
	void retain() { 
		refcount++;
	}
};

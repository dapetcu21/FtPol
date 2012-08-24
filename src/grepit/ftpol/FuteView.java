package grepit.ftpol;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;


public class FuteView {
	
	FuteRenderer _renderer;
	
	ArrayList<FuteView> _children;
	FuteView _parent = null;
	
	rect _frame;
	vec4 bgclr;
	
	rect frame() { return new rect(_frame); }
	rect bounds() { return new rect(0, 0, _frame.size.x, _frame.size.y); }
	
	vec4 backgroundColor() { return new vec4(bgclr); }
	void setBackgroundColor(vec4 bg) { bgclr = new vec4(bg); }
	
	
	void setFrame(rect f)
	{
		_frame = new rect(f);
	}
	
	FuteView(FuteRenderer r)
	{
		_renderer = r;
		_children = new ArrayList<FuteView>();
	}
	
	void removeChild(FuteView v)
	{
		_children.remove(v);
	}
	
	void addChild(FuteView v)
	{
		_children.add(v);
	}
	
	void removeFromParent()
	{
		_parent.removeChild(this);
	}
	
	void drawbg(GL10 gl)
	{
		if (bgclr != null)
		{
			float b [] = {
					0, _frame.origin.y, 0,
					_frame.size.x, 0 , 0,
					0, _frame.size.x, 0,
					_frame.size.x, _frame.size.x, 0
			};
			
			ByteBuffer vbb = ByteBuffer.allocateDirect(b.length * 4);
		    vbb.order(ByteOrder.nativeOrder());
		    FloatBuffer fb = vbb.asFloatBuffer();
		    fb.put(b);
		    fb.position(0);

		    _renderer.setVertexArray(gl, true);
		    _renderer.setTextureCoordArray(gl, false);
		    _renderer.setColorArray(gl, false);
		    gl.glColor4f(bgclr.x, bgclr.y, bgclr.z, bgclr.w);
		    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fb);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		}
	}
	
	void draw(GL10 gl)
	{
	}
	
	
	private void multMatrix(GL10 gl) {
		gl.glTranslatef(_frame.origin.x, _frame.origin.y, 0);
	}
	
	void renderChildren(GL10 gl)
	{
		for (FuteView v : _children)
		{
			v.render(gl);
		}
	}
	
	void render(GL10 gl)
	{
		gl.glPushMatrix();
		multMatrix(gl);
		drawbg(gl);
		draw(gl);
		renderChildren(gl);
		gl.glPopMatrix();
	}
}

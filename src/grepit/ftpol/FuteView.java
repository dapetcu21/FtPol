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
		v._parent = this;
	}
	
	void removeFromParent()
	{
		if (_parent != null)
			_parent.removeChild(this);
	}
	
	void drawbg(GL10 gl)
	{
		if (bgclr != null)
		{
			float b [] = {
					0, 0, 0,
					_frame.size.x, 0 , 0,
					0, _frame.size.y, 0,
					_frame.size.x, _frame.size.y, 0
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
	
	private float sX = 1.0f;
	private float sY = 1.0f;
	
	public float scaleX() { return sX; }
	public float scaleY() { return sY; }
	public void setScaleX(float v) { sX = v; }
	public void setScaleY(float v) { sY = v; }
	
	private void multMatrix(GL10 gl) {
		gl.glTranslatef(_frame.origin.x, _frame.origin.y, 0);
		gl.glTranslatef(_frame.size.x/2, _frame.size.y/2, 0);
		gl.glScalef(sX, sY, 1);
		gl.glTranslatef(-_frame.size.x/2, -_frame.size.y/2, 0);
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
	
	private boolean uin = false;
	public boolean userInput() { return uin; }
	public void setUserInput(boolean b) { uin = b; }
	
	public FuteView eventDepthFirst(FuteEvent evt, vec2 off) {
		if (!uin) return null;
		
		vec2 o = new vec2(off.x + _frame.origin.x, off.y + _frame.origin.y);
		
		for (FuteView v : _children)
		{
			FuteView r = v.eventDepthFirst(evt, o);
			if (r != null)
				return r;
		}
		
		if (	(evt.x <= o.x + _frame.size.x) &&
				(evt.y <= o.y + _frame.size.y) &&
				(evt.x >= o.x) &&
				(evt.y >= o.y))
		{
			evt.orig = o;
			if (onTouchDown(evt))
				return this;
		}
		return null;
	}
	
	public boolean onTouchDown(FuteEvent evt) {
		return false;
	}
	
	public void onTouchMoved(FuteEvent evt) {
	}
	public void onTouchUp(FuteEvent evt) {
	}
}

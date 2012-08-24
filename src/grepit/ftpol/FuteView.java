package grepit.ftpol;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;


public class FuteView {
	
	FuteRenderer _renderer;
	
	ArrayList<FuteView> _children;
	FuteView _parent = null;
	
	rect _frame;
	vec4 bgclr;
	
	rect frame() { return new rect(_frame); }
	rect bounds() { return new rect(0, 0, _frame.size.x, _frame.size.y); }
	
	vec4 backgroundColor() { return new vec4(bgclr); }
	void setBackgroundColor(vec4 bg) { bgclr = new vec4(bgclr); }
	
	
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
	
	void draw()
	{
		if (bgclr != null)
		{
			GL10 gl = _renderer.glContext();
			
			float b [] = {
					0, 0,
					1, 0,
					0, 1,
					1, 1
			};
			for (int i = 0; i < 4; i++)
			{
				b[i*2]   = _frame.origin.x + _frame.size.x * b[i*2];
				b[i*2+1] = _frame.origin.y + _frame.size.y * b[i*2+1];
			}
			
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glColor4f(bgclr.x, bgclr.y, bgclr.z, bgclr.w);
			gl.glVertexPointer(2, GL10.GL_FLOAT, 0, FloatBuffer.wrap(b));
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		}
	}
	
	void render()
	{
		GL10 gl = _renderer.glContext();
		gl.glPushMatrix();
		multMatrix(gl);
		draw();
		renderChildren();
		gl.glPopMatrix();
	}
	
	private void multMatrix(GL10 gl) {
		gl.glTranslatef(_frame.origin.x, _frame.origin.y, 0);
	}
	
	void renderChildren()
	{
		for (FuteView v : _children)
		{
			v.render();
		}
	}
}

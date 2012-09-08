package grepit.ftpol;

import javax.microedition.khronos.opengles.GL10;

public class FutePolitieView extends FuteImageView{

	FutePolitieView(FuteRenderer r) {
		super(r);
		time = 0;
	}
	
	FuteImageView gg, gr;
	float time;
	
	
	void update(float elapsed)
	{
		time += elapsed;
		
		float v = (float)Math.abs(Math.sin(time*3));
		gg.setTint(new vec4(1, 0, 0, v));
		v = (float)Math.abs(Math.cos(time*3));
		gr.setTint(new vec4(0, 0, 1, v));
	}
	
	@Override
	void draw(GL10 gl) {
		gl.glBlendFunc(GL10.GL_SRC_COLOR, GL10.GL_SRC_COLOR);
		super.draw(gl);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	}
}

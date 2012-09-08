package grepit.ftpol;

import java.util.Random;

public class FuteTestViewController extends FuteViewController {
	
	FuteTestViewController(FuteRenderer r, rect frame) {
		super(r, frame);
	}
	
	class PulaView extends FuteView 
	{
		PulaView(FuteRenderer r) {
			super(r);
		}


		public FuteTestViewController p;
		

		@Override
		public boolean onTouchDown(FuteEvent evt)
		{
			rect frm = frame();
			FuteTestViewController vc = new FuteTestViewController(p.renderer(), frame());
			p.navigation().pushViewController(vc);
			return true;
		}
	};

	@Override
	FuteView loadView(rect r)
	{
		PulaView v = new PulaView(renderer());
		v.setFrame(r);
		v.setBackgroundColor(new vec4((float)Math.random(), (float)Math.random(), (float)Math.random(), 1));
		v.setUserInput(true);
		v.p = this;
		return v;
	}

	@Override
	void updateScene(float elapsed) {
		
	}
}

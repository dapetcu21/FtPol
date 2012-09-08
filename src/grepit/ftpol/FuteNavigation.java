package grepit.ftpol;

public class FuteNavigation extends FuteViewController{

	FuteNavigation(FuteRenderer r, rect fr) {
		super(r, fr);
	}

	FuteViewController cvc = null, nvc = null;
	float meow;
	
	FuteView curtain;
	FuteView container;
	
	@Override
	FuteView loadView(rect frame)
	{
		FuteRenderer r = renderer();
		FuteView v = new FuteView(r);
		v.setFrame(frame);
		v.setUserInput(true);
		rect frm = new rect(frame);
		frm.origin.x = frm.origin.y = 0;
		container = new FuteView(r);
		container.setFrame(frm);
		container.setUserInput(true);
		v.addChild(container);
		curtain = new FuteView(r);
		curtain.setFrame(frm);
		v.addChild(curtain);
		return v;
	}

	@Override
	void updateScene(float elapsed) {
		if (cvc != null)
		{
			if (meow>0)
			{
				meow -= elapsed;
				if (meow < 0)
				{
					meow = 0;
					System.out.println("ham");
				}
				if ((meow < 0.5) && (nvc != null))
				{
					System.out.println("meow");
					meow = 0.5f;
					if (cvc != null)
						cvc.getView().removeFromParent();
					container.addChild(nvc.getView());
					cvc = nvc;
					nvc = null;
				}
				float opac = (float)Math.sin(meow * Math.PI);
				curtain.setBackgroundColor(new vec4(0.0f, 0.0f, 0.0f, opac));
			} 
			cvc.updateScene(elapsed);
		}
	}
	
	void pushViewController(FuteViewController vc)
	{
		vc.setNavigation(this);
		if (cvc == null)
		{
			cvc = vc;
			container.addChild(cvc.getView());
		}
		else
		{
			nvc = vc;
			meow = 1.0f;
		}
	}
}

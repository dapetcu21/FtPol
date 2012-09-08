package grepit.ftpol;

public class FuteTouchCapture extends FuteImageView {
	FuteTouchCapture(FuteRenderer r) {
		super(r);
	}

	FuteGameController controller;
	
	float p1 = 0.0f;
	float p2 = 0.0f;
	
	@Override
	public boolean onTouchDown(FuteEvent evt) {
		onTouchEvent(evt);
		return true;
	}
	
	@Override
	public void onTouchUp(FuteEvent evt) {
		onTouchEvent(evt);
	}
	
	@Override
	public void onTouchMoved(FuteEvent evt) {
		onTouchEvent(evt);
	}
	
	void onTouchEvent(FuteEvent evt)
	{
		float x = evt.x - frame().size.x/2 - evt.orig.x;
		float y = evt.y - frame().size.y/2 - evt.orig.y;
		
		if (x<0)
			p1 = y;
		else
			p2 = y;
		
	}
}

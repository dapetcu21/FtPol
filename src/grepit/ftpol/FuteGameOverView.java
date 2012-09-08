package grepit.ftpol;

import android.R.bool;

public class FuteGameOverView extends FuteView {

	FuteGameOverView(FuteRenderer r) {
		super(r);
	}

	FuteGameOver vc;
	
	public boolean onTouchDown(FuteEvent evt)
	{
		vc.doPulaMea();
		return true;
	}
	
}

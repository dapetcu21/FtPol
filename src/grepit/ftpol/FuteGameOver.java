package grepit.ftpol;

public class FuteGameOver extends FuteViewController {

	FuteGameOver(FuteRenderer r, rect frame) {
		super(r, frame);
	}

	@Override
	FuteView loadView(rect frame) {
		FuteGameOverView v = new FuteGameOverView(_renderer);
		v.vc = this;
		v.setUserInput(true);
		v.setFrame(frame);
		v.setBackgroundColor(new vec4(0, 0, 0, 1));
		return v;
	}

	@Override
	void updateScene(float elapsed) {
	}

	public void doPulaMea() {
		FuteViewController vc = new FuteGameController(_renderer, getView().frame());
		navigation().pushViewController(vc);
	}

}

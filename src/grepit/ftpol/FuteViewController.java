package grepit.ftpol;

public abstract class FuteViewController {
	protected FuteRenderer _renderer;
	protected FuteView _view = null;
	protected rect _frame;
	protected FuteNavigation nav;
	
	void setNavigation(FuteNavigation n) { nav = n; }
	FuteNavigation navigation() { return nav; }
	
	FuteViewController(FuteRenderer r, rect frame)
	{
		_renderer = r;
		_frame = new rect(frame);
	}
	
	void setFrame(rect v) { 
		_frame = new rect(v); 
		if (_view != null)
			_view.setFrame(v);
	}
	
	FuteRenderer renderer() { return _renderer; }
	
	abstract FuteView loadView(rect frame);
	
	FuteView getView()
	{
		if (_view == null)
			_view = loadView(_frame);
		return _view;
	}
	
	abstract void updateScene(float elapsed);
}

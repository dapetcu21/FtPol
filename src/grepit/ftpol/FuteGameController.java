package grepit.ftpol;

import java.util.LinkedList;

public class FuteGameController extends FuteViewController {

	FuteGameController(FuteRenderer r, rect frame) {
		super(r, frame);
		
	}

	FuteImageView p1,p2,bll;
	float gameTime;
	vec2 ball;
	vec2 ballv;
	float p1v, p2v;
	FuteTouchCapture capture;
	
	LinkedList<FutePolitieView> politai;
	
	float gen = 0;
	
	void reset()
	{
		gameTime = -3;
		ball = new vec2(0, 0);
		double x = Math.random()-0.5;
		float angle = (float)((Math.random()-0.5) * Math.PI / 5) * (float)(x / Math.abs(x)) + (float)Math.PI/2;
		//float angle = (float)Math.PI/2;
		ballv = new vec2((float)Math.sin(angle), (float)Math.cos(angle));
		p1v = p2v = 0;
		updateViews();
		if (politai != null)
			for (FutePolitieView v : politai)
				v.removeFromParent();
		politai = new LinkedList<FutePolitieView>();
		gen = 2.0f;
	}
	
	private final float polw = 0.15f;
	private final float polh = polw * 153.0f / 69.0f;
	private final float giry = 0.17f;
	private final float girr = 0.2f;
	private final float girgx = polw * 0.3f;
	private final float girrx = polw * 0.7f;
	

	FuteImage polimg;
	FuteImage gimg;
	
	FutePolitieView createPolitieView()
	{
		FuteRenderer r = renderer();
		
		FutePolitieView pol = new FutePolitieView(r);
		FuteImageView gr = new FuteImageView(r);
		gr.setFrame(new rect(girrx - girr, giry - girr, girr*2, girr*2));
		pol.addChild(gr);
		FuteImageView gg = new FuteImageView(r);
		gg.setFrame(new rect(girgx - girr, giry - girr, girr*2, girr*2));
		pol.addChild(gg);
		pol.gr = gr;
		pol.gg = gg;
		pol.setFrame(new rect(-border * 0.7f + border * 1.4f * (float)Math.random() - polw/2,
				1.0f, polw, polh
				));
		pol.setImage(polimg);
		gg.setImage(gimg);
		gr.setImage(gimg);
		
		
		return pol;
	}
	
	FuteView container;
	
	@Override
	FuteView loadView(rect frame) {

		FuteRenderer r = renderer();
		
		polimg = new FuteImage(r, r.root.getResources(), R.drawable.policecar);
		gimg = new FuteImage(r, r.root.getResources(), R.drawable.gradient);
		
		FuteImage img = new FuteImage(r, r.root.getResources(), R.drawable.bg);
		FuteImage pimg = new FuteImage(r, r.root.getResources(), R.drawable.paddle);
		FuteImage pimg2 = new FuteImage(r, r.root.getResources(), R.drawable.paddle2);
		FuteImage bimg = new FuteImage(r, r.root.getResources(), R.drawable.ball);
		FuteTouchCapture iv = capture = new FuteTouchCapture(r);
		iv.setUserInput(true);
		container = new FuteView(r);
		iv.setImage(img);
		iv.setFrame(frame);
		container.setFrame(new rect(frame.size.x/2, frame.size.y/2, frame.size.x, frame.size.y));
		iv.addChild(container);
		
		p1 = new FuteImageView(r);
		p2 = new FuteImageView(r);
		bll = new FuteImageView(r);
		
		p1.setImage(pimg);
		p2.setImage(pimg2);
		bll.setImage(bimg);
		container.addChild(p1);
		container.addChild(p2);
		container.addChild(bll);
		
		reset();
		
		return iv;
	}
	

	private final float border = 1.2f;
	private final float paddle = 0.5f;
	private final float paddlew = paddle * 266.0f/500.0f;
	private final float ballw = 0.05f;
	private final float tilt_w = 2.0f;
	private final float min_speed = 1.0f;
	private final float speed = 1.0f;
	
	void updateViews()
	{
		bll.setFrame(new rect(ball.x - ballw, ball.y - ballw, ballw*2, ballw*2));
		p1.setFrame(new rect(-border-ballw-paddlew, p1v - paddle/2, paddlew, paddle));
		p2.setFrame(new rect(border+ballw, p2v - paddle/2, paddlew, paddle));
	}

	boolean lost = false;
	
	void lose()
	{
		if (lost) return;
		lost = true;
		FuteViewController vc = new FuteGameOver(_renderer, getView().frame());
		navigation().pushViewController(vc);
	}
	
	@Override
	void updateScene(float elapsed) {
		gameTime += elapsed;
		
		p1v = capture.p1;
		p2v = capture.p2;
		
		if (gameTime>=0)
		{
			if (gameTime < elapsed)
				elapsed = gameTime;
			
			float nx, ny;
			
			nx = ball.x + elapsed * ballv.x;
			ny = ball.y + elapsed * ballv.y;
			
			vec3 rb = new vec3(nx, ny, 0);
			
			if (reflectY(ball, rb, -1, -border, border))
				ballv.y = -ballv.y;
			else
			if (reflectY(ball, rb, 1, -border, border))
				ballv.y = -ballv.y;
			
			if (reflectX(ball, rb, -border, p1v-paddle/2, p1v+paddle/2))
			{
				ballv.x = -ballv.x;
				ballv.y += (rb.z - p1v) * tilt_w / paddle;
				if (ballv.x != 0 && ballv.y != 0) 
				{
					float vel = min_speed + Math.abs(rb.z - p1v) / paddle * speed;
					float len = (float)Math.sqrt((double)(ballv.x * ballv.x + ballv.y * ballv.y));
					ballv.x *= vel / len;
					ballv.y *= vel / len;
				}
			}
			if (reflectX(ball, rb, border, p2v-paddle/2, p2v+paddle/2))
			{
				ballv.x = -ballv.x;
				ballv.y += (rb.z - p2v) * tilt_w;
				if (ballv.x != 0 && ballv.y != 0) 
				{
					float vel = min_speed + Math.abs(rb.z - p2v) / paddle * speed;
					float len = (float)Math.sqrt((double)(ballv.x * ballv.x + ballv.y * ballv.y));
					ballv.x *= vel / len;
					ballv.y *= vel / len;
				}
			}
			
			gen -= elapsed;
			if (gen<=0)
			{
				gen = (float)((1.0 + Math.random()) * 10.0 / Math.sqrt((double)gameTime)) + 0.5f;
				FutePolitieView pv = createPolitieView();
				politai.add(pv);
				container.addChild(pv);
			}
			
			LinkedList<FutePolitieView> l = new LinkedList<FutePolitieView>();
			for (FutePolitieView p : politai)
			{
				rect f = p.frame();
				f.origin.y -= elapsed * 0.3f;
				p.setFrame(f);
				p.update(elapsed);
				
				boolean remove = false;
				if (reflectX(ball, rb, f.origin.x - ballw,				f.origin.y - ballw, f.origin.y + f.size.y + ballw) ||
					reflectX(ball, rb, f.origin.x + f.size.x + ballw, 	f.origin.y - ballw, f.origin.y + f.size.y + ballw))
				{
					ballv.x = -ballv.x;
					remove = true;
				}
				
				if (reflectY(ball, rb, f.origin.y + f.size.y + ballw,	f.origin.x - ballw, f.origin.x + f.size.x + ballw) ||
					reflectY(ball, rb, f.origin.y - ballw,				f.origin.x - ballw, f.origin.x + f.size.x + ballw))
				{
					ballv.y = -ballv.y;
					remove = true;
				}
				
				if (remove || (f.origin.y + f.size.y < -1.0f))
					p.removeFromParent();
				else 
					l.add(p);
			}
			politai = l;
			
			ball.x = rb.x;
			ball.y = rb.y;
		}
		
		updateViews();
		
		if ((ball.x < -container.frame().size.x) || (ball.x > container.frame().size.x))
			lose();
	}

	private boolean reflectY(vec2 v, vec3 rv, float b,
			float b1, float b2) {
			float r = (b - rv.y) * (v.x - rv.x) / (v.y - rv.y) + rv.x;
			if ((((v.y<=b) && (b<=rv.y)) || ((rv.y<=b) && (b<=v.y))) && ((b1<=r) && (r<=b2)))
			{
				rv.y = b * 2 - rv.y;
				rv.z = r;
				return true;
			}
			rv.z = r;
			return false;
	}
	
	private boolean reflectX(vec2 v, vec3 rv, float b,
			float b1, float b2) {
			float r = (b - rv.x) * (v.y - rv.y) / (v.x - rv.x) + rv.y;
			if ((((v.x<=b) && (b<=rv.x)) || ((rv.x<=b) && (b<=v.x))) && ((b1<=r) && (r<=b2)))
			{
				rv.x = b * 2 - rv.x;
				rv.z = r;
				return true;
			}
			rv.z = r;
			return false;
	}
}

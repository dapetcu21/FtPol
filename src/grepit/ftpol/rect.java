package grepit.ftpol;

public class rect {
	public vec2 origin, size;
	
	rect(vec2 o, vec2 sz) { 
		origin = new vec2(o); 
		size = new vec2(sz); 
	}
	rect(rect o) { 
		origin = new vec2(o.origin); 
		size = new vec2(o.size); 
	}
	
	rect(float x, float y, float w, float h) { 
		origin = new vec2(x, y); 
		size = new vec2(w, h);
	}
}

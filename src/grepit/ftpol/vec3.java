package grepit.ftpol;

public class vec3 {
	public float x, y, z;
	
	public vec3(float _x, float _y, float _z) { x = _x; y = _y; z = _z; }
	public vec3(vec4 o) { x = o.x; y = o.y; z = o.z; }
}


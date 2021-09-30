package net.mgsx.dl18.springs;

public class SpringEdge {

	public SpringNode nodeA;
	public SpringNode nodeB;
	
	public float originalDistance;
	public float distance;
	public void reset() {
		distance = originalDistance;
	}
}

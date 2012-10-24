package util;


public class path{
	
	public String path;
	public int comparisons;
	public int pathManeuvers;
	public double pathLength;
	
	public path(String s, int x, int y, double d){
		path = s;
		comparisons = x;
		pathManeuvers = y;
		pathLength = d;
	}
	
	public path(){
		path = "";
		comparisons = 0;
		pathManeuvers = 0;
		pathLength = 0.0;
	}
	
	public String getPath(){
		return path;
	}
	
	public int getComparisons(){
		return comparisons;
	}
	
	public int getPathManeuvers(){
		return pathManeuvers;
	}
	
	public double pathLength(){
		return pathLength;
	}
}
	
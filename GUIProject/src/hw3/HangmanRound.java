//AndrewID: tzuyuh  Tzuyu Huang
package hw3;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class HangmanRound extends GameRound {
	private IntegerProperty hitcount=new SimpleIntegerProperty();
	private IntegerProperty missCount=new SimpleIntegerProperty();
		
	
	public int getHitCount() {
		
		
		return hitcount.get();}
	
	public void setHitCount(int hitCount) {
		this.hitcount.set(hitCount);
	}
	
	public IntegerProperty hitCountProperty() {
		return hitcount;

	}
	
	public int getMissCount() {
		return missCount.get();
		
	}
	
	public void setMissCount(int missCount) {
		this.missCount.set(missCount);
	}
	
	public IntegerProperty missCountProperty() {
		return missCount;
		
	}
	
}

//AndrewID: tzuyuh  Tzuyu Huang
package hw3;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Score {
	
	//initiate gameId, puzzleword, timestamp and score
	private IntegerProperty gameId=new SimpleIntegerProperty();
	private StringProperty puzzleWord=new SimpleStringProperty();
	private IntegerProperty timeStamp=new SimpleIntegerProperty();
	private FloatProperty score=new SimpleFloatProperty();
	
	public Score() {

		
	}
	public Score(int gamdId, String puzzleWord, int timeStamp, float score) {
		setGameId(gamdId);
		setpuzzleWord(puzzleWord);
		setTimeStamp(timeStamp);
		setScore(score);
	}
	public void setGameId(int gameId) {
		this.gameId.set(gameId);
		// TODO Auto-generated method stub
		
	}
	public void setpuzzleWord(String puzzleWord) {
		this.puzzleWord.set(puzzleWord);
		// TODO Auto-generated method stub
		
	}
	public void setScore(float score) {
		this.score.set(score);
		// TODO Auto-generated method stub
		
	}
	
	public void setTimeStamp(int timeStamp) {
		this.timeStamp.set(timeStamp);
		// TODO Auto-generated method stub
		
	}
	public int getGameId() {
		// TODO Auto-generated method stub
		return gameId.get();
	}
	
	public String getPuzzleWord() {
		// TODO Auto-generated method stub
		return puzzleWord.get();
	}
	
	public float getScore() {
		// TODO Auto-generated method stub
		return score.get();
	}
	
	public int getTimeStamp() {
		// TODO Auto-generated method stub
		return timeStamp.get();
	}
	
	public IntegerProperty timeStampProperty() {
		// TODO Auto-generated method stub
		return timeStamp;
	}
	
	public StringProperty puzzleWordProperty() {
		return puzzleWord;
	
		// TODO Auto-generated method stub
		
	}
	
	public FloatProperty scoreProperty() {
		return score;
	}
	
	public IntegerProperty gameIdProperty() {
		return gameId;
	}
}

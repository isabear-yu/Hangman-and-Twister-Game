package hw3;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestHangman {	
	static Hangman  hangman;
	String wordSourceName = "data\\wordsFile.txt";
	
	@BeforeEach
	public void setupTest() {
		hangman = new Hangman();
		hangman.hangmanRound = new HangmanRound();
		WordNerdModel.readWordsFile(wordSourceName);
		hangman.hangmanRound.setPuzzleWord("syllabus");
		hangman.hangmanRound.setClueWord("_y__a_u_");

	}
	
	@Test
	public void testWordsListLength() {
		assertEquals(20000, WordNerdModel.wordsFromFile.length);
	}
	
	@Test
	public void testCountDashes() {
		assertEquals ("Test count dashes", 5, hangman.countDashes(hangman.hangmanRound.getClueWord()));		
	}
	

	@Test
	public void testMakeClue() {
		assertEquals ("Test make clue", "_y__a_u_", hangman.hangmanRound.getClueWord());		
	}
	
	@Test
	public void testNextTryCorrectClue() {
		assertEquals(GameView.THUMBS_UP_INDEX, hangman.nextTry("b"));
	}
	
	
	
	@Test
	public void testNextTryWrongClue() {
		assertEquals(GameView.THUMBS_DOWN_INDEX, hangman.nextTry("e"));
	}

	@Test
	public void testHitCount() {
		hangman.nextTry("b");		//hit. Trial# 1
		hangman.nextTry("c");		//miss. Trial# 2
		hangman.nextTry("d");		//miss. Trial# 3
		assertEquals( 1, hangman.hangmanRound.getHitCount());
	}
	@Test
	public void testMissCount() {
		hangman.nextTry("b");		//hit. Trial# 1
		
		hangman.nextTry("c");		//miss. Trial# 2
		hangman.nextTry("d");		//miss. Trial# 3
		hangman.nextTry("s");		//miss. Trial# 2
		assertEquals( "sy__abus", hangman.hangmanRound.getClueWord());
	
		assertEquals( 2, hangman.hangmanRound.getMissCount());
		assertEquals( 2, hangman.hangmanRound.getHitCount());
		
		assertEquals( GameView.SMILEY_INDEX, hangman.nextTry("l"));
	}
}

//AndrewID: tzuyuh  Tzuyu Huang
package hw3;

import java.util.Random;



public class Hangman extends Game{
	static final int MIN_WORD_LENGTH = 5; //minimum length of puzzle word
	static final int MAX_WORD_LENGTH = 10; //maximum length of puzzle word
	static final int HANGMAN_TRIALS = 10;  // max number of trials in a game
	static final int HANGMAN_GAME_TIME = 30; // max time in seconds for one round of game
	
	HangmanRound hangmanRound;
	
	/** setupRound() is a replacement of findPuzzleWord() in HW1. 
	 * It returns a new HangmanRound instance with puzzleWord initialized randomly drawn from wordsFromFile.
	* The puzzleWord must be a word between HANGMAN_MIN_WORD_LENGTH and HANGMAN_MAX_WORD_LEGTH. 
	* Other properties of Hangmanround are also initialized here. 
	*/
	@Override
	HangmanRound setupRound() {
		//write your code here
		int wordIndex; 
		do {
			wordIndex=new Random().nextInt(WordNerdModel.wordsFromFile.length);
		}while(WordNerdModel.wordsFromFile[wordIndex].length()<MIN_WORD_LENGTH || WordNerdModel.wordsFromFile[wordIndex].length()>MAX_WORD_LENGTH );
		
		hangmanRound =new HangmanRound();
		String puzzle=WordNerdModel.wordsFromFile[wordIndex];
		hangmanRound.setPuzzleWord(puzzle);
		String clue=makeAClue(puzzle); 
		hangmanRound.setClueWord(clue);
		return hangmanRound;
	}
	
	
	/** Returns a clue that has at least half the number of letters in puzzleWord replaced with dashes.
	* The replacement should stop as soon as number of dashes equals or exceeds 50% of total word length. 
	* Note that repeating letters will need to be replaced together.
	* For example, in 'apple', if replacing p, then both 'p's need to be replaced to make it a--le */
	@Override
	String makeAClue(String puzzleWord) {
		//write your code here
		do{
			int index=new Random().nextInt(puzzleWord.length());
			puzzleWord=puzzleWord.replace(puzzleWord.charAt(index), '_'); //replace the char with '_'
		}while(countDashes(puzzleWord)<puzzleWord.length()/2);
		
		return puzzleWord;
	}

	/** countDashes() returns the number of dashes in a clue String */ 
	int countDashes(String word) {
		int count = 0;
		for (int i=0; i<word.length();i++) {
			if(word.charAt(i)=='_') {
				count++;
			}
		}
		//write your code here
		return count;
	}
	
	/** getScoreString() returns a formatted String with calculated score to be displayed after
	 * each trial in Hangman. See the handout and the video clips for specific format of the string. */
	@Override
	String getScoreString() {
		//write your code here
		if(hangmanRound.getMissCount()==0) {	
			int hit=hangmanRound.getHitCount();
			int miss=hangmanRound.getMissCount();
			String strDouble = String.format("%.2f", (float)hangmanRound.getHitCount());
			return "Hit: "+hit+" Miss: "+miss+" Score: "+strDouble;
		}
		else{
			int hit=hangmanRound.getHitCount();
			int miss=hangmanRound.getMissCount();
			String strDouble = String.format("%.2f", (float)hangmanRound.getHitCount()/hangmanRound.getMissCount());
			return "Hit: "+hit+" Miss: "+miss+" Score: "+strDouble;
		}
	}

	/** nextTry() takes next guess and updates hitCount, missCount, and clueWord in hangmanRound. 
	* Returns INDEX for one of the images defined in GameView (SMILEY_INDEX, THUMBS_UP_INDEX...etc. 
	* The key change from HW1 is that because the keyboardButtons will be disabled after the player clicks on them, 
	* there is no need to track the previous guesses made in userInputs*/
	@Override
	int nextTry(String guess) {  
		StringBuilder sb=new StringBuilder();
		int containInClue=hangmanRound.getClueWord().indexOf(guess); // check if the guess char is existing in the clue
		int containInPuzl=hangmanRound.getPuzzleWord().indexOf(guess); // check if the guess char is existing in the word 
		
		
		
			
		if ((containInClue== -1) && (containInPuzl!=-1)) {
			int hitcount;
			hitcount=hangmanRound.getHitCount()+1;
			hangmanRound.setHitCount(hitcount);
//			
//			//append the index of matching chars in Puzzleword to sb 
			for(int i=0; i<hangmanRound.getPuzzleWord().length();i++) {
				if(hangmanRound.getPuzzleWord().charAt(i)==guess.charAt(0)) {
					sb.append(i+"\n");
				}
			}	
//			


			
			//set the matching chars with guess
			String[] indexarray = sb.toString().split("\n");
			sb.setLength(0);
			StringBuilder clueChange=new StringBuilder(hangmanRound.getClueWord());
			for(int i=0; i<indexarray.length; i++) {
				int index=Integer.parseInt(indexarray[i]);
				clueChange.setCharAt(index,guess.charAt(0));	
			}
			
			
			hangmanRound.setClueWord(clueChange.toString());
			
			if(hangmanRound.getHitCount()+hangmanRound.getMissCount()==(HANGMAN_TRIALS)) {	
				return GameView.SADLY_INDEX;
			}
			
			//check if the clue word contains dash, if no, then return the CONGRATULATIONS_MESSAGE_INDEX
			if(hangmanRound.getClueWord().indexOf('_')==-1) {
				hangmanRound.setIsRoundComplete(true);
				return GameView.SMILEY_INDEX;
			}
			
			return GameView.THUMBS_UP_INDEX;
		}
		
		else if(containInPuzl==-1){
			int misscount;
			misscount=hangmanRound.getMissCount()+1;
			hangmanRound.setMissCount(misscount);
			if(hangmanRound.getHitCount()+hangmanRound.getMissCount()==(HANGMAN_TRIALS)) {	
				return GameView.SADLY_INDEX;
			}
			return GameView.THUMBS_DOWN_INDEX;
		}
		
		
		
		//write your code here
		return 0;
	}
}

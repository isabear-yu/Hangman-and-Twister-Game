//AndrewID: tzuyuh  Tzuyu Huang
package hw3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Twister {
	public static final int SOLUTION_LIST_COUNT=5;
	public static final int TWISTER_MAX_WORD_LENGTH=7;
	public static final int TWISTER_MIN_WORD_LENGTH=3;
	public static final int NEW_WORD_BUTTON_INTDEX=0;
	public static final int TWIST_BUTTON_INDEX=1;
	public static final int CLEAR_BUTTON_INDEX=2;
	public static final int SUBMIT_BUTTON_INDEX=3;
	public static final int CLUE_BUTTON_SIZE= 75;
	public static final int TWISTER_GAME_TIME=120;
	public static final int MIN_SOLUTION_WORDCOUNT=10;
	TwisterRound twisterRound;
	
	/*
	 * It returns a new TwisterRound instance with puzzleWord initialized randomly drawn from wordsFromFile.
	* The puzzleWord must be a word between TWISTER_MIN_WORD_LENGTH and TWISTER_MAX_WORD_LENGTH. 
	* Other properties of TwisterRound are also initialized here. 
	*/
	TwisterRound setupRound(){
		int wordIndex; 
		
		List<String> doableMin=new ArrayList<String>(); //list store the solution answer
		Boolean solution=true;
		
		
		//get the word from the file that is smaller than TWISTER_MAX_WORD_LENGTH and greater than TWISTER_MIN_WORD_LENGTH
		do {
			doableMin.clear();	
			do {
			wordIndex=new Random().nextInt(WordNerdModel.wordsFromFile.length);
			}while(WordNerdModel.wordsFromFile[wordIndex].length()<TWISTER_MIN_WORD_LENGTH || WordNerdModel.wordsFromFile[wordIndex].length()>TWISTER_MAX_WORD_LENGTH); 
			
			
			StringBuilder sb=new StringBuilder(WordNerdModel.wordsFromFile[wordIndex]);
			
			//if the sb has the char then replace it with '!', if not then break the for loop to continue searching for the next word
			for(int i=0; i<WordNerdModel.wordsFromFile.length;i++) {
				solution=true;
				for(int j=0; j<WordNerdModel.wordsFromFile[i].length();j++) {	
					if(WordNerdModel.wordsFromFile[i].length()>=TWISTER_MIN_WORD_LENGTH && sb.toString().contains(Character.toString(WordNerdModel.wordsFromFile[i].charAt(j)))) {
						sb.setCharAt(sb.indexOf(Character.toString(WordNerdModel.wordsFromFile[i].charAt(j))),'!');
						
					}
					else {
						solution=false;
						sb.setLength(0);
						sb.append(WordNerdModel.wordsFromFile[wordIndex]);
						break;
					}
					
				}
				if (solution == true) {
					doableMin.add(WordNerdModel.wordsFromFile[i]);
					sb.setLength(0);
					sb.append(WordNerdModel.wordsFromFile[wordIndex]);
				}
			}
			
			
		}while(doableMin.size()<MIN_SOLUTION_WORDCOUNT); //get the word from the file that have the number of solution is greater than MIN_SOLUTION_WORDCOUNT
		

		twisterRound =new TwisterRound();
		String puzzle=WordNerdModel.wordsFromFile[wordIndex];
		twisterRound.setPuzzleWord(puzzle);
		twisterRound.setSolutionWordsList(doableMin);
		
		
		//set all the solution into SolutionListsByWordLength
		for(String word:doableMin) {
			twisterRound.setSolutionListsByWordLength(word);
		}
		
		
		//create clue number
		String clue=makeAClue(puzzle); 
		twisterRound.setClueWord(clue);
		

		
		return twisterRound;
		}
	
	
	
	String makeAClue(String puzzleWord) {
		int index;
		StringBuilder sb=new StringBuilder();
		do {
		index=new Random().nextInt(puzzleWord.length());
		if(sb.indexOf(Integer.toString(index))==-1) {
			sb.append(index);
		}
		}while(sb.length()!=puzzleWord.length());
		
		String[] clue=sb.toString().split("");
		sb.setLength(0);
		
		String[] word=puzzleWord.split("");
		for(int i=0;i<clue.length;i++) {
			sb.append(word[Integer.parseInt(clue[i])]);
		}
		
		
		return sb.toString();
		
	}
	
	int nextTry(String guess) {
		int count=0;
		
		//count all the correct answer
		
		for(int i=0; i<twisterRound.getSolutionListsByWordLength().size();i++) {
			count+=twisterRound.getSubmittedListsByWordLength(i).size();
		}
		
		if(guess.length()<3){
			return GameView.THUMBS_DOWN_INDEX;
		}
		//if getSubmittedListsByWordLength already contains the word than return REPEAT_INDEX
		if(twisterRound.getSubmittedListsByWordLength(guess.length()-TWISTER_MIN_WORD_LENGTH).contains(guess)) {
			return GameView.REPEAT_INDEX;
		}
		
		// if the getSolutionWordsList has the number same with the getSubmittedListsByWordLength,  return SMILEY_INDEX 
		else if(twisterRound.getSolutionWordsList().contains(guess)) {
			if(twisterRound.getSolutionWordsList().size()==(count+1)) {
				return GameView.SMILEY_INDEX;
			}
			return GameView.THUMBS_UP_INDEX;
		}
			
		
		return GameView.THUMBS_DOWN_INDEX;
		

	}
	
	String getScoreString() {
		int total=twisterRound.getSolutionWordsList().size();
		int count=0;
		
		//count all the correct answer
		for(int i=0; i<twisterRound.getSolutionListsByWordLength().size();i++) {
			count+=twisterRound.getSubmittedListsByWordLength(i).size();
		}
		return "Twist to find "+ (total-count) +" of " +total +" words";
		
	}
	
}

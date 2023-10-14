//AndrewID: tzuyuh  Tzuyu Huang
package hw3;


import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class TwisterRound extends GameRound {
	private ObservableList<String> solutionWordsList;
	private ObservableList<ObservableList<String>> submittedListsByWordLength;
	private ObservableList<ObservableList<String>> solutionListsByWordLength;
	
	//initiate solutionWordsList, submittedListsByWordLength and solutionListsByWordLength
	TwisterRound() {
		solutionWordsList = FXCollections.observableArrayList();
		submittedListsByWordLength=FXCollections.observableArrayList();
		solutionListsByWordLength=FXCollections.observableArrayList();
		
		
	}
	public void setSolutionWordsList(List<String> solutionwordsList){	

		
		this.solutionWordsList.setAll(solutionwordsList);
		
		//initiate observableArrayList in solutionListsByWordLength and submittedListsByWordLength
		for(int i=0;i<Twister.SOLUTION_LIST_COUNT;i++) {
			solutionListsByWordLength.add(FXCollections.observableArrayList());
			submittedListsByWordLength.add(FXCollections.observableArrayList());
		}

	}
	
	public List<String> getSolutionWordsList(){
		return solutionWordsList;
	}
	
	public ObservableList<String> solutionWordsListProperty(){
		return solutionWordsList;
	
	}
	
	

	void setSubmittedListsByWordLength(String word) {
		
		
		submittedListsByWordLength.get(word.length()-Twister.TWISTER_MIN_WORD_LENGTH).add(word);
		
		
	}
	
	
	public ObservableList<String> getSubmittedListsByWordLength(int letterCount) {
		return submittedListsByWordLength.get(letterCount);
		}
	
	public ObservableList<ObservableList<String>>  getSubmittedListsByWordLength() {
		return submittedListsByWordLength;
		}
	
	public ObservableList<ObservableList<String>> submittedListsByWordLengthProperty() {
		return submittedListsByWordLength;
	}
	

	public void setSolutionListsByWordLength(String word) {	
		
		solutionListsByWordLength.get(word.length()-Twister.TWISTER_MIN_WORD_LENGTH).add(word);

	}
	
	public ObservableList<String>getSolutionListsByWordLength(int letterCount){
		return solutionListsByWordLength.get(letterCount);
		
		}


	public ObservableList<ObservableList<String>> getSolutionListsByWordLength(){
		return solutionListsByWordLength;
		
	}
	
	public ObservableList<ObservableList<String>>  solutionListsByWorldLengthProperty(){
		return solutionListsByWordLength;
		
	}
}
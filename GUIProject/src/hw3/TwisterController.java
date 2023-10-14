//AndrewID: tzuyuh  Tzuyu Huang
package hw3;


import java.util.Collections;

import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class TwisterController extends WordNerdController{
	TwisterView twisterView;
	Twister twister;
	StringBuilder clueIndex=new StringBuilder();
	ObservableList<String> a;
	
	/**startController() creates new Twister, TwisterView, invokes setupRound() to create a new twisterRound,
	 * refreshes the view for next round, and invokes setupBindings to bind the new
	 * TwisterRound properties with GUI components.
	 */
	public void startController() {
		twisterView = new TwisterView();
		twister= new Twister();
		twister.twisterRound=twister.setupRound();
		twisterView.refreshGameRoundView(twister.twisterRound);
		setupBindings();
		
		//set up the Score in wordScoreLabels
		for (int i = 0; i<Twister.SOLUTION_LIST_COUNT ; i++ ) {
			
			twisterView.wordScoreLabels[i].setText("0"+"/"+Integer.toString(twister.twisterRound.getSolutionListsByWordLength(i).size()));
		}
		
		for (int i = 0; i <twisterView.solutionListViews.length ; i++) {
			if(twister.twisterRound.getSolutionListsByWordLength(i).size()!=0) {	
				twisterView.bottomGrid.add(twisterView.solutionListViews[i], 1, i);
				twisterView.bottomGrid.add(twisterView.wordScoreLabels[i], 2, i);
				twisterView.bottomGrid.add(twisterView.wordLengthLabels[i], 0, i); 
			}
			
		}
	
		VBox lowerPanel = new VBox();
		lowerPanel.getChildren().add(twisterView.bottomGrid);
		lowerPanel.getChildren().add(WordNerd.exitButton);
		lowerPanel.setAlignment(Pos.CENTER);
		
		
		WordNerd.root.setTop(twisterView.topMessageText);
		WordNerd.root.setCenter(twisterView.topGrid);
		WordNerd.root.setBottom(lowerPanel);
		
        twisterView.topMessageText.setText(twister.getScoreString());
		twisterView.playButtons[0].setOnAction(new NewButtonHandler());
		
		
		twisterView.playButtons[1].setOnAction(new TwistButtonHandler());
		
		twisterView.playButtons[2].setOnAction(new ClearButtonHandler());

		
		twisterView.playButtons[3].setOnAction(new SubmitButtonHandler());
	
		
		
	}
	public void setupBindings() {
		
		twister.twisterRound.clueWordProperty().addListener((observable, oldValue, newValue) -> {
			for (int i = 0; i < twister.twisterRound.getClueWord().length(); i++) {
				twisterView.clueButtons[i].setText(String.format("%s", newValue.charAt(i)));
			}
		});

		
		
		//When timer runs out, set smiley to sadly, isRoundComplete to true
		GameView.wordTimer.timeline.setOnFinished(event -> { 
			twisterView.smileyButton.setGraphic(twisterView.smileyImageViews[GameView.SADLY_INDEX]);
			int count=0;
				//count all the correct answer
			for(int i=0; i<twister.twisterRound.getSolutionListsByWordLength().size();i++) {
				count+=twister.twisterRound.getSubmittedListsByWordLength(i).size();
			}
			String str="1"+" "+twister.twisterRound.getPuzzleWord()+" "+(Twister.TWISTER_GAME_TIME-Integer.parseInt(GameView.wordTimer.timerButton.getText()))+" "+Float.toString((float)count/twister.twisterRound.getSolutionWordsList().size())+"\n";
			wordNerdModel.writeScore(str);
//			Score twisterScore = new Score(1,twister.twisterRound.getPuzzleWord(),Integer.parseInt(GameView.wordTimer.timerButton.getText()),(float)count/twister.twisterRound.getSolutionWordsList().size()); 
			twister.twisterRound.setIsRoundComplete(true);

		});

		//Bind keyboardGrid's and clueGrid's disable property to hangmanRound's isRoundCompleteProperty.
		//This means that when round is complete, the two should be disabled.
		twisterView.clueGrid.disableProperty().bind(twister.twisterRound.isRoundCompleteProperty());
		for(int i=1; i<twisterView.playButtons.length;i++) {
		twisterView.playButtons[i].disableProperty().bind(twister.twisterRound.isRoundCompleteProperty());
		}
		
		for (int i = 0; i < twisterView.answerButtons.length; i++) {
			twisterView.answerButtons[i].setOnAction(new AnswerButtonHandler() );
			
		}

		for (int i = 0; i < twisterView.clueButtons.length; i++) {
			twisterView.clueButtons[i].setOnAction(new ClueButtonHandler() );
			
		}
	};
	
	
	class ClueButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Button b = (Button)event.getSource();
			String changeWord=b.getText();
			b.setText("");
			
			//add the text to answerButtons
			for (int i = 0; i < twisterView.answerButtons.length; i++) { 
				if(twisterView.answerButtons[i].getText().equals("")) {
				twisterView.answerButtons[i].setText(changeWord);
				break;
				}
			}
			
		}
	}
	
	
	class SubmitButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			StringBuilder sb= new StringBuilder();
			for(int i=0;i<twisterView.answerButtons.length;i++) {
				if(!twisterView.answerButtons[i].getText().equals(""))
				sb.append(twisterView.answerButtons[i].getText());
			}
			
			String word=sb.toString();
			int index = twister.nextTry(word);

			sb.setLength(0);
			
			// if the word is correct, then add the word to solutionListViews
			if(index == GameView.THUMBS_UP_INDEX || index==GameView.SMILEY_INDEX){
			
				
			 
			a= twisterView.solutionListViews[word.length()-Twister.TWISTER_MIN_WORD_LENGTH].getItems();
			a.add(word);
			Collections.sort(a);
			
				
			for(int i =0;i<twisterView.answerButtons.length;i++) {
				sb.append(twisterView.answerButtons[i].getText());
			}
			
			// clear all the answerButtons 
			twister.twisterRound.setSubmittedListsByWordLength(sb.toString());	
			for(int i =0;i<twisterView.answerButtons.length;i++) {
				twisterView.answerButtons[i].setText("");
			}
			
			//make characters back to the clue buttons
			String newClue=twister.makeAClue(twister.twisterRound.getPuzzleWord());
			for(int i =0;i<twisterView.clueButtons.length;i++) {
				twisterView.clueButtons[i].setText(Character.toString(newClue.charAt(i)));
			}
			
			//update the wordScoreLabels
			for (int i = 0; i<Twister.SOLUTION_LIST_COUNT ; i++ ) {
				twisterView.wordScoreLabels[i].setText(Integer.toString(twister.twisterRound.getSubmittedListsByWordLength(i).size())+"/"+Integer.toString(twister.twisterRound.getSolutionListsByWordLength(i).size()));
			}
		}
			
			//if the index return SMILEY_INDEX or SADLY_INDEX make the time stop 
			twisterView.smileyButton.setGraphic(twisterView.smileyImageViews[index]);
			if (index == GameView.SMILEY_INDEX || index == GameView.SADLY_INDEX ) {
				int count=0;
				//count all the correct answer
				for(int i=0; i<twister.twisterRound.getSolutionListsByWordLength().size();i++) {
					count+=twister.twisterRound.getSubmittedListsByWordLength(i).size();
				}
				String str="1"+" "+twister.twisterRound.getPuzzleWord()+" "+(Twister.TWISTER_GAME_TIME-Integer.parseInt(GameView.wordTimer.timerButton.getText()))+" "+Float.toString((float)count/twister.twisterRound.getSolutionWordsList().size())+"\n";
				wordNerdModel.writeScore(str);
				GameView.wordTimer.timeline.stop();
				twister.twisterRound.setIsRoundComplete(true);
				
			}
			
			//update the topMessageText
	        twisterView.topMessageText.setText(twister.getScoreString());
		}
	}
	
	class TwistButtonHandler implements EventHandler<ActionEvent> {

		@Override
		
		//get the remaining clueButtons chars and use makeAClue method to create the clue then set the char to the buttons
		public void handle(ActionEvent event) {
			StringBuilder sb= new StringBuilder();
			for(int i=0;i<twisterView.clueButtons.length;i++) {
				if(!twisterView.clueButtons[i].getText().equals("")) {
				sb.append(twisterView.clueButtons[i].getText());
				}
			}
	
			for(int i=0;i<twisterView.clueButtons.length;i++) {
				twisterView.clueButtons[i].setText("");
			}
			
			
			String resetClue=twister.makeAClue(sb.toString());			
			for(int i=0;i<resetClue.length();i++) {
				twisterView.clueButtons[i].setText(Character.toString(resetClue.charAt(i)));		
			}			
			// TODO Auto-generated method stub
			
		}
	}
	
	//make all the characters back to the clueButtons and clear answerButtons
	class ClearButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			for (int i = 0; i < twisterView.answerButtons.length; i++) {
				twisterView.answerButtons[i].setText("");			
			}
			
			for (int i = 0; i < twisterView.clueButtons.length; i++) {
				twisterView.clueButtons[i].setText(Character.toString(twister.twisterRound.getClueWord().charAt(i)));
			}
			// TODO Auto-generated method stub
			
		}
	}
	
	//restart a round
	class NewButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
//			GameView.wordTimer.restart(Twister.TWISTER_GAME_TIME);
//			startController();
			twister.twisterRound = twister.setupRound();
			twisterView.refreshGameRoundView(twister.twisterRound);
			GameView.wordTimer.restart(Twister.TWISTER_GAME_TIME);
			setupBindings();
			
		}
	}
	
	//move the answer button back to the cluebutton 
	class AnswerButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			Button b = (Button)event.getSource();
			String changeWord=b.getText();
			b.setText("");
			for(int i=0;i<twisterView.answerButtons.length;i++) {
				if(twisterView.clueButtons[i].getText().equals("")) {
					twisterView.clueButtons[i].setText(changeWord);
					break;
					}
			}
			// TODO Auto-generated method stub
			
		}
	}
}

//AndrewID: tzuyuh  Tzuyu Huang
package hw3;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;


public class ScoreController extends WordNerdController{
	//initialize scoreView and scoreChart
	ScoreView scoreView= new ScoreView();;
	ScoreChart scoreChart=new ScoreChart();
	@Override
	void startController() {

		// TODO Auto-generated method stub
		
		
		scoreView.setupView();
		
		GameView.wordTimer = new WordTimer(150);
		
		
		
		//read the score file
		wordNerdModel.readScore();
		VBox lowerPanel = new VBox();
		lowerPanel.getChildren().addAll(scoreChart.drawChart(wordNerdModel.scoreList));
		lowerPanel.getChildren().add(WordNerd.exitButton);
		lowerPanel.setAlignment(Pos.CENTER);

		WordNerd.root.setCenter(scoreView.scoreGrid);
		WordNerd.root.setBottom(lowerPanel);

		
	}

	@Override
	 void setupBindings() {
		// TODO Auto-generated method stub
		
	}
	
	
	

}

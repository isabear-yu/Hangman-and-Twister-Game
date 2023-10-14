//AndrewID: tzuyuh  Tzuyu Huang
package hw3;

public class SearchController extends WordNerdController {

	SearchView searchView;
	
	@Override
	void startController() {
		// TODO Auto-generated method stub
		
		searchView= new SearchView();
		GameView.wordTimer = new WordTimer(150);
		wordNerdModel.readScore();	
		searchView.setupView();
		
	}

	
	void setupBindings() {
		
		
	}
}

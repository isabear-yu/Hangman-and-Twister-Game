//AndrewID: tzuyuh  Tzuyu Huang
package hw3;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
public class SearchView {

	ComboBox<String> gameComboBox = new ComboBox<>(); //shows drop down for filtering the tableView data
	TextField searchTextField = new TextField();  //for entering search letters
	TableView<Score> searchTableView = new TableView<>();  //displays data from scores.csv

	
	WordNerdModel model =new WordNerdModel();
	/**setupView() sets up the GUI components
	 * for Search functionality
	 */

	void setupView() {
		
		VBox searchVBox = new VBox();  //searchVBox contains searchLabel and searchHBox
		Text searchLabel = new Text("Search");
		searchVBox.getChildren().add(searchLabel);

		HBox searchHBox = new HBox();  //searchHBox contain gameComboBox and searchTextField
		searchHBox.getChildren().add(gameComboBox);
		searchHBox.getChildren().add(new Text("Search letters"));
		searchHBox.getChildren().add(searchTextField);
		searchVBox.getChildren().add(searchHBox);
		
		searchLabel.setStyle( "-fx-font: 30px Tahoma;" + 
				" -fx-fill: linear-gradient(from 0% 50% to 50% 100%, repeat, lightgreen 0%, lightblue 50%);" +
				" -fx-stroke: gray;" +
				" -fx-background-color: gray;" +
				" -fx-stroke-width: 1;") ;
		searchHBox.setPrefSize(WordNerd.GAME_SCENE_WIDTH, WordNerd.GAME_SCENE_HEIGHT / 3);
		gameComboBox.setPrefWidth(200);
		searchTextField.setPrefWidth(300);
		searchHBox.setAlignment(Pos.CENTER);
		searchVBox.setAlignment(Pos.CENTER);
		searchHBox.setSpacing(10);
		
		setupSearchTableView();
		
		WordNerd.root.setPadding(new Insets(10, 10, 10, 10));
		WordNerd.root.setTop(searchVBox);
		WordNerd.root.setCenter(searchTableView);
		WordNerd.root.setBottom(WordNerd.exitButton);
	}

	void setupSearchTableView() {

		//add the headers to the columns
		gameComboBox.getItems().add("All games");
		gameComboBox.getItems().add("Hangman");
		gameComboBox.getItems().add("Twister");
		
		//set the default text on the combobox
		gameComboBox.getSelectionModel().selectFirst();
		
		
		//use callback to change the data text on the tableview
		Callback<CellDataFeatures<Score,String>,ObservableValue<String>> callback=
				new Callback<CellDataFeatures<Score,String>,ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Score, String> param) {
						//if the id is 0 then display "Hangman"
							if(param.getValue().getGameId()==0) {
								return (new SimpleStringProperty("Hangman")); 
							}
							
							//if the id is 1 then display "Twister"
							else if(param.getValue().getGameId()==1) {
								return (new SimpleStringProperty("Twister")); 
							}
							
						return null;
					}		
		};
		
		Callback<CellDataFeatures<Score,String>,ObservableValue<String>> scoreCall=
				new Callback<CellDataFeatures<Score,String>,ObservableValue<String>>() {
					//change the value with two digits
					@Override
					public ObservableValue<String> call(CellDataFeatures<Score, String> param) {
						return (new SimpleStringProperty(String. format("%.2f", param.getValue().getScore()))); 
					
					}		
		};

		
		model.readScore();
		searchTableView.setItems(model.scoreList);
		
		TableColumn gameId= new TableColumn("Game");
		TableColumn puzzleWord= new TableColumn("Word");
		TableColumn timeStamp = new TableColumn("Time (Sec)");
		TableColumn score= new TableColumn("Score");
		
        gameId.prefWidthProperty().bind(searchTableView.widthProperty().multiply(.25));
        puzzleWord.prefWidthProperty().bind(searchTableView.widthProperty().multiply(.25));
        timeStamp.prefWidthProperty().bind(searchTableView.widthProperty().multiply(.25));
        score.prefWidthProperty().bind(searchTableView.widthProperty().multiply(.25));
       
        FilteredList<Score> filteredData = new FilteredList<>(model.scoreList, p -> true);
        
        
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(sc -> {
				// If filter text is empty, display all scores.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare puzzleword with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				if (sc.getPuzzleWord().toLowerCase().contains(lowerCaseFilter)&& gameComboBox.getSelectionModel().getSelectedItem().equals("All games")) {
					return true; // Filter matches puzzleword.
				} 
				
				if (sc.getPuzzleWord().toLowerCase().contains(lowerCaseFilter)&& gameComboBox.getSelectionModel().getSelectedItem().equals("Hangman") ) {
					return true; // Filter the Hangman and matches puzzleword.
				} 
				if (sc.getPuzzleWord().toLowerCase().contains(lowerCaseFilter)&& gameComboBox.getSelectionModel().getSelectedItem().equals("Twister")) {
					return true; // Filter the Twister and matche puzzleword.
				} 
				
				return false; // Does not match.
			});
		});
        
		gameComboBox.valueProperty().addListener((observable,oldValue,newValue)->{

			filteredData.setPredicate(sc -> {
				// If filter text is empty, display all scores.
				if (gameComboBox.getSelectionModel().getSelectedIndex()==0) {
					return true;
				}
				
				// filter the selected game
				if ((sc.getGameId()+1)==gameComboBox.getSelectionModel().getSelectedIndex()) {
					return true; // Filter matches the gameid.
				} 
				
				
				return false; // Does not match.
			}); 
			
		});
        
        SortedList<Score> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(searchTableView.comparatorProperty());
        searchTableView.setItems(sortedData);
       
        
        //read the propertyvalue 
        gameId.setCellValueFactory(callback);
        puzzleWord.setCellValueFactory(new PropertyValueFactory<Score,String>("PuzzleWord"));
        timeStamp.setCellValueFactory(new PropertyValueFactory<Score,Integer>("TimeStamp"));
        score.setCellValueFactory(scoreCall);
        
        //set the data to tableview 
		searchTableView.getColumns().addAll(gameId, puzzleWord, timeStamp,score);
		
		
		
       
		//write your code here
	}
}

//AndrewID: tzuyuh  Tzuyu Huang
package hw3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WordNerdModel {
	public static String[] wordsFromFile;
	public static final String WORDS_FILE_NAME ="data/wordsFile.txt";
	public static final String SCORE_FILE_NAME ="data/scores.csv";
	ObservableList<Score> scoreList= FXCollections.observableArrayList();
	StringBuilder sb=new StringBuilder();
	static int count=0;
	static void readWordsFile(String wordsFilename) {
		StringBuilder stringBuilder = new StringBuilder();
		Scanner fileScanner=null;
		
		try {
			File file = new File(wordsFilename);
			fileScanner = new Scanner(file);
			while(fileScanner.useDelimiter("\n").hasNext()) {
				stringBuilder.append(fileScanner.next().trim()+"\n"); 
			}
			
			fileScanner.close();
			wordsFromFile = stringBuilder.toString().trim().split("\n"); 
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		} 
			
		
	}
	
	void writeScore(String scoreString) {
		
		//get all the data from the file and append the new score
		StringBuilder c = new StringBuilder();
		String name=SCORE_FILE_NAME;
		Scanner fileScanner = null;
		try {
			File file = new File(name);
			fileScanner = new Scanner(file);
			while(fileScanner.useDelimiter(",").hasNext()) {
				c.append(fileScanner.next()+" ");
			}
			if(c.length()>=3) {
			c.delete(c.length()-1, c.length());
			}
			c.append(scoreString);
			String sc=c.toString();
		
		//write all the score into the file
		Scanner input =new Scanner(sc);
		String[] next=null;
		try(
			BufferedWriter bw= new BufferedWriter(new FileWriter(SCORE_FILE_NAME));
		){
			do {
				next=input.nextLine().split("\\s+");
				
				bw.write(String.format("%s,%s,%s,%s%n", next[0],next[1],next[2],next[3]));
				
			}while(input.hasNext());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input.close();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


	}
	
	//read the score from the file
	void readScore() {
		scoreList.clear();
		StringBuilder c = new StringBuilder();
		String name=SCORE_FILE_NAME;
		Scanner fileScanner = null;
		try {
			File file = new File(name);
			fileScanner = new Scanner(file);
			while(fileScanner.useDelimiter(",").hasNext()) {
				c.append(fileScanner.next()+",");
			}
			String[] sc=c.toString().split("\n");
		
			for(int i=0;i<sc.length-1;i++) {
				Score s=new Score(Integer.parseInt(sc[i].split(",")[0]),sc[i].split(",")[1],Integer.parseInt(sc[i].split(",")[2]),Float.parseFloat(sc[i].split(",")[3]));
				scoreList.add(s);
			}

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	
		


	
	}
}

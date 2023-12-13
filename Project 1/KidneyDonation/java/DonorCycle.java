import javax.sound.midi.SysexMessage;
import java.io.*;
import java.util.*;

public class DonorCycle{

	public static boolean isInCycle(int[][] matchScores, int[] donorFriends, int query){
			//donor friends 0 0 0 1 1 1 1 2 2
		int[] recipientsConditions = new int[matchScores[0].length];
		for(int i = 0; i< recipientsConditions.length; i++){
			recipientsConditions[i] = 0;
		}
		for(int i=0; i<matchScores.length; i++){   // number of donors   **9 kez gezicek
			for(int j=0; j<matchScores[i].length; j++){  // number of recipients **3 kez gezicek, insanları gezicek 0 iken 2. donör olmalı , 1 ken 0.cı
				if(matchScores[i][j] >= 60){       // if their score is above 60
					if((donorFriends[i]+1)==j){    // if donor friends of A can give A+1(B)
						//System.out.println("i = "+ i + "j = "+j);
						recipientsConditions[j] = 1;   // donor for recipient j is found
					}else if( j == 0 && donorFriends[i] == (j+matchScores[i].length-1) ){  // if recipient is 0th and donorFriends is friend of the last recipient
						//System.out.println("i = "+ i + "j = "+j);
						recipientsConditions[j] = 1;  // donor for recipient j is found
					}else if( i == matchScores.length-1 && j == (query-1)){  //if the all recipients are matched and there is a 1 donor which isnt matched, matches a recipient in the queue waits for the donor
						recipientsConditions[j] = 1;  // donor for recipient j is found
						//System.out.println("i = "+ i + "j = "+j);
						recipientsConditions[0] = 1;
					}
				}
			}
		}
		for(int i = 0; i< recipientsConditions.length; i++){
			if(recipientsConditions[i] == 0){
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args){

		Scanner s = new Scanner(System.in);

		int n = s.nextInt();
		int m = s.nextInt();

		String donorsLine = s.next();
		String[] donorsArray = donorsLine.split(",",0);
		int[] donorsFriends = new int[donorsArray.length];
		for(int i=0; i<m; i++){
			donorsFriends[i] = Integer.parseInt(donorsArray[i]);
		}

		int[][] matchScores = new int[m][n]; //9 3 lük bir match score açılmış  0 için
		for(int i=0; i<m; i++){
			String matchscoreLine = s.next();
			String[] matchscoreArray = matchscoreLine.split(",",0);
			for(int j=0; j<n; j++){
				matchScores[i][j] = Integer.parseInt(matchscoreArray[j]);
			}	
		}
		int query = s.nextInt();
		//System.out.println(matchScores.length);
		System.out.println(isInCycle(matchScores, donorsFriends, query));
	}


}

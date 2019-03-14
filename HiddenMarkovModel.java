import java.util.Scanner;
import java.util.HashMap;
import java.util.Arrays;
public class HiddenMarkov {
    
    public static void main(String[] args) {
		Scanner read = new Scanner(System.in);
		String[] lines = new String[7];
		HashMap<String,Integer> locations = new HashMap<String,Integer>();
		HashMap<String,Integer> clues = new HashMap<String,Integer>(); 
		HashMap<Integer,String> convert = new HashMap<Integer,String>();
		int idxLoc = 0;
		int idxClue = 0;
		String lastLocation = "";
		String[] locationsList = new String[7];
		String[] cluesList = new String[7];
        for (int i = 0; i < 6; i++){
			lines[i] = read.nextLine();
			String[] spl = lines[i].split("\\|");
			String clue = spl[0];
			String location = spl[1];
			locationsList[i] = location;
			cluesList[i] =clue;
			if (locations.containsKey(location)==false){
			//	System.out.println("location: "+ location);
				locations.put(location,idxLoc);	
				convert.put(idxLoc,location);
				idxLoc ++;
			}
			if (clues.containsKey(clue) ==false){
				clues.put(clue,idxClue);
				idxClue++;
			}
			if (i == 5){
				lastLocation = location;
			}
	
		}
		String finalClue = read.nextLine();

		// System.out.println(idxClue);
		// System.out.println(idxLoc);
		int[][] clueGrid = new int[idxClue][idxLoc+1];
		int[][] locationsGrid = new int[idxLoc][idxLoc+1];
		//System.out.println(clueGrid[0][0]);
		for (int i = 0; i < 5; i++){
			// System.out.println("move from " + locations.get(locationsList[i])+ "to " + locations.get(locationsList[i+1]));
			locationsGrid[locations.get(locationsList[i])][locations.get(locationsList[i+1])] ++;
			locationsGrid[locations.get(locationsList[i])][idxLoc] ++;
		}
		for (int i = 0; i < 6; i++){
			// System.out.println("clue " + locations.get(locationsList[i])+ "ends up at " + locations.get(locationsList[i+1]));
			clueGrid[clues.get(cluesList[i])][locations.get(locationsList[i])] ++;
			clueGrid[clues.get(cluesList[i])][idxLoc] ++;
		}
		try {
			double denominator = 0;

			for (int i = 0; i < idxLoc; i++){

				double numerator = ((double)clueGrid[clues.get(finalClue)][i]/(double)clueGrid[clues.get(finalClue)][idxLoc])*((double)locationsGrid[locations.get(lastLocation)][i]/(double)locationsGrid[locations.get(lastLocation)][idxLoc]);
				// System.out.println((clueGrid[clues.get(finalClue)][i]) + "/" + (clueGrid[clues.get(finalClue)][idxLoc]));
				// System.out.println("Numerator" + numerator);
				denominator += numerator;
				// System.out.println(clues.get(finalClue) + " " + i + " " + clues.get(finalClue) + (idxLoc));
			}
			String[] toPrint = new String[idxLoc];
			for (int i = 0; i < idxLoc; i++){
				double numerator = ((double)clueGrid[clues.get(finalClue)][i]/(double)clueGrid[clues.get(finalClue)][idxLoc])*((double)locationsGrid[locations.get(lastLocation)][i]/(double)locationsGrid[locations.get(lastLocation)][idxLoc]);
				if (Double.isNaN((numerator/denominator))){
					toPrint[i] = (convert.get(i) +": undefined");
				}
				else {
					toPrint[i] = (convert.get(i) +": " + String.format("%.2f", (numerator/denominator)*100) + " %");}

				//System.out.println(convert.get(i) +": " + String.format("%.2f", (numerator/denominator)*100) + " %");
			}
			Arrays.sort(toPrint);
			for(int i = 0; i < toPrint.length; i++)
				System.out.println(toPrint[i]);
		}
  		catch (NullPointerException e){
			String[] toPrint = new String[idxLoc];
			for (int i = 0; i < idxLoc; i++){
				toPrint[i] = (convert.get(i) +": undefined");}
				Arrays.sort(toPrint);
			for(int i = 0; i < toPrint.length; i++)
				System.out.println(toPrint[i]);
		}
    }
}

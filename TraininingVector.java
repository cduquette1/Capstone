/*
 * Class that will create training vector, using stemmer and combine list
 * Carolyn Lynch
 * Courtney Duquette
 * Capstone 2016
 */
public class TraininingVector {

	public static void main(String args[]) {
		
		CombineList listToCompare = new CombineList();
		listToCompare.gatherWebsites();
		listToCompare.makeList(listToCompare.getInputFiles());
		
		/*
		 * for(each website in file)
		 * {
		 * 	  save category to string
		 * 	  make new vector row
		 * 	  go through parsed html, cross checking for words in list (1 in column for included or 0 for not included)
		 * 	  add category to row
		 * }
		 */
	}
}

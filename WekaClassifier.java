import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

import javax.swing.JOptionPane;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

/*
 * Courtney Duquette
 * Carolyn Lynch
 * 4/27/2016
 * Web Classification for Bot Detection
 * 
 * Class that will use Weka to do web classification
 * 
 * Capstone Project
*/

public class WekaClassifier {

	//Boolean to account for lag with interaction of Wget/Weka
	private boolean websiteDownloaded = false;
	
	public String runClassifier(boolean trained, String websiteFile) {
		try {
			//Train classifier
			if (!trained) {
				
				//If not trained then training vector needs to be rebuilt and ran
				TraininingVector training = new TraininingVector();
				training.buildTrainVector();
			}
			
			BufferedReader breader = null;
			breader = new BufferedReader(new FileReader("TrainingData.arff"));
			Instances train = new Instances(breader);
			train.setClassIndex(train.numAttributes() - 1);

			//Apply input to classify
			TestVector testing = new TestVector();
			testing.buildTestVector("Websites/" + websiteFile);
			breader = new BufferedReader(new FileReader("TestData.arff"));
			Instances test = new Instances(breader);
			test.setClassIndex(train.numAttributes() - 1);

			breader.close();

			//Destinates which classification to use
			RandomForest tree = new RandomForest();
			tree.buildClassifier(train);
			Instances labeled = new Instances(test);

			//Create outputFile
			runOutputFile(labeled, tree, test);
			
			//Create evaluation
			runCrossValidation(train, tree);
			
			//Create output of single instance
			double variableName = tree.classifyInstance(test.firstInstance());
			labeled.firstInstance().setClassValue(variableName);
			return labeled.toString();

		} catch (Exception e) {
			
			if(websiteDownloaded == false)
				JOptionPane.showMessageDialog(null, "Website has been sent to download. Please hit the \"Classify\" button again.");
			else
				JOptionPane.showMessageDialog(null, "Website was not able to be downlaoded, please enter a new site.");
			
			websiteDownloaded = !websiteDownloaded;
			return null;
		}
	}

	public void runOutputFile(Instances labeled, RandomForest tree, Instances test) throws Exception {

		for (int i = 0; i < test.numInstances(); i++) {
			double variableName = tree.classifyInstance(test.instance(i));
			labeled.instance(i).setClassValue(variableName);
		}

		// create output
		BufferedWriter writer = new BufferedWriter(new FileWriter("ClassifiedData.arff"));
		writer.write(labeled.toString());
		writer.close();

	}
	
	public void runCrossValidation(Instances train, RandomForest tree) throws Exception {
	    
	    Evaluation eval = new Evaluation(train);
	    Random rand = new Random(1);
	    int folds = 10;
	    eval.crossValidateModel(tree, train, folds, rand);
	    System.out.println(eval.toClassDetailsString());
	}
	
	public static void main (String[] args) {
	    
	    WekaClassifier sample = new WekaClassifier();
	    sample.runClassifier(false, "UnknownWeb.txt");
	}
}
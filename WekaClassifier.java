import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import weka.classifiers.trees.J48;
import weka.core.Instances;

public class WekaClassifier {

	public static void main(String args[]) throws Exception {
		
		//train classifier
	    TraininingVector.buildTrainVector();
		BufferedReader breader = null;
		breader = new BufferedReader(new FileReader("TrainingData.arff"));
		Instances train = new Instances(breader);
		train.setClassIndex(train.numAttributes() - 1);
		
		//apply input to classify
		TestVector.buildTestVector();
		breader = new BufferedReader(new FileReader("TestData.arff"));
		Instances test = new Instances(breader);
		test.setClassIndex(train.numAttributes()- 1 );
		
		breader.close();
		
		//destinates which classification to use
		J48 tree = new J48();
		tree.buildClassifier(train);
		Instances labeled = new Instances(test);
		
		for(int i = 0; i < test.numInstances(); i++) {
			double variableName = tree.classifyInstance(test.instance(i));
			labeled.instance(i).setClassValue(variableName);	
		}
		
		//create output
		BufferedWriter writer = new BufferedWriter(new FileWriter("ClassifiedData.arff"));
		writer.write(labeled.toString());
		
		
		writer.close();
		
	}
}

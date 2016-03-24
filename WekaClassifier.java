import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JOptionPane;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

public class WekaClassifier {

	public String runClassifier(boolean trained) {
		try {

			// train classifier
			if (!trained) {
				TraininingVector training = new TraininingVector();
				training.buildTrainVector();
			}
			BufferedReader breader = null;
			breader = new BufferedReader(new FileReader("TrainingData.arff"));
			Instances train = new Instances(breader);
			train.setClassIndex(train.numAttributes() - 1);

			// apply input to classify
			TestVector testing = new TestVector();
			testing.buildTestVector("Websites/DemoUnknownWeb.txt");
			breader = new BufferedReader(new FileReader("TestData.arff"));
			Instances test = new Instances(breader);
			test.setClassIndex(train.numAttributes() - 1);

			breader.close();

			// destinates which classification to use
			// J48 tree = new J48();
			// NaiveBayes tree = new NaiveBayes();
			RandomForest tree = new RandomForest();
			tree.buildClassifier(train);
			Instances labeled = new Instances(test);

			// create output
			double variableName = tree.classifyInstance(test.firstInstance());
			labeled.firstInstance().setClassValue(variableName);
			return labeled.toString();

		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "There was a connection error, please try again.");
			throw new IllegalArgumentException();
		}
	}

	public static void main(String args[]) throws Exception {

		// creates training vector and then classifies test data
		// train classifier
		TraininingVector training = new TraininingVector();
		training.buildTrainVector();
		BufferedReader breader = null;
		breader = new BufferedReader(new FileReader("TrainingData.arff"));
		Instances train = new Instances(breader);
		train.setClassIndex(train.numAttributes() - 1);

		// apply input to classify
		TestVector testing = new TestVector();
		testing.buildTestVector("Websites/UnknownWeb.txt");
		breader = new BufferedReader(new FileReader("TestData.arff"));
		Instances test = new Instances(breader);
		test.setClassIndex(train.numAttributes() - 1);

		breader.close();

		// destinates which classification to use
		// J48 tree = new J48();
		// NaiveBayes tree = new NaiveBayes();
		RandomForest tree = new RandomForest();
		tree.buildClassifier(train);
		Instances labeled = new Instances(test);

		for (int i = 0; i < test.numInstances(); i++) {
			double variableName = tree.classifyInstance(test.instance(i));
			labeled.instance(i).setClassValue(variableName);
		}

		// create output
		BufferedWriter writer = new BufferedWriter(new FileWriter("ClassifiedData.arff"));
		writer.write(labeled.toString());
		writer.close();

	}
}
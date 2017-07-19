package com.ab.docSimilarity.calculations;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ab.docSimilarity.cInterface.WMDJNIWrapper;
import com.ab.docSimilarity.io.ReadFiles;
import com.ab.docSimilarity.model.WordVecs;
import com.ab.docSimilarity.utils.StanfordAnnotator;
import com.ab.docSimilarity.utils.TextUtils;

public class TextDistanceCalulation {

	WordVecs wordVecs;
	StanfordAnnotator stanfordAnnotator;
	WMDJNIWrapper wmdjniWrapper;
	
	
	public TextDistanceCalulation() throws NumberFormatException, IOException {
		super();
		stanfordAnnotator = new StanfordAnnotator();
		wordVecs = ReadFiles.loadWordVecSpace("/home/abhishek/DataSet/WordEmbeddings/glove.6B/glove.6B.50d.txt");
		wmdjniWrapper = new WMDJNIWrapper();
	}



	public double calculateTextWMDDistance(String text1, String text2) {
	
		List<String> text1List = stanfordAnnotator.lemmatize(text1);
		text1List = TextUtils.removePunctuations(text1List);
		text1List = TextUtils.removeStopList(text1List);
		text1List = TextUtils.dictionaryWords(text1List, wordVecs.getDictionaryWords());
		Map<String, Integer> text1CountMap = TextUtils.wordCountMap(text1List);
		Map<String, Double> text1WeightMap = TextUtils.wordWeightMap(text1CountMap);
		double[] w1 = getCorrespondingWordWeights(text1List, text1WeightMap);
		System.out.println("W1 = "+text1List.toString());
		
		List<String> text2List = stanfordAnnotator.lemmatize(text2);
		text2List = TextUtils.removePunctuations(text2List);
		text2List = TextUtils.removeStopList(text2List);
		text2List = TextUtils.dictionaryWords(text2List, wordVecs.getDictionaryWords());
		Map<String, Integer> text2CountMap = TextUtils.wordCountMap(text2List);
		Map<String, Double> text2WeightMap = TextUtils.wordWeightMap(text2CountMap);
		double[] w2 = getCorrespondingWordWeights(text2List, text2WeightMap);
		System.out.println("W2 = "+text2List.toString());
		
		double[][] costMatrix = calculateCostMatrix(text1List, text2List);
		
		double distance = wmdjniWrapper.computeDistance(w1, w2, costMatrix);
		
		return distance;
	}
	
	public double calculateTextWMDDistanceWithWordCount(String text1, String text2) {
		
		List<String> text1List = stanfordAnnotator.lemmatize(text1);
		text1List = TextUtils.removePunctuations(text1List);
		text1List = TextUtils.removeStopList(text1List);
		text1List = TextUtils.dictionaryWords(text1List, wordVecs.getDictionaryWords());
		Map<String, Integer> text1CountMap = TextUtils.wordCountMap(text1List);
		double[] w1 = getCorrespondingWordCount(text1List, text1CountMap);
		System.out.println("W1 = "+text1List.toString());
		
		List<String> text2List = stanfordAnnotator.lemmatize(text2);
		text2List = TextUtils.removePunctuations(text2List);
		text2List = TextUtils.removeStopList(text2List);
		text2List = TextUtils.dictionaryWords(text2List, wordVecs.getDictionaryWords());
		Map<String, Integer> text2CountMap = TextUtils.wordCountMap(text2List);
		double[] w2 = getCorrespondingWordCount(text2List, text2CountMap);
		System.out.println("W2 = "+text2List.toString());
		
		double[][] costMatrix = calculateCostMatrix(text1List, text2List);
		
		double distance = wmdjniWrapper.computeDistance(w1, w2, costMatrix);
		
		return distance;
	}
	
	public double[] getCorrespondingWordWeights(List<String> wordList, Map<String,Double> wordWeightMap) {
		
		double output[] = new double[wordList.size()];
		for(int i = 0; i<wordList.size();i++) {
			output[i] = (double)wordWeightMap.get(wordList.get(i));
		}
		
		return output;
	}
	
public double[] getCorrespondingWordCount(List<String> wordList, Map<String,Integer> wordCountMap) {
		
		double output[] = new double[wordList.size()];
		for(int i = 0; i<wordList.size();i++) {
			output[i] = wordCountMap.get(wordList.get(i));
		}
		
		return output;
	}
	
	public double[][] calculateCostMatrix(List<String> text1List, List<String> text2List) {
		
		double output[][] = new double[text1List.size()][text2List.size()];
		for(int i=0;i<text1List.size();i++) {
			for(int j=0;j<text2List.size();j++) {
				List<Double> vec1 = wordVecs.getWordVec(text1List.get(i));
				List<Double> vec2 = wordVecs.getWordVec(text2List.get(j));
				output[i][j] = DistanceCalculation.euclideanDistance(vec1, vec2);
			}
		}
		
		return output;
	}
}

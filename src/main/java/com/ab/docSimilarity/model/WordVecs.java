package com.ab.docSimilarity.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordVecs {

	Map<String, List<Double>> wordVecMap;

	public WordVecs() {
		super();
		wordVecMap = new HashMap<>();
	}
	
	public List<Double> getWordVec(String word) {
		
		List<Double> output = new ArrayList<>();
		output = wordVecMap.get(word);
		
		return output;
	}
	
	public void addWordVec(String word, List<Double> wordVec) {
		
		wordVecMap.put(word, wordVec);
	}
	
	public Set<String> getDictionaryWords() {
		return wordVecMap.keySet();
	}
}

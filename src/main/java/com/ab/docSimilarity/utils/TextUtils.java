package com.ab.docSimilarity.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class TextUtils {

	public static List<String> stopList;

	//Static block to initialize the static stopList
	static {
		
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream("/home/abhishek/workspace_exps/docSimilarity/src/main/resources/stopwords.txt");
			Yaml yaml = new Yaml();
			stopList = (List<String>)yaml.load(fileInputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	/*public TextUtils() {
		super();

		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream("src/main/resources/stopwords.txt");
			Yaml yaml = new Yaml();
			stopList = (List<String>)yaml.load(fileInputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	public static List<String> tokenizeText(String text) {

		List<String>output = new ArrayList<>();
		StringReader stringReader = new StringReader(text);
		PTBTokenizer<CoreLabel> ptbTokemizer =new PTBTokenizer<CoreLabel>(stringReader, new CoreLabelTokenFactory(), "");
		List<CoreLabel> tokens = ptbTokemizer.tokenize();

		tokens.stream().forEach(a->{output.add(a.value());});

		return output;
	}

	public static List<String> removeStopList(List<String>input) {
		
		List<String> output = new ArrayList<>();
		for (String string : input) {
			if(stopList.contains(string.trim())){
				continue;
			}
			output.add(string);
		}
		
		return output;
	}
	
	public static List<String> removePunctuations(List<String> input){
		
		List<String> output = new ArrayList<>();
		for (String string : input) {
			String string3 = string.replaceAll("[^a-zA-Z ]", "").toLowerCase();
			if(StringUtils.isNotBlank(string3)) {
				output.add(string3.trim());				
			}
		}
		
		return output;
	}
	
	public static Map<String, Integer> wordCountMap(List<String> input) {
		Map<String,Integer> output = new HashMap<>();
		for (String string : input) {
			if(output.containsKey(string)){
				Integer integer = output.get(string);
				output.put(string, integer+1);
			}else {
				output.put(string, 1);
			}
		}
		
		return output;
	}
	
	public static Map<String,Double> wordWeightMap(Map<String, Integer> countMap) {
		
		Map<String, Double> outputWeightMap = new HashMap<>();
		Collection<Integer> values = countMap.values();
		int sum=addCollection(values);
		
		for (Entry<String, Integer> entry : countMap.entrySet()) {
			outputWeightMap.put(entry.getKey(), (double)entry.getValue()/sum);
		}
		
		return outputWeightMap;
	}
	
	private static int addCollection(Collection<Integer> values) {
		
		int sum = 0;
		for (Integer integer : values) {
			sum+=integer;
		}
		return sum;
	}
	
	public static List<String> dictionaryWords(List<String> list, Set<String> dictionary) {
		List<String> output = new ArrayList<>();
		for (String string : list) {
			if(dictionary.contains(string)) {
				output.add(string);
			}
		}
		
		return output;
	}

	public static void main(String[] args) throws FileNotFoundException {

		FileInputStream fileInputStream = new FileInputStream("src/main/resources/stopwords.txt");
		Yaml yaml = new Yaml();
		List<String> list = (List<String>)yaml.load(fileInputStream);
		list.forEach(b->System.out.println(b));

	}
}

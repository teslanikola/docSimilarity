package com.ab.docSimilarity.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ab.docSimilarity.model.WordVecs;

public class ReadFiles {

	public static WordVecs loadWordVecSpace(String filePath) throws NumberFormatException, IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		WordVecs wordVecs = new WordVecs();
		
		String s="";
		while((s=reader.readLine())!=null) {
			String[] split = StringUtils.split(s, " ");
			if(split.length > 1) {
				String word = split[0].toLowerCase();
				List<Double> vec = new ArrayList<>();
				for(int i=1;i<split.length;i++) {
					vec.add(Double.parseDouble(split[i]));
				}
				wordVecs.addWordVec(word, vec);
			}
		}
		
		reader.close();
		return wordVecs;
	}
}

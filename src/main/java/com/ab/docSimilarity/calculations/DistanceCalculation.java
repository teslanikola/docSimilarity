package com.ab.docSimilarity.calculations;

import java.util.List;

public class DistanceCalculation {

	/**
	 * Calculates the euclidean distance between 2 vectors
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static double euclideanDistance(List<Double> list1, List<Double> list2) {
		
		double output =0.0f;
		if(list1.size() != list2.size()) {
			return Double.NaN;
		}
		
		for(int i=0;i<list1.size();i++) {
			double sq = (list1.get(i)-list2.get(i))*(list1.get(i)-list2.get(i));
			output+=sq;
		}
		
		return Math.sqrt(output);
	}
	
	/**
	 * Calculates the distance matrix
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static double[][] calculateDistanceMatrix(List<List<Double>> list1, List<List<Double>> list2) {
		
		double output[][] = new double[list1.size()][list2.size()];
		
		for(int i=0; i<list1.size(); i++) {
			for(int j=0; j<list2.size(); j++) {
				
				output[i][j] = euclideanDistance(list1.get(i), list2.get(j));
			}
		}
		
		return output;
	}
}

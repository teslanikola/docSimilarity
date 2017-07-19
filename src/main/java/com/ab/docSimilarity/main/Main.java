package com.ab.docSimilarity.main;

import java.io.IOException;

import com.ab.docSimilarity.calculations.TextDistanceCalulation;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub

		/*double  w1[] = { 0.4, 0.2, 0.2, 0.1, 0.1 },
				w2[] = { 0.6, 0.2, 0.2 };

		double dist[][] = {{0.3, 0.5, 0.2},{0.0, 0.2, 0.5},{0.1, 0.1, 0.3},{0.8, 0.4, 0.3},{0.7, 0.6, 0.5}};

		WMDJNIWrapper wmdjniWrapper = new WMDJNIWrapper();
		double outputDistance = wmdjniWrapper.computeDistance(w1, w2, dist);
		System.out.println("The output distance = "+outputDistance);*/
		
		TextDistanceCalulation distanceCalulation = new TextDistanceCalulation();
		//double distance = distanceCalulation.calculateTextWMDDistance("Obama speaks to the media in Illinois", "The president greets the press in Chicago");
		//double distance = distanceCalulation.calculateTextWMDDistance("Obama speaks to the media in Illinois", "The press was greeted by the president in Chicago");
		//double distance = distanceCalulation.calculateTextWMDDistance("Obama speaks to the media in Illinois", "The press was greeted by the president in Chicago");
		double distance = distanceCalulation.calculateTextWMDDistanceWithWordCount("Obama speaks to the media in Illinois as a part of Obama's nationwide tour this year", "The press was greeted by the president in Chicago");
		System.out.println("Distance is = "+distance);
	}

}

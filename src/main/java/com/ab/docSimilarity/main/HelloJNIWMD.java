package com.ab.docSimilarity.main;

public class HelloJNIWMD {
	public native double computeDistance(double w1[], double w2[], double dist[][]);

	static { System.loadLibrary("HelloJNIWMDImpl"); }

	public static void main (String[] args) {
		HelloJNIWMD hello = new HelloJNIWMD();

		double  w1[] = { 0.4, 0.2, 0.2, 0.1, 0.1 },
				w2[] = { 0.6, 0.2, 0.2 };

		double dist[][] = {{3.1, 5.1, 2.2},{0.5, 2.6, 5.4},{1.1, 1.1, 3.2},{8.3, 4.1, 3.2},{7.1, 6.2, 5.3}};
		double outputDistance = hello.computeDistance(w1, w2, dist);
		System.out.println("The output distance = "+outputDistance);
	}
}

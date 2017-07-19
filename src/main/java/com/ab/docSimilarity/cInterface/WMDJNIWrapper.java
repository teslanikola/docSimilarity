package com.ab.docSimilarity.cInterface;

public class WMDJNIWrapper {

	 public native double computeDistance(double w1[], double w2[], double dist[][]);

	  static { System.loadLibrary("WMDJNIWrapperImpl"); }
}

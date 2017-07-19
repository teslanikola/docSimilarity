# docSimilarity
Copy emd.h, emd.c, com_ab_docSimilarity_cInterface_WMDJNIWrapper.h and WMDWrapper.c to the target
gcc -o libWMDJNIWrapperImpl.so -lc -shared -I$JAVA_HOME/include -I$JAVA_HOME/include/linux -fPIC WMDWrapper.c emd.c

#Need to put the above in pom

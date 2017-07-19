#include <stdio.h>
#include "emd.h"
#include "com_ab_docSimilarity_cInterface_WMDJNIWrapper.h"



float _COST[1000][1000];


float dist(feature_t *F1, feature_t *F2) { return _COST[*F1][*F2]; }

JNIEXPORT jdouble JNICALL Java_com_ab_docSimilarity_cInterface_WMDJNIWrapper_computeDistance
  (JNIEnv *env, jobject obj, jdoubleArray w1Arr, jdoubleArray w2Arr, jobjectArray distance)
{

  jint w1Size = (*env)->GetArrayLength(env,w1Arr);
  jint w2Size = (*env)->GetArrayLength(env,w2Arr);


  jint width = (*env)->GetArrayLength(env, distance);
    for(int i=0;i<width;i++) {
      jdoubleArray *line =   (*env)->GetObjectArrayElement(env, distance, i);
      int height = (*env)->GetArrayLength(env, line);
      jdouble *pos = (*env)->GetDoubleArrayElements(env, line, 0);
      for(int j=0;j<height;j++) {
        _COST[i][j] = *(pos+j);
      }
    }

  feature_t   f1[w1Size],f2[w2Size];
  float       w1[w1Size],w2[w2Size];

  double* w1Ptr  = (*env)->GetDoubleArrayElements(env, w1Arr,0); // Get C++ pointer to array data 
  double* w2Ptr  = (*env)->GetDoubleArrayElements(env, w2Arr,0); // and "pin" array elements

    //Initializing the first set
    for(int i=0;i<w1Size;i++){
      f1[i]=i;
      w1[i] = *(w1Ptr+i);
    }

    //Initializing the 2nd set
    for(int i=0;i<w2Size;i++){
      f2[i]=i;
      w2[i]=*(w2Ptr+i);
    }

    signature_t s1 = { w1Size, f1, w1},
        s2 = { w2Size, f2, w2};
  float       e;
  flow_t      flow[w1Size*w2Size];
  int         i, flowSize;

  e = emd(&s1, &s2, dist, flow, &flowSize);
  //e = emd(&s2, &s1, dist, flow, &flowSize);

  printf("emd=%f\n", e);
  printf("\nflow:\n");
  printf("from\tto\tamount\n");
  for (i=0; i < flowSize; i++)
    if (flow[i].amount > 0)
      printf("%d\t%d\t%f\n", flow[i].from, flow[i].to, flow[i].amount);

  printf("Trial 3\n");

  return e;
}

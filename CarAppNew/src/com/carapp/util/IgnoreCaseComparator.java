package com.carapp.util;

import java.util.Comparator;

public	class IgnoreCaseComparator implements Comparator<String> {
public int compare(String strA, String strB) {
  return strA.compareToIgnoreCase(strB);
}

}

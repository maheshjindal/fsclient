package com.maheshjindal.scala.fsclient.utils

import java.util.Scanner

object IOUtils {
  def getScannerInstance() : Scanner =  {
    new Scanner(System.in);
  }
}

package com.maheshjindal.scala.fsclient.storage

abstract class DirReference(val parentPath: String, val name: String) {
  def path = parentPath + Directory.SEPARATOR + name
  def toDirectory:Directory
  def getType:String
}

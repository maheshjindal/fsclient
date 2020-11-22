package com.maheshjindal.scala.fsclient.storage

import com.maheshjindal.scala.fsclient.fs.File

abstract class DirReference(val parentPath: String, val name: String) {
  def path: String = {
    val _separator = if(Directory.ROOT_PATH.equals(parentPath)) "" else Directory.SEPARATOR
    parentPath + _separator + name
  }
  def toDirectory:Directory
  def toFile:File
  def getType:String
  def isDirectory: Boolean = matchRefType("Directory")
  def isFile: Boolean = matchRefType("File")
  private def matchRefType(refType:String):Boolean = {
    getType.equalsIgnoreCase(refType)
  }
}

package com.maheshjindal.scala.fsclient.fs

import java.nio.file.FileSystemException

import com.maheshjindal.scala.fsclient.storage.{DirReference, Directory}


class File(override val parentPath: String, override val name: String,contents:String) extends DirReference(parentPath,name){
  override def toDirectory: Directory = throw new FileSystemException("A file can't be converted to a directory")

  def toFile:File = this
  def getContents = contents

  override def getType: String = "File"
  def setContents(newContents:String): File ={
    new File(parentPath,name,newContents)
  }
  def appendContents(newContents:String):File = {
    setContents(contents + "\n"+newContents)
  }
}
object File {
  def empty(parentPath: String, name: String ): File = new File(parentPath,name,"")
}

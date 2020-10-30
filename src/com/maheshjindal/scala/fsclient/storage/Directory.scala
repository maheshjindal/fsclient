package com.maheshjindal.scala.fsclient.storage

import com.maheshjindal.scala.fsclient.utils.FSClientEnvProperties

import scala.annotation.tailrec


class Directory(override val parentPath: String, override val name: String,val dirContents: List[DirReference])
  extends DirReference(parentPath,name){

  def findDescandants(existingDirs: List[String]):Directory = {
    if (existingDirs.isEmpty){
      this
    } else {
      findEntry(existingDirs.head).toDirectory.findDescandants(existingDirs.tail)
    }

  }

  def replaceEntry(dirName: String, directory: DirReference): Directory = {
    new Directory(parentPath,name,dirContents.filter(c => c.name.equals(dirName)) :+ directory)
  }

  def addEntry(newEntry: DirReference) = {
      new Directory(parentPath,name,dirContents :+ newEntry)
  }

  def findEntry(name: String):DirReference = {

    @tailrec
    def _findEntry(name:String, dirRefList: List[DirReference]) : DirReference = {
      if(dirRefList.isEmpty) null
      else if(dirRefList.head.name.equals(name)) dirRefList.head
      else _findEntry(name,dirRefList.tail)
    }
    _findEntry(name,dirContents)
  }


  def hasEntry(name:String):Boolean = findEntry(name) != null

  def getAllDirsInPath():List[String] = path.substring(1).split(Directory.SEPARATOR).toList.filter(d => !d.isEmpty)

  override def toDirectory: Directory = this
  def getType:String = "Directory"
}


object Directory {
  val SEPARATOR = FSClientEnvProperties.SEPARATOR
  val ROOT_PATH = "/"

  def ROOT : Directory = empty("","")
  def empty(parentPath: String, name: String ): Directory = new Directory(parentPath,name,List())

}
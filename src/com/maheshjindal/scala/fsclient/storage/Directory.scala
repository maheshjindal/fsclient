package com.maheshjindal.scala.fsclient.storage

import com.maheshjindal.scala.fsclient.utils.FSClientEnvProperties

class Directory(override val parentPath: String, override val name: String,val dirContents: List[String])
  extends DirReference(parentPath,name){



}


object Directory {
  val SEPERATOR = FSClientEnvProperties.SEPERATOR
  val ROOT_PATH = "/"

  def ROOT : Directory = emptyPath("","")
  def emptyPath(parentPath: String, name: String ): Directory = new Directory(parentPath,name,List())

}
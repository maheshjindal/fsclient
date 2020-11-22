package com.maheshjindal.scala.fsclient.commands
import com.maheshjindal.scala.fsclient.fs.File
import com.maheshjindal.scala.fsclient.statemanager.State
import com.maheshjindal.scala.fsclient.storage.Directory

import scala.annotation.tailrec

class Echo(args:Array[String]) extends Command {

  def createContent(args: Array[String], topIndex: Int) = {
    @tailrec
    def _createContentHeloper(currInd:Int, acc:String):String = {
      if(currInd >= topIndex) acc
      else _createContentHeloper(currInd+1, acc + " "+ args(currInd))
    }
    _createContentHeloper(0,"")
  }

  def getRootAfterEcho(currDir:Directory, path:List[String],contents:String, append:Boolean):Directory ={
    if(path.isEmpty) currDir
    else if (path.tail.isEmpty){
      val dirEntry = currDir.findEntry(path.head)
      if(dirEntry == null){
        currDir.addEntry(new File(currDir.path,path.head,contents))
      }else if(dirEntry.isDirectory)currDir
      else {
        if(append) {
          currDir.replaceEntry(path.head,dirEntry.asInstanceOf[File].toFile.appendContents(contents))
        }else{
          currDir.replaceEntry(path.head,dirEntry.asInstanceOf[File].toFile.setContents(contents))
        }
      }
    }else{
      val nextDir = currDir.findEntry(path.head).toDirectory
      val newNextDir = getRootAfterEcho(nextDir,path.tail,contents,append)
      if(nextDir == newNextDir)currDir
      else currDir.replaceEntry(path.head,newNextDir)

    }
  }
  def doEcho(state: State, contents: String, filename: String, append:Boolean) = {
    if(filename.contains(Directory.SEPARATOR)){
      state.setMessage("File name must not contain seperators")
    }else{
      val newRoot:Directory = getRootAfterEcho(state.root,state.workingDir.getAllDirsInPath() :+ filename,contents,append)
      if(newRoot == state.root)
        state.setMessage(filename+": no such file")
      else
        State(newRoot,newRoot.findDescandants(state.workingDir.getAllDirsInPath()))

    }
  }

  override def apply(state: State): State = {
  if(args.isEmpty) state
  else if(args.length == 1) state.setMessage(args(0))
    else{
    val operator = args(args.length -2)
    val filename = args(args.length - 1)
    val contents = createContent(args,args.length - 2)
    if(">>".equals(operator))
      doEcho(state,contents,filename,true)
    else if (">".equals(operator))
      doEcho(state,contents,filename,false)
    else
      state.setMessage(createContent(args,args.length))
  }
  }
}

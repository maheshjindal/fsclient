package com.maheshjindal.scala.fsclient

import com.maheshjindal.scala.fsclient.commands.Command
import com.maheshjindal.scala.fsclient.statemanager.State
import com.maheshjindal.scala.fsclient.storage.{DirReference, Directory}

abstract class CreateObjectEntry(objectName: String, recursive:Boolean=false) extends Command{
  final val invalidCharacters = "."

  override def apply(state: State): State = {
    val currentWorkingDir = state.workingDir
    if (currentWorkingDir.hasEntry(objectName)) {
      state.setMessage(s"Directory with name '$objectName' already exists.")
    }
    try {
      _validateDirectoryName()
    } catch {
      case e: IllegalArgumentException => state.setMessage(e.getMessage)
    }

    _doCreateObjectEntry(state,objectName)

    //    state
  }

  def _validateDirectoryName(): Unit = {
    if (!recursive && objectName.contains(Directory.SEPARATOR)) {
      throw new IllegalArgumentException(s"Directory name '$objectName' is invalid. Note: " +
        s"Please use --recursive option, to create directories recursively")
    }
    if (objectName.contains(invalidCharacters)) {
      throw new IllegalArgumentException(s"Characters [$invalidCharacters] are not allowed for the directory name.")
    }
  }

  def _doCreateObjectEntry(state:State, name: String) = {

    def updateDirStructure(currDir: Directory, path: List[String], newEntry: DirReference):Directory = {
      if(path.isEmpty)currDir.addEntry(newEntry)
      else{
        val headDirRef = currDir.findEntry(path.head).toDirectory
        currDir.replaceEntry(headDirRef.name,updateDirStructure(currDir,path.tail,newEntry))
      }
    }

    val workingDir = state.workingDir
    val existingDirs = workingDir.getAllDirsInPath
    val newDir = _createSpecificEntry(state,objectName)
    val updatedRootDir = updateDirStructure(state.root, existingDirs,newDir)
    val newWorkingDir = updatedRootDir.findDescandants(existingDirs)
    State(updatedRootDir,newWorkingDir)
  }
   def _createSpecificEntry(state: State, entryName: String) : DirReference
}

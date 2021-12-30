package com.maheshjindal.scala.fsclient.commands

import com.maheshjindal.scala.fsclient.statemanager.State
import com.maheshjindal.scala.fsclient.storage.Directory

class Rm(name: String) extends Command {

  def doRm(state: State, path: String): State = {

    def rmHelper(currentDir: Directory, path: List[String]): Directory = {
      if (path.isEmpty) currentDir
      else if (path.tail.isEmpty) currentDir.removeEntry(path.head)
      else {
        val nextDir = currentDir.findEntry(path.head)
        if (!nextDir.isDirectory) currentDir
        else {
          val newNextDir = rmHelper(nextDir.toDirectory, path.tail)
          if (newNextDir.equals(nextDir)) currentDir
          else currentDir.replaceEntry(path.head, newNextDir)
        }
      }
    }

    val tokens: List[String] = path.substring(1).split(Directory.SEPARATOR).toList
    val newRoot: Directory = rmHelper(state.root, tokens)
    if (newRoot == state.root)
      state.setMessage(path + " No such file or directory... ")
    else
      State(newRoot, newRoot.findDescandants(state.workingDir.path.substring(1)))
  }

  def _getAbsPath(workingDir: Directory, dir: String): String = {
    if (dir.startsWith(Directory.SEPARATOR)) dir
    else if (workingDir.isRoot) workingDir.path + Directory.SEPARATOR + dir
    else workingDir.path + Directory.SEPARATOR + dir
  }

  override def apply(state: State): State = {
    val workingDir = state.workingDir
    val absPath = _getAbsPath(workingDir, name)
    if (Directory.ROOT_PATH.equals(absPath)) {
      state.setMessage("Unsupported Operation...")
    } else {
      doRm(state, absPath)
    }
  }
}

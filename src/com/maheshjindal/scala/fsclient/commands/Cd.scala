package com.maheshjindal.scala.fsclient.commands

import com.maheshjindal.scala.fsclient.statemanager.State
import com.maheshjindal.scala.fsclient.storage.{DirReference, Directory}

import scala.annotation.tailrec

class Cd(dir: String) extends Command {
  def _getAbsPath(workingDir: Directory, dir: String): String = {
    if (dir.startsWith(Directory.SEPARATOR)) dir
    else if (workingDir.isRoot) workingDir.path + Directory.SEPARATOR + dir
    else workingDir.path + Directory.SEPARATOR + dir
  }

  override def apply(state: State): State = {
    val workingDir = state.workingDir
    val absPath = _getAbsPath(workingDir: Directory, dir: String)
    val destDir = findDestinationDirReference(state.root, absPath)
    if (destDir == null || !destDir.isDirectory) {
      state.setMessage("Destination " + dir + " is not a directory!")
    } else {
      State(state.root, destDir.toDirectory)
    }
  }

  def findDestinationDirReference(root: Directory, path: String): DirReference = {
    @tailrec
    def _findDestDirRef(currDir: Directory, path: List[String]): DirReference = {
      if (path.isEmpty || path.head.isEmpty) currDir
      else if (path.tail.isEmpty) currDir.findEntry(path.head)
      else {
        val nextDir = currDir.findEntry(path.head)
        if (nextDir == null) null
        else _findDestDirRef(nextDir.toDirectory, path.tail)
      }
    }

    @tailrec
    def collapseEmptyTokens(path: List[String], result: List[String]): List[String] = {
      if (path.isEmpty) result
      else if (".".equals(path.head)) collapseEmptyTokens(path.tail, result)
      else if ("..".equals(path.head)) {
        if (result.isEmpty) null
        else collapseEmptyTokens(path.tail, result.init)
      } else collapseEmptyTokens(path.tail, result :+ path.head)
    }

    val tokens: List[String] = path.substring(1).split(Directory.SEPARATOR).toList
    val newTokens = collapseEmptyTokens(tokens, List())
    _findDestDirRef(root, newTokens)
  }
}

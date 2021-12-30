package com.maheshjindal.scala.fsclient.commands

import com.maheshjindal.scala.fsclient.statemanager.State

class Cat(fileName: String) extends Command {
  override def apply(state: State): State = {
    val workingDir = state.workingDir
    val dirEntry = workingDir.findEntry(fileName)
    if (dirEntry == null || !dirEntry.isFile) {
      state.setMessage(s"$fileName : no such file")
    } else {
      state.setMessage(dirEntry.toFile.getContents)
    }
  }
}

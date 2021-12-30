package com.maheshjindal.scala.fsclient.commands

import com.maheshjindal.scala.fsclient.statemanager.State
import com.maheshjindal.scala.fsclient.storage.{DirReference, Directory}

class MkDir(dirName: String, recursive: Boolean = false) extends CreateObjectEntry(dirName, recursive) {
  override def _createSpecificEntry(state: State, entryName: String): DirReference = {
    Directory.empty(state.workingDir.path, entryName)
  }
}

package com.maheshjindal.scala.fsclient.commands
import com.maheshjindal.scala.fsclient.CreateObjectEntry
import com.maheshjindal.scala.fsclient.statemanager.State
import com.maheshjindal.scala.fsclient.storage.DirReference
import com.maheshjindal.scala.fsclient.fs.File

class Touch(fileName: String) extends CreateObjectEntry(fileName) {
  override def _createSpecificEntry(state: State, entryName: String): DirReference = {
    File.empty(state.workingDir.path,entryName)
  }
}

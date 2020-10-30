package com.maheshjindal.scala.fsclient.commands
import com.maheshjindal.scala.fsclient.statemanager.State

class Pwd extends Command {
  override def apply(state: State): State = state.setMessage(state.workingDir.path)
}

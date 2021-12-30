package com.maheshjindal.scala.fsclient.commands
import com.maheshjindal.scala.fsclient.statemanager.State

class IncompleteCommand(name:String) extends Command {
  override def apply(state: State): State = {
    state.setMessage(s"[INCOMPLETE COMMAND] - Please check the complete syntax of command with name '$name'")
  }
}

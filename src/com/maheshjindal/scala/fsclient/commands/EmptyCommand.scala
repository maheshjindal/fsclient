package com.maheshjindal.scala.fsclient.commands
import com.maheshjindal.scala.fsclient.statemanager.State

class EmptyCommand extends Command {
  override def apply(state: State): State = state.setMessage("[EMPTY COMMAND] Please provide a valid command")
}

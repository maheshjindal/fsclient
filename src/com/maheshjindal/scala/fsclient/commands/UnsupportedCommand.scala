package com.maheshjindal.scala.fsclient.commands
import com.maheshjindal.scala.fsclient.statemanager.State

class UnsupportedCommand extends Command{
  override def apply(state: State): State = state.setMessage("| Command Not Found |")
}

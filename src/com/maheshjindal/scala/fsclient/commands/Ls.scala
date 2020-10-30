package com.maheshjindal.scala.fsclient.commands
import com.maheshjindal.scala.fsclient.statemanager.State
import com.maheshjindal.scala.fsclient.storage.DirReference

class Ls extends Command {

  def _getFormattedList(dirContents: List[DirReference]):String = {
    if(dirContents.isEmpty) return ""
    else{
      val headDir = dirContents.head
      headDir.name + "["+ headDir.getType +"] \n "+ _getFormattedList(dirContents.tail) + "\n"
    }
  }

  override def apply(state: State): State = {
    val dirContents = state.workingDir.dirContents
    val formattedList = _getFormattedList(dirContents)
    state.setMessage(formattedList)
  }
}

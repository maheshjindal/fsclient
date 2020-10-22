package com.maheshjindal.scala.fsclient.statemanager

import com.maheshjindal.scala.fsclient.storage.Directory
import com.maheshjindal.scala.fsclient.utils.FSClientEnvProperties

/**
 * To implement tracker functionality which retains last 10 commands
 */
class State(val root: Directory, val workingDir: Directory, val prevCmdOutput: String) {

  def show: Unit = {
    if (!prevCmdOutput.isEmpty) println(prevCmdOutput)
    print(State.SHELL_TOKEN.concat(" > "))
  }

  def setMessage(message: String): State = State(root, workingDir, message)

}

object State {
  val SHELL_TOKEN = FSClientEnvProperties.SHELL_TOKEN

  def apply(root: Directory, workingDir: Directory, prevCmdOutput: String = ""): State = new State(root, workingDir, prevCmdOutput)

}
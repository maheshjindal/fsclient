package com.maheshjindal.scala.fsclient.commands

import com.maheshjindal.scala.fsclient.statemanager.State

trait Command {
  def apply(state: State): State
}

object Command {
  def from(inputPath:String):Command = {
    val command = CommandParser.parse(inputPath)
    command
  }
}

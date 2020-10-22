package com.maheshjindal.scala.fsclient.commands

class CommandParser {

  def parse(input: String) = {
    val tokens: Array[String] = input.split(" ")
    if (!tokens.isEmpty && tokens.length > 0) {
      val headCmd = tokens(0)
      val operation = Operation.valueOf(headCmd)

      operation match {
        case Operation.MKDIR => new MkDir(tokens(1))
        case Operation.TOUCH =>  new Touch
        case _ => new CommandNotFound
      }
    }
  }
}

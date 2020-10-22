package com.maheshjindal.scala.fsclient

import java.util.concurrent.ThreadPoolExecutor

import com.maheshjindal.scala.fsclient.commands.Command
import com.maheshjindal.scala.fsclient.statemanager.State
import com.maheshjindal.scala.fsclient.storage.Directory
import com.maheshjindal.scala.fsclient.utils.IOUtils

object FSClient {
  def main(args: Array[String]): Unit = {

    val _root = Directory.ROOT
    var _state = State(_root,_root)
    val _scanner = IOUtils.getScannerInstance()

    while (true){
      _state.show
      val userInput = _scanner.nextLine()

      // Adds the new command entered by user
      _state = Command.from(userInput).apply(_state)
    }
  }
}
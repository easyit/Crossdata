/*
 * Licensed to STRATIO (C) under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  The STRATIO (C) licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.stratio.crossdata.connectors

import java.util.concurrent.{TimeUnit, Executors}

import akka.actor.Actor
import com.stratio.crossdata.communication.HeartbeatSig
import org.apache.log4j.Logger

trait HeartbeatActor extends Actor {

  lazy val logger = Logger.getLogger(classOf[HeartbeatActor])
  private val scheduler = Executors.newSingleThreadScheduledExecutor()

  private val callback = new Runnable {
    def run:Unit = {
      self ! new HeartbeatSig()
    }
  }

  val initialDelay:Long=0
  val period:Long=500

 /**
  * command: the task to execute.
  * initialDelay: the time to delay first execution.
  * period: the period between successive executions.
  * unit: the time unit of the initialDelay and period parameters.
  */
  scheduler.scheduleAtFixedRate(callback, initialDelay, period, TimeUnit.MILLISECONDS)

  def receive: Receive = {
    case heartbeat: HeartbeatSig =>  handleHeartbeat(heartbeat)
  }

  def handleHeartbeat(heartbeat: HeartbeatSig):Unit = {
    logger.debug("HeartbeatActor receives a heartbeat message")
  }

}

package com.stratio.meta.server.query

import akka.testkit.{ImplicitSender, DefaultTimeout, TestKit}
import akka.actor.{Props, ActorSystem}
import com.typesafe.config.ConfigFactory
import org.scalatest.FunSuiteLike
import com.stratio.meta.server.actors._
import scala.concurrent.duration._
import com.stratio.meta.core.engine.Engine
import com.stratio.meta.server.utilities._
import com.stratio.meta.server.config.BeforeAndAfterCassandra
import com.stratio.meta.common.ask.Query
import akka.pattern.ask
import org.testng.Assert._
import com.stratio.meta.common.result.QueryResult
import com.stratio.meta.communication.ACK

class BasicQueryActorTest extends TestKit(ActorSystem("TestKitUsageSpec",ConfigFactory.parseString(TestKitUsageSpec.config)))
                                  with ImplicitSender with DefaultTimeout with FunSuiteLike with BeforeAndAfterCassandra{

  lazy val engine:Engine = createEngine.create()

  lazy val queryRef = system.actorOf(Props(classOf[QueryActor],engine))

  override def beforeCassandraFinish() {
    shutdown(system)
  }

  override def afterAll() {
    super.afterAll()
    engine.shutdown()
  }

  test ("Unknown message"){
    within(5000 millis){
      queryRef ! 1
      val result = expectMsgClass(classOf[QueryResult])
      assertTrue(result.hasError, "Expecting error message")
    }
  }

  test ("Create catalog"){
    within(5000 millis){
      val query = "create KEYSPACE ks_demo WITH replication = {class: SimpleStrategy, replication_factor: 1};"
      queryRef ! new Query("query-actor", "", query, "test")
      expectMsgClass(classOf[ACK])
      val result = expectMsgClass(classOf[QueryResult])
      assertFalse(result.hasError, "Error not expected: " + result.getErrorMessage)
    }
  }

  test ("Create existing KS"){
    within(5000 millis){
      val query = "create KEYSPACE ks_demo WITH replication = {class: SimpleStrategy, replication_factor: 1};"
      queryRef ! new Query("query-actor", "", query, "test")
      val result = expectMsgClass(classOf[QueryResult])
      assertTrue(result.hasError, "Expecting ks exists error")
    }
  }

  test ("Use keyspace"){
    within(5000 millis){
      val query = "use ks_demo ;"
      queryRef ! new Query("query-actor", "", query, "test")
      expectMsgClass(classOf[ACK])
      val result = expectMsgClass(classOf[QueryResult])
      assertFalse(result.hasError, "Error not expected: " + result.getErrorMessage)
    }
  }

  test ("Use non-existing keyspace"){
    within(5000 millis){
      val query = "use unknown ;"
      queryRef ! new Query("query-actor", "", query, "test")
      val result = expectMsgClass(classOf[QueryResult])
      assertTrue(result.hasError, "Expecting ks not exists error")
    }
  }

  test ("Insert into non-existing table"){
    within(5000 millis){
      val query = "insert into demo (field1, field2) values ('test1','text2');"
      queryRef ! new Query("query-actor", "ks_demo", query, "test")
      val result = expectMsgClass(classOf[QueryResult])
      assertTrue(result.hasError, "Expecting table not exists")
    }
  }

  test ("Create table"){
    within(5000 millis){
      val query = "create TABLE demo (field1 varchar PRIMARY KEY , field2 varchar);"
      queryRef ! new Query("query-actor", "ks_demo", query, "test")
      expectMsgClass(classOf[ACK])
      val result = expectMsgClass(classOf[QueryResult])
      assertFalse(result.hasError, "Error not expected: " + result.getErrorMessage)
    }
  }

  test ("Create existing table"){
    within(5000 millis){
      val query = "create TABLE demo (field1 varchar PRIMARY KEY , field2 varchar);"
      queryRef ! new Query("query-actor", "ks_demo", query, "test")
      val result = expectMsgClass(classOf[QueryResult])
      assertTrue(result.hasError, "Expecting table exists")
    }
  }

  test ("Insert into table"){
    within(5000 millis){
      val query = "insert into demo (field1, field2) values ('text1','text2');"
      queryRef ! new Query("query-actor", "ks_demo", query, "test")
      expectMsgClass(classOf[ACK])
      val result = expectMsgClass(classOf[QueryResult])
      assertFalse(result.hasError, "Error not expected: " + result.getErrorMessage)
    }
  }

  test ("Select"){
    within(5000 millis){
      val query = "select * from demo ;"
      queryRef ! new Query("query-actor", "ks_demo", query, "test")
      expectMsgClass(classOf[ACK])
      val result = expectMsgClass(classOf[QueryResult])
      assertFalse(result.hasError, "Error not expected: " + result.getErrorMessage)
      assertEquals(result.getResultSet.size(), 1, "Cannot retrieve data")
      val r = result.getResultSet.iterator().next()
      assertEquals(r.getCells.get("field1").getValue, "text1", "Invalid row content")
      assertEquals(r.getCells.get("field2").getValue, "text2", "Invalid row content")
    }
  }

  test ("Drop table"){
    within(5000 millis){
      val query = "drop table demo ;"
      queryRef ! new Query("query-actor", "ks_demo", query, "test")
      expectMsgClass(classOf[ACK])
      val result = expectMsgClass(classOf[QueryResult])
      assertFalse(result.hasError, "Error not expected: " + result.getErrorMessage)
    }
  }

  test ("Drop keyspace"){
    within(5000 millis){
      val query = "drop keyspace ks_demo ;"
      queryRef ! new Query("query-actor", "ks_demo", query, "test")
      expectMsgClass(classOf[ACK])
      val result = expectMsgClass(classOf[QueryResult])
      assertFalse(result.hasError, "Error not expected: " + result.getErrorMessage)
    }
  }

  test ("Drop non-existing keyspace"){
    within(5000 millis){
      val query = "drop keyspace ks_demo ;"
      queryRef ! new Query("query-actor", "ks_demo", query, "test")
      val result = expectMsgClass(classOf[QueryResult])
      assertTrue(result.hasError, "Expecting keyspace not exists")
    }
  }

}



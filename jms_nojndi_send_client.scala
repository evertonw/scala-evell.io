//*
//*
//* If using Oracle XE, the following jar files are required in your classpath to
//* run this example:
//*
//* .;%DB_HOME%/RDBMS/jlib/aqapi13.jar;%DB_HOME%/RDBMS/jlib/jmscommon.jar;
//* %DB_HOME%/RDBMS/jlib/xdb.jar;%DB_HOME%/lib/xmlparserv2.jar;
//* %DB_HOME%/jdbc/lib/ojdbc14.jar;%DB_HOME%/jlib/orai18n.jar;%DB_HOME%/jlib/jndi.jar;%J2EE_HOME%/lib/jta.jar
//*
//*/

// Java infrastructure packages
import java.lang._

// JMS packages
import oracle.AQ._
import oracle.jms._
import javax.jms._
import java.sql._
//import java.sql.DriverManager;
//import java.sql.Connection;
//import java.sql.SQLException;

import scala.Array 

/* -------------------------------------------------------
* Send
* ------------------------------------------------------- */
object  SimpleJMSClientSend {

// Here's the XML payload to put in a text message
val SOME_XML = "\n" + "\n" + " \n" + " John\n" + " W\n" + " Doe\n" + " \n"+ ""


//var tcfact = None: Option[TopicConnectionFactory]
//var tconn  = None: Option[TopicConnection]
//var topic  = None: Option[Topic]
//var tsess  = None: Option[TopicSession]
//var publisher = None: Option[TopicPublisher]
//var subscriber = None: Option[TopicSubscriber]
var txtmsg   =  ""

val DEFAULT_DESTNAME = "JMSDEMO_TOPIC"
val DEFAULT_DBHOST = "127.0.0.1"
val DEFAULT_DBPORT = "1521"
val DEFAULT_DBSID = "CSDBEWE"
val DEFAULT_DBDRIVER = "thin"
val DEFAULT_DBUSER = "jmsuser"
val DEFAULT_DBPASSWORD = "jmsuser"


    def sendMessage(theMessage: String) {
      sendMessage(
        destname = DEFAULT_DESTNAME,
        dbhost = DEFAULT_DBHOST,
        dbport = DEFAULT_DBPORT,
        dbsid = DEFAULT_DBSID,
        dbdriver = DEFAULT_DBDRIVER,
        dbuser = DEFAULT_DBUSER,
        dbpassword = DEFAULT_DBPASSWORD,
        messageText = theMessage)
    }
 

    def sendMessage(destname : String, dbhost : String, dbport : String, dbsid : String, dbdriver : String, 
                    dbuser : String, dbpassword : String , 
                    messageText : String) {
          println("\n-------------------------------------------------------------")
          println("OEMS.155 - simple JMS Send / JMS 1.02 / Database AQ / no JNDI")
          println("-------------------------------------------------------------")


	try {
	   // get connection factory - we are not going through JNDI here
	   val tcfact = AQjmsFactory.getTopicConnectionFactory(dbhost, dbsid, dbport.toInt, dbdriver)
	   println("Connection factory = " + tcfact.toString())

	   // create connection
	   val tconn = tcfact.createTopicConnection(dbuser,dbpassword)
	   println("Created connection = " + tconn.toString())

	   // create session
	   val tsess = tconn.createTopicSession(true, Session.CLIENT_ACKNOWLEDGE)
	   println("Created session = " + tsess.toString())

	   // start connection
	   tconn.start() 
	   println("started connection")

	   // get topic
	   val topicsess : AQjmsSession = tsess.asInstanceOf[AQjmsSession] 
	   val topic = topicsess.getTopic(dbuser,destname) 
	   println("Got topic = " + topic.toString())
	   println("started session = " + tsess.toString())

	  val publisher = tsess.createPublisher(topic)
	  var txtmsg = tsess.createTextMessage(SOME_XML) 
	  println("\ndestination: " + topic + "\nmessage :\n\n" + SOME_XML)
	  publisher.publish(topic, txtmsg) 
	  tsess.commit() 
	  println("\nmessage was sent with ID="+txtmsg.getJMSMessageID())

	// Cleaning up before exiting
	   //((AQjmsDestination)topic).stop(tsess, true, true, false)
	   tsess.close() 
	   tconn.close()
	} catch {
	   case jmse : JMSException =>
		  jmse.printStackTrace(System.err)
		  System.exit(0)
	   case _ : Throwable =>
	          println("** Problem terminating session and connection:\n")
	} 

}

/*-----------------------------------------------------------------------
* usage
* prints program usage
*----------------------------------------------------------------------*/

def usage() {
   println("\nUsage: Send ")
   println("Ex : Send JMSDEMO_TOPIC")
}

def main (args: Array[String]) = {
      sendMessage( theMessage = "hello from Scala sendMessage() with 1 arg")
}


}

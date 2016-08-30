package movio.api.kafka_0_10

import org.apache.kafka.clients.producer.KafkaProducer
import scala.util.Try
import scala.concurrent.duration._
import java.util.concurrent.TimeUnit

trait Producer[K, T] {
  def kafkaProducer: KafkaProducer[String, String]

  def send(single: T, tenant: String): Try[T]
  def send(batch: Seq[T], tenant: String): Try[Seq[T]]

  def sendWrapped(single: K, tenant: String): Try[K]
  def sendWrapped(batch: Seq[K], tenant: String): Try[Seq[K]]

  // If the kafkaProducer has not been flushed during send this will block
  // until all messages have been sent. It's recommended to flush in your
  // send implementations
  // Default 5 mins
  def close(): Unit = close(Duration(5, MINUTES))

  def close(timeout: Duration): Unit =
    kafkaProducer.close(timeout.toMillis, TimeUnit.MILLISECONDS);
}

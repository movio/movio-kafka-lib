package movio.api.kafka_0_8

import kafka.producer._
import scala.util.{ Try }

trait KafkaProducer {
  def producerConfig: ProducerConfig

  def producer: Producer[String, String]

  def send[T](single: T, tenant: String): Try[T] 
  def send[T](batch: Seq[T], tenant: String): Try[Seq[T]]

  def sendWrapped[T](single: T, tenant: String): Try[T]
  def sendWrapped[T](batch: Seq[T], tenant: String): Try[Seq[T]]
}

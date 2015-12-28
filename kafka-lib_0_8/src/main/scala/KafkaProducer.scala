package movio.api.kafka_0_8

import kafka.producer._
import scala.util.Try

trait KafkaProducer[K, T] {
  def producerConfig: ProducerConfig

  def producer: Producer[String, String]

  def send(single: T, tenant: String): Try[T]
  def send(batch: Seq[T], tenant: String): Try[Seq[T]]

  def sendWrapped(single: K, tenant: String): Try[K]
  def sendWrapped(batch: Seq[K], tenant: String): Try[Seq[K]]
}

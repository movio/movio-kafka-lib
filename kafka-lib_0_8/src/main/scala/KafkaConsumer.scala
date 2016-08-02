package movio.api.kafka_0_8

import kafka.consumer._
import scala.util.Try

trait KafkaConsumer[T] {
  def topicFilter: TopicFilter

  def consumer: ConsumerConnector
  def consumerConfig: ConsumerConfig

  def stream: KafkaStream[String, String]

  def processBatchThenCommit(
    processor: Map[String, Seq[T]] ⇒ Try[Map[String, Seq[T]]],
    batchSize: Int
  ): Try[Map[String, Seq[T]]]

  def processBatchWithKeysThenCommit(
    processor: Map[String, Seq[(String, Option[T])]] ⇒ Try[Map[String, Seq[(String, Option[T])]]],
    batchSize: Int
  ): Try[Map[String, Seq[(String, Option[T])]]]

  def shutdown(): Unit
}

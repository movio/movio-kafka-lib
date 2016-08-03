package movio.api.kafka_0_8

import kafka.consumer._
import scala.util.Try

trait KafkaConsumer[T] {
  def topicFilter: TopicFilter

  def consumer: ConsumerConnector
  def consumerConfig: ConsumerConfig

  def stream: KafkaStream[String, String]

  /**
    * Process a batch of messages with given processor function and commit
    * offsets if it succeeds. Messages with null payloads are ignored.
    *
    * @param processor processor function that takes a map of messages for different tenants
    * @param batchSize the maximum number of messages to process
    */
  def processBatchThenCommit(
    processor: Map[String, Seq[T]] ⇒ Try[Map[String, Seq[T]]],
    batchSize: Int
  ): Try[Map[String, Seq[T]]]

  /**
    * Process a batch of messages with given processor function and commit
    * offsets if it succeeds.
    *
    * Each message is a tuple of the key and the payload deserialised to
    * `Option[T]` which is `None` when the message has a null payload.
    *
    * @param processor processor function that takes a map of messages for different tenants
    * @param batchSize the maximum number of messages to process
    */
  def processBatchWithKeysThenCommit(
    processor: Map[String, Seq[(String, Option[T])]] ⇒ Try[Map[String, Seq[(String, Option[T])]]],
    batchSize: Int
  ): Try[Map[String, Seq[(String, Option[T])]]]

  def shutdown(): Unit
}

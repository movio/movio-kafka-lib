package movio.api.kafka_0_10

import org.apache.kafka.clients.consumer.KafkaConsumer
import scala.util.Try

trait Consumer[T] {

  def kafkaConsumer: KafkaConsumer[String, String]
  
  def processBatch(
    processor: Map[String, Seq[T]] ⇒ Try[Map[String, Seq[T]]],
    batchSize: Int
  ): Try[Map[String, Seq[T]]]

  def processBatchWithKeys(
    processor: Map[String, Seq[(String, Option[T])]] ⇒ Try[Map[String, Seq[(String, Option[T])]]],
    batchSize: Int
  ): Try[Map[String, Seq[(String, Option[T])]]]

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

  def close(): Unit = kafkaConsumer.close()
}

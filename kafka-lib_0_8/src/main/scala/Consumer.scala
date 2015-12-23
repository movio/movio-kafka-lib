package movio.api.kafka_0_8

import kafka.consumer._
import scala.util.{ Try }

trait KafkaConsumer {
  def topicFilter: TopicFilter

  def consumer: ConsumerConnector
  def consumerConfig: ConsumerConfig

  def stream: KafkaStream[String, String]

  def processBatchThenCommit[T](
    processor: Map[String, Seq[T]] ⇒ Try[Map[String, Seq[T]]],
    batchSize: Int 
  ): Try[Map[String, Seq[T]]]

  def shutdown:Unit 
}

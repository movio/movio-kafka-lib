package movio.api.kafka_0_8

case class KafkaProducerException(
  message: String,
  ex: Throwable
) extends RuntimeException(message, ex)

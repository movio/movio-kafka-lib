package movio.api.kafka_0_8

case class KafkaProducerException(
  message: String,
  exception: Throwable
) extends RuntimeException(message, exception)

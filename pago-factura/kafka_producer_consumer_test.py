from confluent_kafka import DeserializingConsumer, KafkaException
from confluent_kafka.schema_registry import SchemaRegistryClient
from confluent_kafka.schema_registry.avro import AvroDeserializer
import unittest

# Importo las funciones de mi productor
from producer_factura import enviar_id_factura, load_schema

class KafkaProducerConsumerDockerTest(unittest.TestCase):

    def setUp(self):
        # Configuración de Kafka y Schema Registry que ya está corriendo en Docker
        self.bootstrap_servers = 'localhost:9092'
        self.schema_registry_url = 'http://localhost:8081'
        self.topic = 'facturasPagadas'

    def test_produce_and_consume_message_avro(self):

        key_schema_str = load_schema("FacturaPagadaKey.avsc")
        value_schema_str = load_schema("FacturaPagadaValue.avsc")

        # Producir un mensaje al topic
        enviar_id_factura(self.topic, 13)

        # Configurar Schema Registry Client y deserializador Avro para el consumidor
        schema_registry_client = SchemaRegistryClient({'url': self.schema_registry_url})
        key_deserializer = AvroDeserializer(schema_registry_client, schema_str=key_schema_str)
        value_deserializer = AvroDeserializer(schema_registry_client, schema_str=value_schema_str)

        # Configurar el consumidor para leer el mensaje
        consumer_config = {
            'bootstrap.servers': self.bootstrap_servers,
            'group.id': 'test_group',
            'auto.offset.reset': 'latest',
            'key.deserializer': key_deserializer,
            'value.deserializer': value_deserializer
        }
        consumer = DeserializingConsumer(consumer_config)
        consumer.subscribe([self.topic])

        msg = None
        try:
            # Intento leer un mensaje desde Kafka (espero 10 segundos)
            msg = consumer.poll(10.0)

            if msg is None:
                self.fail("No se recibió ningún mensaje dentro del tiempo esperado")
            if msg.error():
                raise KafkaException(msg.error())
                
            # Verificar el contenido del mensaje leído
            key = msg.key()
            value = msg.value()

            # Verificamos los valores que enviamos con el productor
            self.assertEqual(key, {"idFactura": 13})
            self.assertEqual(value, {"idFactura": 13})

        except KafkaException as e:
            self.fail(f"Error en el consumidor de Kafka: {e}")
        
        finally:
            consumer.close()

if __name__ == "__main__":
    unittest.main()

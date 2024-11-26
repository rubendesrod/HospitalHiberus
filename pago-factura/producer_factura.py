from confluent_kafka import SerializingProducer
from confluent_kafka.schema_registry.avro import AvroSerializer
from confluent_kafka.schema_registry import SchemaRegistryClient
import sys

def load_schema(file_path):
    try:
        with open(file_path, 'r') as schema_file:
            return schema_file.read()
    except Exception as e:
        raise Exception(f"Error al cargar el esquema: {e}")

def kafka_producer(key_schema_str, value_schema_str):
    schema_registry_config = {
        'url': 'http://localhost:8081'
    }
    schema_registry_client = SchemaRegistryClient(schema_registry_config)

    key_serializer = AvroSerializer(schema_registry_client, key_schema_str)
    value_serializer = AvroSerializer(schema_registry_client, value_schema_str)

    producer_config = {
        'bootstrap.servers': 'localhost:9092',
        'key.serializer': key_serializer,
        'value.serializer': value_serializer
    }

    return SerializingProducer(producer_config)

def enviar_id_factura(topic, id_factura):
    try:
        id_factura = int(id_factura)
        key = {"idFactura": id_factura}
        value = {"idFactura": id_factura}

        key_schema_str = load_schema("FacturaPagadaKey.avsc")
        value_schema_str = load_schema("FacturaPagadaValue.avsc")
        producer = kafka_producer(key_schema_str, value_schema_str)

        producer.produce(topic=topic, key=key, value=value)
        producer.flush()
        print(f"Mensaje enviado al topic '{topic}': Key={key}, Value={value}")
    except Exception as e:
        print(f"Error al enviar el mensaje: {e}")


if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Uso: python producer_factura.py <idFactura>")
        sys.exit(1)

    topic = "facturasPagadas"
    id_factura = sys.argv[1]
    enviar_id_factura(topic, id_factura)

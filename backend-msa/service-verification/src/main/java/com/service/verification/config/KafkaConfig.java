    package com.service.verification.config;


    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.service.common.dto.kafka.CreatedEmailVerificationMsg;
    import com.service.common.dto.kafka.CreateEmailVerificationMsg;
    import com.service.common.dto.kafka.DeleteVerificationMsg;
    import lombok.extern.slf4j.Slf4j;
    import org.apache.kafka.clients.consumer.ConsumerConfig;
    import org.apache.kafka.clients.producer.ProducerConfig;
    import org.apache.kafka.common.serialization.Deserializer;
    import org.apache.kafka.common.serialization.Serializer;
    import org.apache.kafka.common.serialization.StringDeserializer;
    import org.apache.kafka.common.serialization.StringSerializer;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
    import org.springframework.kafka.core.*;

    import java.util.HashMap;
    import java.util.Map;

    @Slf4j
    @Configuration
    public class KafkaConfig {

        @Value("${spring.kafka.bootstrap-servers}")
        private String kafkaBroker;

        private final ObjectMapper objectMapper = new ObjectMapper();

        // Producer
        private <T> ProducerFactory<String, T> createProducerFactory(Class<? extends Serializer<T>> serializerClass) {
            Map<String, Object> config = new HashMap<>();
            config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBroker);
            config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, serializerClass);
            return new DefaultKafkaProducerFactory<>(config);
        }

        // Serializer
        public static class GenericKafkaSerializer<T> implements Serializer<T> {
            private final ObjectMapper objectMapper = new ObjectMapper();
            @Override
            public byte[] serialize(String topic, T data) {
                try {
                    return objectMapper.writeValueAsBytes(data);
                } catch (Exception e) {
                    throw new RuntimeException(data.getClass().getSimpleName() + " 직렬화 중 오류 발생", e);
                }
            }
        }

        // CreatedEmailVerificationProducer
        public static class CreatedEmailVerificationSerializer extends GenericKafkaSerializer<CreatedEmailVerificationMsg> {}
        @Bean(name = "createdEmailVerificationMsgKafkaTemplate")
        public KafkaTemplate<String, CreatedEmailVerificationMsg> createdEmailVerificationMsgKafkaTemplate() {
            return new KafkaTemplate<>(createProducerFactory(CreatedEmailVerificationSerializer.class));
        }

        // ##########################################################################################################################

        // Consumer
        private <T> ConsumerFactory<String, T> createConsumerFactory(Class<? extends Deserializer<T>> deserializerClass) {
            Map<String, Object> config = new HashMap<>();
            config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBroker);
            config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializerClass);
            config.put(ConsumerConfig.GROUP_ID_CONFIG, "verify");
            return new DefaultKafkaConsumerFactory<>(config);
        }

        // Deserializer
        public static class GenericKafkaDeserializer<T> implements Deserializer<T> {
            private final ObjectMapper objectMapper = new ObjectMapper();
            private final Class<T> targetClass;


            public GenericKafkaDeserializer(Class<T> targetClass) {
                this.targetClass = targetClass;
            }

            @Override
            public T deserialize(String topic, byte[] data) {
                try {
                    log.info("Received raw message: " + new String(data));
                    return objectMapper.readValue(data, targetClass);
                } catch (Exception e) {
                    throw new RuntimeException(targetClass.getSimpleName() + " 역직렬화 중 오류 발생", e);
                }
            }
        }

        // createEmailVerificationConsumer
        public static class CreateEmailVerificationMsgDeserializer extends GenericKafkaDeserializer<CreateEmailVerificationMsg> {
            public CreateEmailVerificationMsgDeserializer() {
                super(CreateEmailVerificationMsg.class);
            }
        }
        @Bean
        public ConsumerFactory<String, CreateEmailVerificationMsg> createEmailVerificationMsgConsumerFactory() {
            return createConsumerFactory(CreateEmailVerificationMsgDeserializer.class);
        }
        @Bean(name = "createEmailVerificationMsgKafkaListenerContainerFactory")
        public ConcurrentKafkaListenerContainerFactory<String, CreateEmailVerificationMsg> createEmailVerificationMsgKafkaListenerContainerFactory() {
            ConcurrentKafkaListenerContainerFactory<String, CreateEmailVerificationMsg> factory =
                    new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(createEmailVerificationMsgConsumerFactory());
            return factory;
        }

        // deleteEmailVerificationConsumer
        public static class DeleteVerificationMsgDeserializer extends GenericKafkaDeserializer<DeleteVerificationMsg> {
            public DeleteVerificationMsgDeserializer() {
                super(DeleteVerificationMsg.class);
            }
        }
        @Bean
        public ConsumerFactory<String, DeleteVerificationMsg> deleteVerificationMsgConsumerFactory() {
            return createConsumerFactory(DeleteVerificationMsgDeserializer.class);
        }
        @Bean(name = "deleteVerificationMsgKafkaListenerContainerFactory")
        public ConcurrentKafkaListenerContainerFactory<String, DeleteVerificationMsg> deleteVerificationMsgKafkaListenerContainerFactory() {
            ConcurrentKafkaListenerContainerFactory<String, DeleteVerificationMsg> factory =
                    new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(deleteVerificationMsgConsumerFactory());
            return factory;
        }
    }

package com.epam.crmgym.messaging.activemq;

import com.epam.crmgym.dto.client.TrainingSessionDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageProducer {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Value("${spring.artemis.embedded.topics}")
    private String topicName;


    public MessageProducer(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessageToTopicWithJwt(String jwtToken, TrainingSessionDTO sessionDTO) {

        try {
            String jsonSessionDTO = objectMapper.writeValueAsString(sessionDTO);

            jmsTemplate.setSessionTransacted(true);
            jmsTemplate.convertAndSend(topicName, jsonSessionDTO, message -> {
                message.setStringProperty("Authorization", "Bearer " + jwtToken);
                return message;
            });
            log.info("Message sent successfully to Topic.example");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            log.error("Error sending message to ActiveMQ", e);
        }



    }





}

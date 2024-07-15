package com.epam.trainerworkload.unit.messaging;


import com.epam.trainerworkload.config.JwtService;
import com.epam.trainerworkload.dto.TrainingSessionDTO;
import com.epam.trainerworkload.messaging.IntermediateQueueConsumer;
import com.epam.trainerworkload.service.TrainerSummaryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class IntermediateQueueConsumerTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private TrainerSummaryService trainerSummaryService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private IntermediateQueueConsumer consumer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }





    @Test
    public void testProcessTrainingSession_NonTextMessage() throws Exception {
        Message message = mock(Message.class);

        consumer.processTrainingSession(message);

        verify(objectMapper, times(0)).readValue(anyString(), eq(TrainingSessionDTO.class));
        verify(trainerSummaryService, times(0)).manageTrainingSession(any(TrainingSessionDTO.class), anyString());
    }

    @Test
    public void testProcessTrainingSession_Exception() throws Exception {
        String jsonSessionDTO = "{\"trainerUsername\":\"test\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"isActive\":true,\"trainingDate\":\"2022-06-01T12:00:00.000Z\",\"trainingDuration\":60,\"actionType\":\"ADD\"}";

        TextMessage textMessage = mock(TextMessage.class);
        when(textMessage.getText()).thenReturn(jsonSessionDTO);

        Message message = textMessage;

        when(jwtService.validateToken(anyString())).thenThrow(new RuntimeException("Error"));

        consumer.processTrainingSession(message);

        verify(objectMapper, times(1)).readValue(jsonSessionDTO, TrainingSessionDTO.class);
        verify(trainerSummaryService, times(0)).manageTrainingSession(any(TrainingSessionDTO.class), anyString());
    }
}

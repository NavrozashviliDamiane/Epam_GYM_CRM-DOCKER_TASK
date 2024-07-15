package com.epam.trainerworkload.unit.transaction;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.trainerworkload.util.transaction.TransactionIdGenerator;
import org.junit.jupiter.api.Test;

public class TransactionIdGeneratorTest {

    @Test
    public void testGenerateTransactionId() {
        String transactionId1 = TransactionIdGenerator.generateTransactionId();
        String transactionId2 = TransactionIdGenerator.generateTransactionId();

        assertNotNull(transactionId1);
        assertNotNull(transactionId2);
        assertNotEquals(transactionId1, transactionId2);
    }

}

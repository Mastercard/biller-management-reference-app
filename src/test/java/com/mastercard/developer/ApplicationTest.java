package com.mastercard.developer;

import com.mastercard.developer.example.BillerManagementApiExample;
import com.mastercard.developer.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationTest {

    @InjectMocks
    private Application application;

    @Mock
    private BillerManagementApiExample billerManagementApiExample;

    @Test
    void testRun() throws Exception {
        doNothing().when(billerManagementApiExample).run(any());

        application.run();

        verify(billerManagementApiExample, atMostOnce()).run(any());
    }

    @Test
    void testRunException() throws Exception {
        doThrow(new ServiceException("Some error occurred")).when(billerManagementApiExample).run(any());

        application.run();

        verify(billerManagementApiExample, atMostOnce()).run(any());
    }
}

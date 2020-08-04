package com.mastercard.developer.service.impl;

import com.mastercard.developer.util.SampleResponseLoader;
import okhttp3.Call;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiResponse;
import org.openapitools.client.model.BillerManagementResponse;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BillerManagementServiceImplTest {

    @InjectMocks
    private BillerManagementServiceImpl service;

    @Mock
    private ApiClient apiClient;

    @BeforeEach
    void setUp() throws Exception {
        when(apiClient.buildCall(anyString(), anyString(), anyList(), anyList(), any(), anyMap(), anyMap(), anyMap(), any(), any())).thenReturn(mock(Call.class));
    }

    @Test
    void test_manageBiller_add() throws Exception {
        when(apiClient.execute(any(Call.class), any(Type.class))).thenReturn(new ApiResponse<>(200, new HashMap<>(), getMockBillerManagementResponse("add")));
        List<BillerManagementResponse> response = service.manageBillers("add");
        verify(apiClient, atMostOnce()).buildCall(anyString(), anyString(), anyList(), anyList(), any(), anyMap(), anyMap(), anyMap(), any(), any());
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(1, response.size())
        );
    }

    @Test
    void test_manageBiller_update() throws Exception {
        when(apiClient.execute(any(Call.class), any(Type.class))).thenReturn(new ApiResponse<>(200, new HashMap<>(), getMockBillerManagementResponse("update")));
        List<BillerManagementResponse> response = service.manageBillers("update");
        verify(apiClient, atMostOnce()).buildCall(anyString(), anyString(), anyList(), anyList(), any(), anyMap(), anyMap(), anyMap(), any(), any());
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(1, response.size())
        );
    }

    @Test
    void test_manageBiller_deactivate() throws Exception {
        when(apiClient.execute(any(Call.class), any(Type.class))).thenReturn(new ApiResponse<>(200, new HashMap<>(), getMockBillerManagementResponse("deactivate")));
        List<BillerManagementResponse> response = service.manageBillers("deactivate");
        verify(apiClient, atMostOnce()).buildCall(anyString(), anyString(), anyList(), anyList(), any(), anyMap(), anyMap(), anyMap(), any(), any());
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(1, response.size())
        );
    }

    @Test
    void test_manageBiller_all() throws Exception {
        when(apiClient.execute(any(Call.class), any(Type.class))).thenReturn(new ApiResponse<>(200, new HashMap<>(), getMockBillerManagementResponse("all")));
        List<BillerManagementResponse> response = service.manageBillers("all");
        verify(apiClient, atMostOnce()).buildCall(anyString(), anyString(), anyList(), anyList(), any(), anyMap(), anyMap(), anyMap(), any(), any());
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(3, response.size())
        );
    }

    private static List<BillerManagementResponse> getMockBillerManagementResponse(String scenario) throws Exception{
        return SampleResponseLoader.getResponseFromJson(scenario);
    }
}

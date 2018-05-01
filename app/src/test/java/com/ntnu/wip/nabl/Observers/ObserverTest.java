package com.ntnu.wip.nabl.Observers;

import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;
import com.ntnu.wip.nabl.Observers.Observers.ObserverFactory;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObserverTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private FireStoreClient network;

    @Test
    public void observerCallBack() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Client expectedClient = new Client();
        final Client[] actualClient = new Client[1];

        Method setClients = network.getClass().getDeclaredMethod("setLastFetchedClient", Client.class);
        setClients.setAccessible(true);

        Observer observer = ObserverFactory.create(ObserverFactory.CLIENT);
        observer.setSubject(network);
        observer.setOnUpdateListener(client -> {
            actualClient[0] = (Client) client;
        });

        setClients.invoke(network, expectedClient);

        Assert.assertEquals(expectedClient, actualClient[0]);
    }
}
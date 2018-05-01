package com.ntnu.wip.nabl.Observers;

import com.ntnu.wip.nabl.Network.AbstractClient;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;

public class ObserverTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private FireStoreClient client;

    @Test
    public void observerCallBack(){
        client.getAllProjects();
    }
}
package com.ntnu.wip.nabl.Network.FirestoreImpl.Callback;

import com.google.firebase.firestore.QuerySnapshot;

/**
 * {@link QuerySnapshot} callback
 */
public interface QuerySnapshotCallback {
    void trigger(QuerySnapshot snapshot);
}

package com.ntnu.wip.nabl.Network.FirestoreImpl.Callback;

import com.google.firebase.firestore.DocumentSnapshot;

/**
 * {@link DocumentSnapshotCallback} callback
 */
public interface DocumentSnapshotCallback {
    void trigger(DocumentSnapshot snapshot);
}

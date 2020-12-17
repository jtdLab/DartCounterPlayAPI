package dartServer.api.services;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InvitationService {

    public static void addInvitation(String uid, String inviter, int gameCode) {
        Firestore db = FirestoreClient.getFirestore();

        DocumentReference docRef = db.collection("users").document(uid);

        Map<String, Object> invitation = new HashMap<>();
        invitation.put("inviter", inviter);
        invitation.put("gameCode", gameCode);

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.update("invitations", FieldValue.arrayUnion(invitation));
        ApiFutures.addCallback(result, new ApiFutureCallback<WriteResult>() {
            @Override
            public void onFailure(Throwable throwable) {
                Map<String, Object> invitations = new HashMap<>();
                invitations.put("invitations", Arrays.asList(invitation));
                docRef.set(invitations);
            }

            @Override
            public void onSuccess(WriteResult writeResult) {

            }
        });

    }

    public static void removeInvitation(String uid, String inviter, int gameCode) {
        Firestore db = FirestoreClient.getFirestore();

        DocumentReference docRef = db.collection("users").document(uid);
        Map<String, Object> invitation = new HashMap<>();
        invitation.put("inviter", inviter);
        invitation.put("gameCode", gameCode);
        docRef.update("invitations", FieldValue.arrayRemove(invitation));
    }

    public static void removeAllInvitations(String uid) {
        Firestore db = FirestoreClient.getFirestore();

        DocumentReference docRef = db.collection("users").document(uid);
        docRef.update("invitations", FieldValue.delete());
    }

}

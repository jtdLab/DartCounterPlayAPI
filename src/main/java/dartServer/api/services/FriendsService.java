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

public class FriendsService {

    public static void addFriendship(String user1Uid, String user1Username, String user2Uid, String user2Username) {
        Firestore db = FirestoreClient.getFirestore();

        DocumentReference docRefUser1 = db.collection("users").document(user1Uid);
        DocumentReference docRefUser2 = db.collection("users").document(user2Uid);

        Map<String, Object> friendship1 = new HashMap<>();
        friendship1.put("uid", user2Uid);
        friendship1.put("username", user2Username);

        Map<String, Object> friendship2 = new HashMap<>();
        friendship1.put("uid", user1Uid);
        friendship1.put("username", user1Username);

        //asynchronously write data user1
        ApiFuture<WriteResult> result1 = docRefUser1.update("friends", FieldValue.arrayUnion(friendship1));
        ApiFutures.addCallback(result1, new ApiFutureCallback<WriteResult>() {
            @Override
            public void onFailure(Throwable throwable) {
                Map<String, Object> friends = new HashMap<>();
                friends.put("friends", Arrays.asList(friendship1));
                docRefUser1.set(friends);
            }

            @Override
            public void onSuccess(WriteResult writeResult) {

            }
        });

        //asynchronously write data
        ApiFuture<WriteResult> result2 = docRefUser2.update("friends", FieldValue.arrayUnion(friendship2));
        ApiFutures.addCallback(result2, new ApiFutureCallback<WriteResult>() {
            @Override
            public void onFailure(Throwable throwable) {
                Map<String, Object> friends = new HashMap<>();
                friends.put("friends", Arrays.asList(friendship2));
                docRefUser2.set(friends);
            }

            @Override
            public void onSuccess(WriteResult writeResult) {

            }
        });

    }

    public static void removeFriendship(String user1Uid, String user1Username, String user2Uid, String user2Username) {
        Firestore db = FirestoreClient.getFirestore();

        DocumentReference docRefUser1 = db.collection("users").document(user1Uid);
        DocumentReference docRefUser2 = db.collection("users").document(user2Uid);

        Map<String, Object> friendship1 = new HashMap<>();
        friendship1.put("uid", user2Uid);
        friendship1.put("username", user2Username);

        Map<String, Object> friendship2 = new HashMap<>();
        friendship1.put("uid", user1Uid);
        friendship1.put("username", user1Username);

        docRefUser1.update("friends", FieldValue.arrayRemove(friendship1));
        docRefUser2.update("friends", FieldValue.arrayRemove(friendship2));
    }

    public static void removeAllFriendships(String uid) {
        Firestore db = FirestoreClient.getFirestore();

        DocumentReference docRef = db.collection("users").document(uid);
        docRef.update("friends", FieldValue.delete());
    }

}

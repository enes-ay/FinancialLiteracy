package com.enesay.financialliteracy.data.datasource

import android.util.Log
import com.enesay.financialliteracy.model.Education.EducationalContent
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EducationalContentDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    companion object {
        private const val TAG = "EducationalContentDS"
        private const val COLLECTION_EDUCATIONAL_CONTENTS = "educational_contents"
        private const val COLLECTION_USERS = "Users"
    }

    fun getEducationalContents(): Flow<List<EducationalContent>> = callbackFlow {
        val collectionRef = firestore.collection(COLLECTION_EDUCATIONAL_CONTENTS)
        val listenerRegistration = collectionRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val contents = snapshot?.documents?.mapNotNull { document ->
                document.toObject(EducationalContent::class.java)?.copy(id = document.id)
            } ?: emptyList()

            // trySend ile gönderim yapıyoruz; başarısız olursa zaten kapatıyoruz.
            trySend(contents).isSuccess
        }

        // Flow kapatıldığında listener temizlenecek.
        awaitClose { listenerRegistration.remove() }
    }.flowOn(Dispatchers.IO)

    fun getContentDetail(categoryId: String): Flow<EducationalContent?> = callbackFlow {
        val documentRef = firestore
            .collection(COLLECTION_EDUCATIONAL_CONTENTS)
            .document(categoryId)
        val listenerRegistration = documentRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val contentDetail = snapshot?.toObject(EducationalContent::class.java)
            trySend(contentDetail).isSuccess
        }

        awaitClose { listenerRegistration.remove() }
    }.flowOn(Dispatchers.IO)

    fun getLatestBalance(userId: String): Flow<Double?> = callbackFlow {
        val documentRef = firestore.collection(COLLECTION_USERS).document(userId)
        val listenerRegistration = documentRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val balance = snapshot.getDouble("Balance") ?: 0.0
                Log.d(TAG, "Latest balance: $balance")
                trySend(balance).isSuccess
            } else {
                // Eğer belge yoksa veya snapshot boşsa null gönderebiliriz.
                trySend(null).isSuccess
            }
        }

        awaitClose { listenerRegistration.remove() }
    }.flowOn(Dispatchers.IO)
}

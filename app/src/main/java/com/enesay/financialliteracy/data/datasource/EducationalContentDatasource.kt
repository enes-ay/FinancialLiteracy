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

    fun getEducationalContents(): Flow<List<EducationalContent>> = callbackFlow {
        val collection = firestore.collection("educational_contents")
        val subscription = collection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val contents = snapshot?.documents?.mapNotNull { document ->
                document.toObject(EducationalContent::class.java)?.copy(id = document.id)
            } ?: emptyList()

            trySend(contents).isSuccess
        }

        awaitClose { subscription.remove() }
    }.flowOn(Dispatchers.IO)

    fun getContentDetail(categoryId: String): Flow<EducationalContent?> = callbackFlow {
        val document = firestore.collection("educational_contents").document(categoryId)
        val subscription = document.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val categoryDetail = snapshot?.toObject(EducationalContent::class.java)
            trySend(categoryDetail).isSuccess
        }

        awaitClose { subscription.remove() }
    }.flowOn(Dispatchers.IO)

    fun getLatestBalance(userId: String): Flow<Double?> = callbackFlow {
        val document = firestore.collection("Users").document(userId)
        val subscription = document.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val balance = snapshot.getDouble("Balance") ?: 0.0
                Log.d("home", "datasource $balance")

                trySend(balance)
            }
        }

        awaitClose { subscription.remove() }
    }.flowOn(Dispatchers.IO)
}

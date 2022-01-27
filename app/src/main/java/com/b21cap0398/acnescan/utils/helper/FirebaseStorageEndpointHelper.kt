package com.b21cap0398.acnescan.utils.helper

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

object FirebaseStorageEndpointHelper {
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference


    // Path to "result_id.jpg"
    fun getAcnePhotoPath(email: String, result_id: String): StorageReference {
        return storageRef.child("users/${email}/results/${result_id}/${result_id}.jpg")
    }

    fun getStorageRef(): StorageReference {
        return storageRef
    }

    fun getDownloadUrlOfReference(childRef: String): Task<Uri> {
        return getStorageRef().child(childRef).downloadUrl
    }
}
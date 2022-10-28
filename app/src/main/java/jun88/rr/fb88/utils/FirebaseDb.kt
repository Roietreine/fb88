package jun88.rr.fb88.utils

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseDb {

    lateinit var databaseReference: DatabaseReference
    fun getDatabase(): DatabaseReference {
        val database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("jumpcode")
        return databaseReference
    }
}
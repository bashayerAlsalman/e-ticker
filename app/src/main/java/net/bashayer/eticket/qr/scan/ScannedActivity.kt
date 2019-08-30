package net.bashayer.eticket.qr.scan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_scanned.*
import net.bashayer.eticket.R
import net.bashayer.eticket.common.BaseActivity
import net.bashayer.eticket.qr.helper.EncryptionHelper
import net.bashayer.eticket.qr.models.EventAttendee

class ScannedActivity : BaseActivity() {

    companion object {
        private const val SCANNED_STRING: String = "scanned_string"
        fun getScannedActivity(callingClassContext: Context, encryptedString: String): Intent {
            return Intent(callingClassContext, ScannedActivity::class.java)
                    .putExtra(SCANNED_STRING, encryptedString)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanned)
        if (intent.getSerializableExtra(SCANNED_STRING) == null) throw RuntimeException("No encrypted String found in intent")
        val decryptedString = EncryptionHelper.getInstance().getDecryptionString(intent.getStringExtra(SCANNED_STRING))
        val userObject = Gson().fromJson(decryptedString, EventAttendee::class.java)
    //   scannedFullNameTextView.text = userObject.eventId
       // scannedAgeTextView.text = userObject.eventAttendeeName
    }
}

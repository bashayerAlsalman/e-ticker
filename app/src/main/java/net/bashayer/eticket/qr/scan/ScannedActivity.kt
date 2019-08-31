package net.bashayer.eticket.qr.scan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_scanned.*
import net.bashayer.eticket.R
import net.bashayer.eticket.common.BaseActivity
import net.bashayer.eticket.network.TicketService
import net.bashayer.eticket.network.UnsafeOkHttpClient
import net.bashayer.eticket.qr.helper.EncryptionHelper
import net.bashayer.eticket.qr.models.EventAttendee
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ScannedActivity : BaseActivity() {

    companion object {
        private const val SCANNED_STRING: String = "scanned_string"
        fun getScannedActivity(callingClassContext: Context, encryptedString: String): Intent {
            return Intent(callingClassContext, ScannedActivity::class.java)
                    .putExtra(SCANNED_STRING, encryptedString)
        }
    }

    var ticketId = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanned)
        if (intent.getSerializableExtra(SCANNED_STRING) == null) throw RuntimeException("No encrypted String found in intent")
        val decryptedString = EncryptionHelper.getInstance().getDecryptionString(intent.getStringExtra(SCANNED_STRING))
        val attendee = Gson().fromJson(decryptedString, EventAttendee::class.java)

        ticketId = attendee.eventId
        //todo ticket ID

//        scannedFullNameTextView.text = userObject.eventId
//        scannedAgeTextView.text = userObject.eventAttendeeName
    }


    private fun startVerify() {
        val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://e-ticketing-sandbox.mxapps.io/rest/verfication/v1/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val service = retrofit.create(TicketService::class.java)

        service.verifyTicket("application/json", ticketId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Boolean> {

                    override fun onSubscribe(d: Disposable) {}

                    override fun onNext(value: Boolean) {
                        checkVerification(value)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onComplete() {}
                })

    }

    private fun checkVerification(isVerified: Boolean) {
        if (isVerified) {
            icon.background = getDrawable(R.drawable.ic_check_circle_black_24dp)
            eventAttendeeNameValue.text = getString(R.string.attendeeVerified)
        } else {
            icon.background = getDrawable(R.drawable.ic_cancel_black_24dp)
            eventAttendeeNameValue.text = getString(R.string.attendeeNotVerified)
        }

    }
}

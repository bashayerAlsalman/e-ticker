package net.bashayer.eticket.qr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import kotlinx.android.synthetic.main.activity_generate_qr_code.*
import net.bashayer.eticket.R
import net.bashayer.eticket.common.BaseActivity
import net.bashayer.eticket.qr.eticket.ETicketAdapter
import net.bashayer.eticket.qr.helper.EncryptionHelper
import net.bashayer.eticket.qr.helper.QRCodeHelper
import net.bashayer.eticket.qr.models.AttendeeTicket
import net.bashayer.eticket.qr.models.EventAttendee
import net.bashayer.eticket.qr.models.EventAttendees
import java.util.*

class GenerateQrCodeActivity : BaseActivity(), TicketCallback {

    lateinit var adapter: ETicketAdapter

    companion object {
        fun getGenerateQrCodeActivity(callingClassContext: Context) = Intent(callingClassContext, GenerateQrCodeActivity::class.java)
        val EVENT_ATTENDEES_KEY = "event-attendees"
    }

    var QRCodes = ArrayList<AttendeeTicket>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_qr_code)

        //  val attendees = intent.getSerializableExtra(EVENT_ATTENDEES_KEY) as EventAttendees todo use this
        val attendees = EventAttendees(listOf(EventAttendee("1234", "Bashayer Alsalman", "programming challenege in Jeddah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam", "Jeddah", Date()),
                EventAttendee("1234", "Nora Alyahya", "Elm hackathon in suhail", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam", "Riyadh", Date()),
                EventAttendee("1234", "Nora Alyahya", "Elm hackathon in suhail", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam", "Riyadh", Date())))

        //todo show loader and message getting the tickets

        attendees.attendees.forEach {
            val EventAttendee = EventAttendee(it.eventName, it.eventAttendeeName, it.eventDate)
            val serializeString = Gson().toJson(EventAttendee)
            val encryptedString = EncryptionHelper.getInstance().encryptionString(serializeString).encryptMsg()
            val bitmap = QRCodeHelper.newInstance(this).setContent(encryptedString).setErrorCorrectionLevel(ErrorCorrectionLevel.Q).setMargin(2).qrcOde

            QRCodes.add(AttendeeTicket(bitmap, it.eventId, it.eventAttendeeName, it.eventName, it.eventDescription, it.city, it.eventDate))
        }

        //todo hide the loader

        initAdapter()
    }


    private fun initAdapter() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@GenerateQrCodeActivity)
            adapter = ETicketAdapter(this@GenerateQrCodeActivity, this@GenerateQrCodeActivity, QRCodes)
        }
    }

    override fun onTicketClicked(attendeeTicket: AttendeeTicket) {
        var myIntent = Intent(Intent.ACTION_SEND) //todo add the content to be shared
        myIntent.setType("text/plain");
        var shareBody = "Your body is here"
        var shareSub = "Your subject"
        myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
        myIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(myIntent, getString(R.string.share_using)))

    }
}


interface TicketCallback {
    fun onTicketClicked(attendeeTicket: AttendeeTicket)
}
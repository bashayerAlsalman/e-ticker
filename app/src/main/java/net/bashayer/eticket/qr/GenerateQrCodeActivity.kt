package net.bashayer.eticket.qr

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.core.app.ActivityCompat
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
        private const val MY_WRITE_REQUEST_CODE = 6516
    }

    var QRCodes = ArrayList<AttendeeTicket>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_qr_code)

        supportActionBar?.hide()

        //  val attendees = intent.getSerializableExtra(EVENT_ATTENDEES_KEY) as EventAttendees todo use this
        val attendees = EventAttendees(listOf(EventAttendee("1234", "Bashayer Alsalman", "programming challenege in Jeddah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam", "Jeddah", Date()),
                EventAttendee("1234", "Nora Alyahya", "Elm hackathon in suhail", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam", "Riyadh", Date()),
                EventAttendee("1234", "Nora Alyahya", "Elm hackathon in suhail", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam", "Riyadh", Date())))

        //todo show loader and message getting the ticketModels

        attendees.attendees.forEach {
            val EventAttendee = EventAttendee(it.eventName, it.eventAttendeeName)
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

    override fun onTicketClicked(attendeeTicket: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_WRITE_REQUEST_CODE)
                return
            }
        }

        val mBitmap = createBitmapFromLayout(attendeeTicket )//todo event info
        val path = MediaStore.Images.Media.insertImage(contentResolver, mBitmap, getString(R.string.share_ticket), null)
        val uri = Uri.parse(path)

        val share = Intent(Intent.ACTION_SEND)
        share.type = "image/**"
        share.putExtra(Intent.EXTRA_STREAM, uri)
        share.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_ticket))

        startActivity(Intent.createChooser(share, getString(net.bashayer.eticket.R.string.share_using))) //todo add event information
    }


    private fun createBitmapFromLayout(tv: View): Bitmap { //todo
        val spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        tv.measure(spec, spec)
        tv.layout(0, 0, tv.measuredWidth, tv.measuredHeight)
        val b = Bitmap.createBitmap(tv.measuredWidth, tv.measuredWidth,
                Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        c.translate((-tv.scrollX).toFloat(), (-tv.scrollY).toFloat())
        tv.draw(c)
        return b
    }

}


interface TicketCallback {
    fun onTicketClicked(attendeeTicket: View)
}
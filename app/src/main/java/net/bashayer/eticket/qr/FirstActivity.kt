package net.bashayer.eticket.qr

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_first.*
import net.bashayer.eticket.R
import net.bashayer.eticket.common.BaseActivity
import net.bashayer.eticket.qr.scan.ScanQrCodeActivity

class FirstActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        firstActivityGenerateButton.setOnClickListener {
            startActivity(GenerateQrCodeActivity.getGenerateQrCodeActivity(this))
        }
        firstActivityScanButton.setOnClickListener {
            startActivity(ScanQrCodeActivity.getScanQrCodeActivity(this))
        }
    }
}

package id.co.eltisia.asseteltisia

import android.Manifest
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.zxing.BarcodeFormat
import com.kotlinpermissions.KotlinPermissions

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

//    val TAG = "Bismillah"
    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //=============================================================barcode Setting
        codeScanner = CodeScanner(this, scanner_view)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = listOf(BarcodeFormat.CODE_39, BarcodeFormat.CODE_93)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isTouchFocusEnabled = true

        //=============================================================barcode Success Callback
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
//                Toast.makeText(this,"Scan result ${it.text}", Toast.LENGTH_LONG).show()
                toast("Scan result ${it.text}")
                val buff = it.text
                val index = buff.indexOf('/')
                val idBarcode: String? = if (index == -1) null else buff.substring(index + 1)
                txt_barcode.setText(idBarcode)
                scanMode(false)
            }
        }

        //=============================================================barcode Error Calback
        codeScanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(this, "error ${it.message}", Toast.LENGTH_LONG).show()
            }
        }

        //=============================================================recycle view
        recy_view.layoutManager = LinearLayoutManager(this)
        recy_view.adapter = MainAdapter()

        //============================================================view.setOnClickListener
        btn_scan.setOnClickListener {
            Toast.makeText(this,"touch",Toast.LENGTH_SHORT).show()
            KotlinPermissions.with(this)
                .permissions(Manifest.permission.CAMERA)
                .onAccepted {
//                    Toast.makeText(this,"accepted",Toast.LENGTH_SHORT).show()
                    toast("accepted")
                    scanMode(true)
                    codeScanner.startPreview()
                }
                .onDenied {
//                    Toast.makeText(this,"denied",Toast.LENGTH_SHORT).show()
                    toast("denied")
                }
                .ask()
        }
        fab.setOnClickListener { view ->
            alert("Data sudah benar?", "Konfirmasi Perubahan Data") {
                yesButton {
                    toast("Data Diubah")
                }
                noButton {

                }
            }.show()

        }

        fetchJson()
    }

    fun fetchJson(){
        doAsync {

        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun scanMode(b :Boolean){
        if(b){
            layout_txt.visibility = View.INVISIBLE
            btn_scan.visibility = View.INVISIBLE
            btn_search.visibility = View.INVISIBLE
            recy_view.visibility = View.INVISIBLE
            fab.hide()
            frame_layout.visibility = View.VISIBLE
        }else{
            layout_txt.visibility = View.VISIBLE
            btn_scan.visibility = View.VISIBLE
            btn_search.visibility = View.VISIBLE
            recy_view.visibility = View.VISIBLE
            fab.show()
            frame_layout.visibility = View.INVISIBLE
        }
    }
}

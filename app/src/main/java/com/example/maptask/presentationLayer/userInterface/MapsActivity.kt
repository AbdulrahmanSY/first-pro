package com.example.maptask.presentationLayer.userInterface

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.lifecycleScope
import com.example.maptask.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.maptask.databinding.ActivityMapsBinding
import com.example.maptask.presentationLayer.viewModel.UserViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var editTextOne: TextInputEditText
    private lateinit var editTextTow: TextInputEditText
    private lateinit var editTextThree: TextInputEditText

    private lateinit var ballOne: ImageView
    private lateinit var ballTow: ImageView
    private lateinit var ballThree: ImageView
    lateinit var viewModel: UserViewModel

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//      navigation bar
        navigation_view()

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        val logout = findViewById<Button>(R.id.btn_sing_out)
        val token = intent.getStringExtra("access_token").toString()
        logout.setOnClickListener(){
            viewModel.logout(token)


        }

            BottomSheetBehavior.from(binding.menuFramlayout).apply { peekHeight=200 }

            editTextOne = findViewById(R.id.edit_text_menu_one)
            editTextTow = findViewById(R.id.edit_text_menu_tow)
            editTextThree = findViewById(R.id.edit_text_menu_three)

            ballOne = findViewById(R.id.ball_one)
            ballTow= findViewById(R.id.ball_tow)
            ballThree = findViewById(R.id.ball_three)

            editBallText(editTextOne,ballOne)
            editBallText(editTextTow,ballTow)
            editBallText(editTextThree,ballThree)




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        val istambol= LatLng(33.572266, 36.401810)
        mMap.addMarker(MarkerOptions().position(istambol).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(istambol,16f))
    }

    fun navigation_view(){
        binding.apply {

            toggle = ActionBarDrawerToggle(this@MapsActivity,binding.myDrawerLayout,
                R.string.open,
                R.string.close
            )
            binding.myDrawerLayout.addDrawerListener(toggle)
//            toggle.syncState()

            val nav = findViewById<NavigationView>(R.id.navigation_view)
            nav?.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.free_rides ->
                    {Toast.makeText(this@MapsActivity,"free_rides",Toast.LENGTH_LONG).show()}
                    R.id.pre_order ->
                    {Toast.makeText(this@MapsActivity,"pre_order",Toast.LENGTH_LONG).show()}
                    R.id.rides ->
                    {Toast.makeText(this@MapsActivity,"rides",Toast.LENGTH_LONG).show()}
                    R.id.couier ->
                    {Toast.makeText(this@MapsActivity,"couier",Toast.LENGTH_LONG).show()}
                    R.id.paymentmethods ->
                    {Toast.makeText(this@MapsActivity,"paymentmethods",Toast.LENGTH_LONG).show()}
                    R.id.help_and_contact ->
                    {Toast.makeText(this@MapsActivity,"help_and_contact",Toast.LENGTH_LONG).show()}
                    R.id.settings ->
                    {Toast.makeText(this@MapsActivity,"settings",Toast.LENGTH_LONG).show()}
                }
                true

            }
            val sout = findViewById<Button>(R.id.btn_sing_out)
            sout.setOnClickListener { Toast.makeText(this@MapsActivity,"sing out",Toast.LENGTH_LONG).show() }
            val back_nav = findViewById<MaterialButton>(R.id.btn_back_arr)

            back_nav?.setOnClickListener(){
                Toast.makeText(this@MapsActivity,"settings",Toast.LENGTH_LONG).show()
            }
            val header = nav.getHeaderView(0)
            header.setOnClickListener(){
                startActivity(Intent(this@MapsActivity, Profile::class.java))
            }

            header.findViewById<Button>(R.id.btn_back_arr).setOnClickListener(){
                binding.myDrawerLayout.closeDrawer(nav)
            }

            binding.btnNavigationBar.setOnClickListener(){binding.myDrawerLayout.openDrawer(nav)}

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){true}

        return super.onOptionsItemSelected(item)
    }

     fun editBallText(textField:TextInputEditText,ballOne:ImageView){

        textField.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                ballOne.setImageResource(R.drawable.point)
            } else {
                ballOne.setImageResource(R.drawable.point1)
            }
        }

    }
}



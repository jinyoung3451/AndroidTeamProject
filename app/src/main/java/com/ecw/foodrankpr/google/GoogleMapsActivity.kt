package com.ecw.foodrankpr.google

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.ecw.foodrankpr.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.maps.model.CameraPosition

//구글지도 api 페이지
class GoogleMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    // 현재 위치를 검색하기 위함
    private lateinit var fusedLocationClient: FusedLocationProviderClient // 위칫값 사용
    private lateinit var locationCallback: LocationCallback // 위칫값 요청에 대한 갱신 정보를 받아옴

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps)

        // 사용할 권한 array로 저장
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION)
        requirePermissions(permissions, 999) //권환 요쳥, 999는 임의의 숫자
    }

    fun startProcess() {
        // SupportMapFragment를 가져와서 지도가 준비되면 알림을 받습니다.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /** 권한 요청*/
    fun requirePermissions(permissions: Array<String>, requestCode: Int) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionGranted(requestCode)
        } else {
            val isAllPermissionsGranted = permissions.all { checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED }
            if (isAllPermissionsGranted) {
                permissionGranted(requestCode)
            } else {
                ActivityCompat.requestPermissions(this, permissions, requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            permissionGranted(requestCode)
        } else {
            permissionDenied(requestCode)
        }
    }

    // 권한이 있는 경우 실행
    fun permissionGranted(requestCode: Int) {
        startProcess() // 권한이 있는 경우 구글 지도를준비하는 코드 실행
    }

    // 권한이 없는 경우 실행
    fun permissionDenied(requestCode: Int) {
        Toast.makeText(this
            , "권한 승인이 필요합니다."
            , Toast.LENGTH_LONG)
            .show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap



        // 수동으로 마커추가
        // 현재위치로 이동버튼추가, 줌기능 추가
        val yakiya = LatLng(35.156553, 129.060070)
        mMap.addMarker(MarkerOptions().position(yakiya).title("야키야"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(yakiya))

        val mamadoma = LatLng(35.154115, 129.061465)
        mMap.addMarker(MarkerOptions().position(mamadoma).title("마마도마"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mamadoma))

        val jejuokktop = LatLng(35.156069, 129.059952)
        mMap.addMarker(MarkerOptions().position(jejuokktop).title("제주옥탑"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(jejuokktop))

        val care = LatLng(35.155920, 129.057141)
        mMap.addMarker(MarkerOptions().position(care).title("겐짱카레"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(care))

        val sulim = LatLng(35.155821, 129.055382)
        mMap.addMarker(MarkerOptions().position(sulim).title("수림식당"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sulim))

        val hongkong = LatLng(35.158887, 129.064450)
        mMap.addMarker(MarkerOptions().position(hongkong).title("굿모닝홍콩"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hongkong))

        val sangho = LatLng(35.160182, 129.064287)
        mMap.addMarker(MarkerOptions().position(sangho).title("상호미지수"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sangho))

        val canda = LatLng(35.158348, 129.062198)
        mMap.addMarker(MarkerOptions().position(canda).title("칸다소바"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(canda))

        val likermeal = LatLng(35.152194, 129.067549)
        mMap.addMarker(MarkerOptions().position(likermeal).title("라이커밀"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(likermeal))

        val lala = LatLng(35.153059, 129.062242)
        mMap.addMarker(MarkerOptions().position(lala).title("라라관"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lala))
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        googleMap.isMyLocationEnabled = true
        googleMap.uiSettings.isZoomControlsEnabled = true
    }


    // 위치 정보를 받아오는 역할
    @SuppressLint("MissingPermission") //requestLocationUpdates는 권한 처리가 필요한데 현재 코드에서는 확인 할 수 없음. 따라서 해당 코드를 체크하지 않아도 됨.
    fun updateLocation() {
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 1000
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.let {
                    for(location in it.locations) {
                        Log.d("Location", "${location.latitude} , ${location.longitude}")
                        setLastLocation(location)
                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    fun setLastLocation(lastLocation: Location) {
        val myLocation = LatLng(lastLocation.latitude, lastLocation.longitude)
        val marker = MarkerOptions()
            .position(myLocation)
            .title("Here!")

        val cameraOption = CameraPosition.Builder()
            .target(myLocation)
            .zoom(15.0f)
            .build()

        mMap.clear()
        mMap.addMarker(marker)
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraOption))
    }
}
package com.example.weather
import android.os.Bundle
import android.view.WindowManager
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.weather.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
//    8b7dcadb1a8b4e4c680909a239e4a0f4
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        Thread.sleep(400)
        installSplashScreen()
        enableEdgeToEdge()
        hide()
        setContentView(binding.root)
fetch("London")
        searchcity()
    }

    private fun searchcity() {
        val searchView=binding.Search
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0!=null)
                fetch(p0)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
    }


    private fun fetch(cityname:String)
   {
       val retrofit=Retrofit.Builder()
           .addConverterFactory(GsonConverterFactory.create())
           .baseUrl("https://api.openweathermap.org/data/2.5/")
           .build().create(apiinterface::class.java)
       val response=retrofit.getweatherdata(cityname,"8b7dcadb1a8b4e4c680909a239e4a0f4","metric")
       response.enqueue(object :Callback<WeatherApp>{
           override fun onResponse(p0: Call<WeatherApp>, p1: Response<WeatherApp>) {
               val responseBody=p1.body()
               if (p1.isSuccessful&&responseBody!=null){
                   val temperature=responseBody.main.temp
                   val humidity=responseBody.main.humidity
                   val max=responseBody.main.temp_max
                   val min=responseBody.main.temp_min
                   val sunrise=responseBody.sys.sunrise
                   val sunset=responseBody.sys.sunset
                   val condition=responseBody.weather.firstOrNull()?.main?:"unknown"
                   val windspeeed=responseBody.wind.speed
                   binding.Temperature.text="$temperature"
                   binding.Condition.text="$condition"
                   binding.Max.text="$max"
                   binding.Min.text="$min"
                   binding.Sunset.text="$sunset"
                   binding.Sunrise.text="$sunrise"
                   binding.Humidity.text="$humidity"
                   binding.Sunrise.text="$sunrise"
                   binding.Windspeed.text="$windspeeed"
                   binding.Day.text=dayname(System.currentTimeMillis())
                   binding.Date.text=date()
                   binding.City.text="$cityname"
                   change(condition)
               }

           }

           override fun onFailure(p0: Call<WeatherApp>, p1: Throwable) {
           toast().showtoast(this@MainActivity,"Sorry",)
           }

       })

   }

    private fun change(condition:String) {
        when(condition)
        {
            "Clear Sky","Sunny","Clear"->{
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.Animation.setAnimation(R.raw.sun)
            }
            "Partly Clouds","Clouds","Overcast","Mist","Foggy"->{
                binding.root.setBackgroundResource(R.drawable.cloudbg)
                binding.Animation.setAnimation(R.raw.cloud)
            }
            "Light Rain","Drizzle","Moderate Rain","Showers","Heavy Rain"->{
                binding.root.setBackgroundResource(R.drawable.rain_background)
                binding.Animation.setAnimation(R.raw.rain)
            }
            "Light Snow","Moderate Snow","Heavy Snow","Blizzard"->{
                binding.root.setBackgroundResource(R.drawable.snow_background)
                binding.Animation.setAnimation(R.raw.snow)
            }
            else->{
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.Animation.setAnimation(R.raw.sun)
            }
        }

    }

    fun dayname(timestamp: Long):String{
        val sdf=SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date())
    }
    private fun date():String
    {
        val sdf=SimpleDateFormat("dd mm yyyy", Locale.getDefault())
        return sdf.format(Date())
    }
    private fun hide()
    {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}
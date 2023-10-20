package mhha.sample.eminfo

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import mhha.sample.eminfo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //이동하기
        binding.moveinputActivityButton.setOnClickListener {
            var intent = Intent(this, inputActivity::class.java)
            intent.putExtra("intentMessage","응급의료정보")
            startActivity(intent)
        } //binding.moveinputActivityButton.setOnClickListener

        binding.phonLayer.setOnClickListener {
            with(Intent(Intent.ACTION_VIEW)){
                var phoneNumber = binding.phonenumberValueTextView.text.toString()
                    .replace("-","")
                data = Uri.parse("tel:$phoneNumber")
                startActivity(this)
            }//with(Intent(Intent.ACTION_VIEW))
        }//binding.phonLayer.setOnClickListener

    }//onCreate

    override fun onResume() {
        super.onResume()
        getDataUiUpdate()
    }//onResume

    private fun getDataUiUpdate(){
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)){
            var name = getString(NAME,"")
            binding.nameValueTextView.text = getString(NAME,"")
            binding.birthdateValueTextView.text = getString(BIRTHDATE, "0000-00-00")
            binding.bloodtypeValueTextView.text = getString(BLOOD_TYPE,"")
            binding.phonenumberValueTextView.text = getString(PHONE,"000-0000-0000")
            val caution = getString(CAUTON,"")

        Log.d("info", "Name : ${name}")
        // caution에 정보에 따라 뷰가 보여지거나 안보여지는 것을 3줄로 줄일 수 있음.
//            if(caution.isNullOrEmpty()){
//                binding.cautionTextView.isVisible = false
//                binding.cautionValueTextView.isVisible = false
//            }else{
//                binding.cautionTextView.isVisible = true
//                binding.cautionValueTextView.isVisible = true
//                binding.cautionValueTextView.text = caution
//            }//if(caution.isNullOrEmpty())

            binding.cautionTextView.isVisible = caution.isNullOrEmpty().not()
            binding.cautionValueTextView.isVisible= caution.isNullOrEmpty().not()
            if(caution.isNullOrEmpty().not()) binding.cautionValueTextView.text = caution

        }//with(getSharedPreferences("infodata", Context.MODE_PRIVATE))
    }//private fun getData()
}//MainActivity
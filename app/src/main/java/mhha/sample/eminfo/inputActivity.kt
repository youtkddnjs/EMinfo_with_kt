package mhha.sample.eminfo

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.view.isVisible
import mhha.sample.eminfo.databinding.ActivityInputBinding
import mhha.sample.eminfo.databinding.ActivityMainBinding

class inputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentMessage = intent.getStringExtra("intentMeaasge")?:"없음"
        Log.d("intentMessage",intentMessage)

        //혈액형
        binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.blood_types,
            android.R.layout.simple_list_item_1
        ) //binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource

        //생년월일
        binding.birthdateLayer.setOnClickListener {
            val listener = OnDateSetListener{date,year,month,dayOfMonth ->
                binding.dateTextView.text = "$year-${month.inc()}-$dayOfMonth"
            }
            DatePickerDialog(this, listener,1900,1,1,).show()
        } //binding.birthdateLayer.setOnClickListener

        //주의 사항 노출
        binding.cautionEditTextView.isVisible = binding.cautionCheckBox.isChecked
        binding.cautionCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.cautionEditTextView.isVisible = isChecked
        }

        binding.saveButton.setOnClickListener {
            saveData()
            finish()
        } //binding.saveButton.setOnClickListener

        binding.deleteButton.setOnClickListener {
            deleteData()
            finish()
        }

    }//onCreate

    private fun saveData(){
        //with을 사용하기 전 예제
//        val edit = getSharedPreferences("infodata", MODE_PRIVATE).edit()
//        edit.putString("name",binding.nameEditTextView.text.toString())
//        edit.putString("bloodType","")
//        edit.putString("phonNumber", binding.phonenumberEditTextView.text.toString())
//        edit.putString("birthdate",binding.bloodtypeEditTextView.text.toString())
//        edit.putString("caution","")
//        // edit.commit() 다른 쓰레드에서 진행해야 함.
//        edit.apply()

        with(getSharedPreferences(USER_INFORMATION, MODE_PRIVATE).edit()){
            putString(NAME,binding.nameEditTextView.text.toString())
            putString(BLOOD_TYPE,getBloodType())
            putString(PHONE, binding.phonenumberEditTextView.text.toString())
            putString(BIRTHDATE,binding.dateTextView.text.toString())
            putString(CAUTON,getCaution())
            apply()
        }
        Toast.makeText(this,"저장 완료",Toast.LENGTH_SHORT).show()
    } //private fun saveData()

    private fun getBloodType(): String{
        val bloodAlphabet = binding.bloodTypeSpinner.selectedItem.toString()
        var bloodSign = if(binding.bloodTypePlus.isChecked) "+" else "-"
        return "$bloodSign$bloodAlphabet"
    }//private fun getBloodType()

    private fun getCaution() : String{
        return if(binding.cautionCheckBox.isChecked) binding.cautionEditTextView.text.toString() else ""
    }//private fun getCaution()

    private fun deleteData(){
        with(getSharedPreferences(USER_INFORMATION, MODE_PRIVATE).edit()){
            clear()
            apply()
        }//getSharedPreferences

        Toast.makeText(this,"삭제 완료",Toast.LENGTH_SHORT).show()
    }//private fun deleteData()

}//input
package com.training.farmapp_0706012110026

import android.R
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isEmpty
import com.google.android.material.snackbar.Snackbar
import com.training.farmapp_0706012110026.DB.AnimalDatabase.Companion.animalArr
import com.training.farmapp_0706012110026.Model.Animal
import com.training.farmapp_0706012110026.databinding.ActivityAnimalFormBinding

class AnimalFormActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAnimalFormBinding
    private var animalPosition : Int = -1
    private var imageUri : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimalFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        listener()
        checkIfValuePassed()


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun checkIfValuePassed() {
        animalPosition = intent.getIntExtra("position", -1)
        val actionBar = supportActionBar
        if(animalPosition != -1){
            setValueToInput(animalArr.get(animalPosition))
            binding.submitFormBtn.setText("UPDATE")
            actionBar?.setTitle("Edit Hewan")
        }else{
            binding.submitFormBtn.setText("ADD")
            actionBar?.setTitle("Tambah Hewan")
        }
    }

    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        if (it.resultCode == Activity.RESULT_OK){
            val uri = it.data?.data
            binding.inputCurImage.setImageURI(uri)
            imageUri = uri.toString()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                baseContext.getContentResolver().takePersistableUriPermission(Uri.parse(it.data?.data.toString()),
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            }
        }

    }

    private fun setValueToInput(animal : Animal){
        binding.inputName.editText?.setText(animal.nama)
        binding.inputJenis.editText?.setText(animal.jenis)
        binding.inputUsia.editText?.setText(animal.age.toString().split(".")[0])
        binding.inputCurImage.setImageURI(Uri.parse(animal.imageUri))
        imageUri = animal.imageUri.toString()
    }
    private fun listener(){
        binding.submitFormBtn.setOnClickListener {
            val name = binding.inputName.editText?.text.toString()
            val jenis = binding.inputJenis.editText?.text.toString()
            var umur = binding.inputUsia.editText?.text.toString()
            if(umur == ""){
                umur = "-1"
            }

            inputChecker(Animal(name, jenis, umur.toDouble()))
        }

        binding.inputImageBtn.setOnClickListener{
            val galleryIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            galleryIntent.type = "image/*"
            GetResult.launch(galleryIntent)
        }

    }
    private fun inputChecker(animal : Animal){
        var isCompleted = true

        if(animal.nama!!.isEmpty()){
            binding.inputName.error = "Tolong isi kolom nama"
            isCompleted = false
        }else{
            binding.inputName.error = ""
        }

        if(animal.jenis!!.isEmpty()){
            binding.inputJenis.error = "Tolong isi kolom jenis"
            isCompleted = false
        }else{
            binding.inputJenis.error = ""
        }

        if(animal.age!! == 0.0){
            binding.inputUsia.error = "Tolong isi kolom umur"
            isCompleted = false
        }else if(animal.age!! < 0){
            binding.inputUsia.error = "Tolong input umur terlebih dahulu"
            isCompleted = false
        }else{
            binding.inputUsia.error = ""
        }



        if (isCompleted){
            animal.imageUri = imageUri

            if(animalPosition == -1){
                animalArr.add(animal)
                setResult(1)

            }else{
                animalArr.set(animalPosition, animal)
                setResult(2)
            }

            finish()
        }
    }
}
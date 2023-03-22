package smartngo.async.com.ui.form

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import smartngo.async.com.core.DataState
import smartngo.async.com.databinding.ActivityAddBinding
import smartngo.async.com.domain.model.User
import smartngo.async.com.ui.main.MainViewModel

@AndroidEntryPoint
class AddActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddBinding
    private lateinit var userViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        initUI()
        initObservers()
    }

    private fun initUI() {
         binding.btnAdd.setOnClickListener {
             var name = binding.edName.text.toString().trim()
             var location = binding.edLocation.text.toString().trim()

             checkFields(name, location)
         }
    }

    private fun checkFields(name: String, location: String) {
        if (name.isEmpty()) {
            binding.edName.setHintTextColor(Color.RED)
        } else if (location.isEmpty()) {
            binding.edLocation.setHintTextColor(Color.RED)
        } else {
            var user = User(System.currentTimeMillis(), name, location)
            userViewModel.createUser(user)
        }
    }

    private fun initObservers() {
        userViewModel.addUser.observe(this) {
            when(it) {
                is DataState.Loading -> {
                    showProgress(true)
                }
                is DataState.Error -> {
                    showProgress(false)
                    Toast.makeText(this, "Ocorreu um erro", Toast.LENGTH_SHORT).show()
                }
                is DataState.Success -> {
                    Toast.makeText(this, "Adicionado com sucesso", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else -> {}
            }
        }
    }

    private fun showProgress(isLoading: Boolean) {
        if (isLoading) {
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.GONE
        }
    }

}
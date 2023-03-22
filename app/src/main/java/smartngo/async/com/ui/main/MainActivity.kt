package smartngo.async.com.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import smartngo.async.com.R
import smartngo.async.com.core.DataState
import smartngo.async.com.ui.adapter.UserAdapter
import smartngo.async.com.databinding.ActivityMainBinding
import smartngo.async.com.domain.model.User
import smartngo.async.com.ui.form.AddActivity
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var userViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        userViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        adapter = UserAdapter {}

        initObservers()

        binding.fab.setOnClickListener { view ->
            Intent(this, AddActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_settings -> {

                userViewModel.asyncUserData()
                Toast.makeText(this, "Sincronizando...", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showUsers(usersList: List<User>) {
        adapter.clear()
        binding.rvUsers.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvUsers.adapter = adapter
        adapter.insertAll(usersList)
    }

    private fun initObservers() {
        userViewModel.users.observe(this) {
            when(it) {
                is DataState.Loading -> {
                    showProgress(true)
                }
                is DataState.Error -> {
                    showProgress(false)
                    Timber.e("Error: ${it.message}")
                    Toast.makeText(this, "Ocorreu um erro", Toast.LENGTH_SHORT).show()
                }
                is DataState.Success -> {
                    Timber.e("Sucess: ${it.data}")
                    showProgress(false)
                    showUsers(it.data)
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

    override fun onRestart() {
        super.onRestart()
        userViewModel.getAllUsers()
    }

    override fun onResume() {
        super.onResume()
        userViewModel.getAllUsers()
    }

}
package com.pratthamarora.rickandmorty_graphql

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.coroutines.toDeferred
import com.pratthamarora.rickandmorty_graphql.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private var characterList: List<GetCharactersQuery.Result?>? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var charactersAdapter: CharactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        lifecycleScope.launch(Main) {
            getCharacters()
        }
    }

    private suspend fun getCharacters() {
        withContext(IO) {
            val response = AplClient.getApolloClient(this@MainActivity)!!
                .query(GetCharactersQuery())
                .toDeferred()
                .await()
            characterList = response.data?.characters?.results
        }
        if (!characterList.isNullOrEmpty()) {
            binding.apply {
                charactersAdapter.setCharacters(characterList!!.toList())
                progressBar.isGone = true
                charactersRecyclerview.isVisible = true
            }
        } else {
            binding.progressBar.isGone = true
            Toast.makeText(this, "Some error occured", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        charactersAdapter = CharactersAdapter()
        binding.charactersRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
            adapter = charactersAdapter

        }
    }
}
package com.pratthamarora.rickandmorty_graphql

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.pratthamarora.rickandmorty_graphql.databinding.CharacterItemBinding


class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {

    private val characters = ArrayList<GetCharactersQuery.Result?>()

    class CharactersViewHolder(val binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun setCharacter(character: GetCharactersQuery.Result) {

            binding.apply {
                characterName.text = character.name
                characterGender.text = character.gender
                characterStatus.text = character.status

                //set company logo
                characterLogo.load(character.image) {
                    fallback(R.drawable.ic_launcher_foreground)
                    error(R.drawable.ic_launcher_foreground)
                    crossfade(true)
                    crossfade(1000)
                    scale(Scale.FIT)
                    transformations(RoundedCornersTransformation(20f))
                }
            }
        }
    }

    fun setCharacters(character: List<GetCharactersQuery.Result?>) {
        characters.clear()
        characters.addAll(character)
        this.notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharactersViewHolder {
        val binding =
            CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersViewHolder(binding)
    }

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {

        val character: GetCharactersQuery.Result? = this.characters[position]
        character?.let { holder.setCharacter(it) }
    }
}
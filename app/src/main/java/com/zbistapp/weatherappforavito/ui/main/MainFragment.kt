package com.zbistapp.weatherappforavito.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.zbistapp.weatherappforavito.R

class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                //todo
                return true
            }
            R.id.my_location -> {
                //todo
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
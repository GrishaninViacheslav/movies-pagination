package io.github.grishaninvyacheslav.theatre_movies_pagination.ui.screens

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import io.github.grishaninvyacheslav.theatre_movies_pagination.ui.fragments.DetailsFragment
import io.github.grishaninvyacheslav.theatre_movies_pagination.ui.fragments.MainFragment

class Screens: IScreens {
    override fun main() = FragmentScreen { MainFragment.newInstance() }
    override fun details(id: String): Screen = FragmentScreen { DetailsFragment.newInstance(id) }
}
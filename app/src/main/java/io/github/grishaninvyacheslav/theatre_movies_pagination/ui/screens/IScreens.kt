package io.github.grishaninvyacheslav.theatre_movies_pagination.ui.screens

import com.github.terrakok.cicerone.Screen

interface IScreens {
    fun main(): Screen
    fun details(id: String): Screen
}
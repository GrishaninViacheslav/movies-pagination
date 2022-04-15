package io.github.grishaninvyacheslav.theatre_movies_pagination.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import io.github.grishaninvyacheslav.theatre_movies_pagination.R
import io.github.grishaninvyacheslav.theatre_movies_pagination.ui.screens.IScreens
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val cicerone: Cicerone<Router> by inject()
    private val router: Router by inject()
    private val screens: IScreens by inject()

    private val navigator: Navigator by lazy {
        AppNavigator(this, R.id.container, supportFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            router.replaceScreen(screens.main())
        }
    }

    override fun onResume() {
        super.onResume()
        cicerone.getNavigatorHolder().setNavigator(navigator)
    }

    override fun onPause() {
        cicerone.getNavigatorHolder().removeNavigator()
        super.onPause()
    }
}
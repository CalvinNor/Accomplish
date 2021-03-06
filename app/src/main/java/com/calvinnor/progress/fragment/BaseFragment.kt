package com.calvinnor.progress.fragment

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.MenuRes
import android.support.v4.app.Fragment
import android.view.*
import com.calvinnor.progress.contract.DataProxy
import com.calvinnor.progress.injection.dependencyComponent
import javax.inject.Inject

/**
 * Base Fragment to inherit from.
 * All common code and abstraction goes here.
 */
abstract class BaseFragment : Fragment() {

    companion object {
        private const val NO_LAYOUT = -1
    }

    @Inject
    protected lateinit var dataProxy: DataProxy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dependencyComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return getInflatedView(inflater)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(menu != NO_LAYOUT)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        if (this.menu == NO_LAYOUT) return
        inflater?.inflate(this.menu, menu)
    }

    /**
     * Override this method to provide a fragment layout.
     */
    @LayoutRes
    open protected val layout = NO_LAYOUT

    /**
     * Override this value to provide a Menu.
     */
    @MenuRes
    open protected val menu = NO_LAYOUT

    /**
     * Override this value to provide a fragment tag.
     * This will be used in Fragment Transactions.
     */
    abstract val fragmentTag: String

    private fun getInflatedView(inflater: LayoutInflater): View? {
        val activityLayout = layout
        return if (activityLayout == NO_LAYOUT) null
        else inflater.inflate(layout, null, false)
    }
}

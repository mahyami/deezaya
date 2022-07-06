package com.mahyamir.deezaya.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Reduces the boiler plate code in using view binding.
 * Note that you still need to inflate the layout in  {@link Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)}.
 * @see {@link https://zhuinden.medium.com/simple-one-liner-viewbinding-in-fragments-and-activities-with-kotlin-961430c6c07c}
 * @see {@link https://proandroiddev.com/make-android-view-binding-great-with-kotlin-b71dd9c87719}
 *
 * ** This code was developed in the Swapcard corporation. **
 * */

fun <T : ViewBinding> Fragment.viewBinding(clearBinding: (binding: T) -> Unit = {}) =
    FragmentViewBindingDelegate(this, clearBinding)

class FragmentViewBindingDelegate<T : ViewBinding>(
    val fragment: Fragment,
    clearBinding: (binding: T) -> Unit
) : ReadWriteProperty<Fragment, T> {
    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            val viewLifecycleOwnerLiveDataObserver =
                Observer<LifecycleOwner?> {
                    val viewLifecycleOwner = it ?: return@Observer
                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            binding?.also { b ->
                                clearBinding(b)
                            }
                            binding = null
                        }
                    })
                }

            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever(
                    viewLifecycleOwnerLiveDataObserver
                )
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver(
                    viewLifecycleOwnerLiveDataObserver
                )
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return binding ?: throw IllegalStateException("Binding is not set!")
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        if (binding != null) throw IllegalAccessError("Binding is already set!")
        binding = value
    }
}
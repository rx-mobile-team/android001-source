package com.rxmobileteam.lecture9sample.features.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import com.rxmobileteam.lecture9sample.ServiceLocator
import com.rxmobileteam.lecture9sample.databinding.ActivitySearchBinding
import com.rxmobileteam.lecture9sample.features.search.SearchManageAdapter.Companion.TAB_PHOTOS
import com.rxmobileteam.lecture9sample.features.search.SearchManageAdapter.Companion.TAB_USERS
import com.rxmobileteam.lecture9sample.utils.Logger
import com.rxmobileteam.lecture9sample.utils.MySharePref
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityRetainedScope
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class SearchActivity : AppCompatActivity(), AndroidScopeComponent {

  private val binding by lazy {
    ActivitySearchBinding.inflate(layoutInflater)
  }
  override val scope: Scope by activityRetainedScope()
  init {
    loadKoinModules(featureSearch)
  }

  private val mySharePref: MySharePref by inject()

  private val viewModel: SearchViewModel by viewModel()


  private val logger1: Logger by inject(qualifier = named("LoggerConsole"))

  private val logger2: Logger by inject(qualifier = named("LoggerFileSystem"))


  private val tabIndex by lazy {
    mapOf(
      TAB_PHOTOS to "Photo",
      TAB_USERS to "Users"
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupAdapterTab()
    setupSearchText()

    mySharePref.saveString("SearchActivity", "Hello")

    logger1.logMessage("From LoggerConsole")
    logger2.logMessage("From LoggerFileSystem")

    Log.d("Logger", "ConsoleLoggerImpl + ${logger1.hashCode()}")
    Log.d("Logger", "ConsoleLoggerImpl + ${logger2.hashCode()}")

    Log.d("SearchActivity", "onCreate MyPref:  ${mySharePref.hashCode()}")
  }

  private fun setupSearchText() {
    binding.searchEdt.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

      }

      override fun afterTextChanged(s: Editable?) {
        viewModel.queryTextChange(s?.toString() ?: return)

        val valueStringMySharePref = mySharePref.getString("SearchActivity")

        Log.d("SearchActivity", "afterTextChanged:  $valueStringMySharePref")
      }
    })
  }

  private fun setupAdapterTab() = binding.apply {
    val adapter = SearchManageAdapter(this@SearchActivity)
    viewPager.adapter = adapter
    TabLayoutMediator(tabLayout, viewPager) { tab, positon ->
      tab.text = tabIndex[positon]
    }.attach()
  }


}
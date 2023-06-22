package com.rxmobileteam.lecture9sample.features.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import com.rxmobileteam.lecture9sample.ServiceLocator
import com.rxmobileteam.lecture9sample.databinding.ActivitySearchBinding
import com.rxmobileteam.lecture9sample.features.search.SearchManageAdapter.Companion.TAB_PHOTOS
import com.rxmobileteam.lecture9sample.features.search.SearchManageAdapter.Companion.TAB_USERS

class SearchActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<SearchViewModel>(
        factoryProducer = {
            viewModelFactory {
                addInitializer(SearchViewModel::class) {
                    SearchViewModel(unsplashApiService = ServiceLocator.unsplashApiService)
                }
            }
        }
    )

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
    }

    private fun setupSearchText() {
        binding.searchEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (binding.rdbSearchByUser.isChecked){
                    viewModel.queryTextChangeSearchUser(s.toString())
                }else{
                    viewModel.queryTextChangeSearchPhoto(s.toString())
                }
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
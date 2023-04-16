package com.xiong.aithelp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class apiViewModelFactory:ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return super.create(modelClass)
    }
}
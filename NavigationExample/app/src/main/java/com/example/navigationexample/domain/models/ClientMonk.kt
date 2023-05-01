package com.example.navigationexample.domain.models

import com.example.navigationexample.data.entity.Client

data class ClientMonk(val client: Client,
                      val appatment: String,
                      val color: Int,
                      val ieEnable: Boolean,
                      val ieStartDay: Boolean,
                      val ieEndDay: Boolean,

) {
}
package com.example.navigationexample.domain.models

import com.example.navigationexample.data.entity.Client

data class ClientMonk(val client: Client,
                      val appatment: String,
                      val color: Int,
                      val isEnable: Boolean,
                      val isStartDay: Boolean,
                      val ieEndDay: Boolean,
                      ) {
}
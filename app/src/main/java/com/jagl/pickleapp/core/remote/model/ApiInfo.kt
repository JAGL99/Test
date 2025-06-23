package com.jagl.pickleapp.core.remote.model

data class ApiInfo(
    val count: Int,
    val next: String?,
    val pages: Int,
    val prev: String?
)
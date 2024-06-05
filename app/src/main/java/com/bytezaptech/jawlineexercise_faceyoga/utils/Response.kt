package com.bytezaptech.jawlineexercise_faceyoga.utils

sealed class Response
class Success<T>(val data: T, val days: T): Response()
class Error(val error: String): Response()
class Progress: Response()
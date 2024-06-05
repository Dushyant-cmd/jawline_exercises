package com.bytezaptech.jawlineexercise_faceyoga.utils

sealed class Response
class Success<T>(val data: T): Response()
class Error(val error: String): Response()
class Progress: Response()

sealed class ExerciseResponse
class ExerciseSuccess<T>(val data: T, val days: T): ExerciseResponse()
class ExerciseError(val error: String): ExerciseResponse()
class ExerciseProgress: ExerciseResponse()
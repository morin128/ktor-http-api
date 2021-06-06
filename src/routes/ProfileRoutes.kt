package com.example.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.File

@KtorExperimentalLocationsAPI
fun Application.profileRoutes() {
    routing {
        route("/profile") {
            downloadProfileImage()
        }
    }
}

@KtorExperimentalLocationsAPI
fun Route.downloadProfileImage() {
    get("/download") {
        val iamgeFileName = "profile.jpg"
        val file = File("files/$iamgeFileName")
        call.response.header(
            HttpHeaders.ContentDisposition,
            ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, iamgeFileName).toString()
        )
        call.respondFile(file)
    }
}
package com.example

import com.example.routes.profileRoutes
import com.example.routes.registerCustomerRoutes
import com.example.routes.registerOrderRoutes
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.serialization.DefaultJsonConfiguration
import io.ktor.serialization.json
import kotlinx.serialization.json.Json

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalLocationsAPI
fun Application.module(testing: Boolean = false) {

    // see. https://ktor.io/servers/features/content-negotiation/serialization-converter.html
    install(ContentNegotiation) {
        // "serialization()" is deprecated. Use json instead.
        json(
                json = Json(configuration = DefaultJsonConfiguration.copy(prettyPrint = true)),
                contentType = ContentType.Application.Json
        )
    }

    install(Locations)

    registerCustomerRoutes()
    registerOrderRoutes()
    profileRoutes()
}
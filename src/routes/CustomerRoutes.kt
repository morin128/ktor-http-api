package com.example.routes

import com.example.models.Customer
import com.example.models.customerStorage
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*

fun Application.registerCustomerRoutes() {
    routing {
        route("/customer") {
            getAllCustomers()
            getCustomerById()
            createCustomer()
            deleteCustomerById()
        }
    }
}

fun Route.getAllCustomers() {
    get() {
        if(customerStorage.isNotEmpty()) {
            call.respond(customerStorage)
        } else {
            call.respondText("No customer found.", status = HttpStatusCode.NotFound)
        }
    }
}

fun Route.getCustomerById() {
    get("{id}") {
        val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
        )
        val customer = customerStorage.find { it.id == id } ?: return@get call.respondText(
                "No customer found by id $id",
                status = HttpStatusCode.NotFound
        )
        call.respond(customer)
    }
}

fun Route.createCustomer() {
    post {
        val customer = call.receive<Customer>()
        customerStorage.add(customer)
        call.respondText("Customer added correctly.", status = HttpStatusCode.Accepted)
    }
}

fun Route.deleteCustomerById() {
    delete("{id}") {
        val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
        if (customerStorage.removeIf { it.id == id }) {
            call.respondText("Customer removed correctly.", status = HttpStatusCode.Accepted)
        } else {
            call.respondText("Not found.", status = HttpStatusCode.NotFound)
        }
    }
}
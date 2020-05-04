package com.example

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class OrderRouteTests {

    @Test
    fun testGetOrderList() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/order").apply {
                assertEquals("""
                    [
                        {
                            "number": "2020-04-06-01",
                            "contents": [
                                {
                                    "item": "Ham Sandwich",
                                    "amount": 2,
                                    "price": 5.5
                                },
                                {
                                    "item": "Water",
                                    "amount": 1,
                                    "price": 1.5
                                },
                                {
                                    "item": "Beer",
                                    "amount": 3,
                                    "price": 2.3
                                },
                                {
                                    "item": "Cheesecake",
                                    "amount": 1,
                                    "price": 3.75
                                }
                            ]
                        },
                        {
                            "number": "2020-04-03-01",
                            "contents": [
                                {
                                    "item": "Cheeseburger",
                                    "amount": 1,
                                    "price": 8.5
                                },
                                {
                                    "item": "Water",
                                    "amount": 2,
                                    "price": 1.5
                                },
                                {
                                    "item": "Coke",
                                    "amount": 2,
                                    "price": 1.76
                                },
                                {
                                    "item": "Ice Cream",
                                    "amount": 1,
                                    "price": 2.35
                                }
                            ]
                        }
                    ]
                """.trimIndent(),
                response.content)
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun testGetOrder() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/order/2020-04-06-01").apply {
                assertEquals(
                        """
                            {
                                "number": "2020-04-06-01",
                                "contents": [
                                    {
                                        "item": "Ham Sandwich",
                                        "amount": 2,
                                        "price": 5.5
                                    },
                                    {
                                        "item": "Water",
                                        "amount": 1,
                                        "price": 1.5
                                    },
                                    {
                                        "item": "Beer",
                                        "amount": 3,
                                        "price": 2.3
                                    },
                                    {
                                        "item": "Cheesecake",
                                        "amount": 1,
                                        "price": 3.75
                                    }
                                ]
                            }
                        """.trimIndent(),
                        response.content
                )
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun testTotalizedOrder() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/order/2020-04-06-01/total").apply {
                assertEquals("Total for order is 23.15", response.content)
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}
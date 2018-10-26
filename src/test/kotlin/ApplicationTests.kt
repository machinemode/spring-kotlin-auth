package com.machinemode.auth

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@RunWith(SpringRunner::class)
@ActiveProfiles("test")
@SpringBootTest
class ApplicationTests {

    @Autowired
    private lateinit var context: WebApplicationContext

    private lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply<DefaultMockMvcBuilder>(springSecurity())
                .build()
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should redirect to a login page`() {
        val request = MockMvcRequestBuilders.get("/")
        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection)
    }

    @Test
    fun `userinfo should require authorization`() {
        val request = MockMvcRequestBuilders.get("/userinfo")
        mockMvc.perform(request)
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun `health should return ok`() {
        val request = MockMvcRequestBuilders.get("/actuator/health")
        mockMvc.perform(request)
                .andExpect(status().isOk)
    }
}

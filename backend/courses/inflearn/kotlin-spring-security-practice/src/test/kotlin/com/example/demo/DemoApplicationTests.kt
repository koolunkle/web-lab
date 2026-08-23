package com.example.demo

import com.example.demo.DemoApplication
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [DemoApplication::class])
class DemoApplicationTests {

	@Test
	fun contextLoads() {
	}

}

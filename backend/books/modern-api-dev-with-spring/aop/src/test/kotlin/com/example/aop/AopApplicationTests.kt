package com.example.aop

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@SpringBootTest
class AopApplicationTests {

	@Autowired
	lateinit var testBean: com.example.aop.Test

	@Test
	fun contextLoads() {
	}

	@Test
	fun `performSomeTask 호출 시 TimeMonitorAspect가 실행 시간을 로깅한다`() {
		val originalOut = System.out
		val captured = ByteArrayOutputStream()
		System.setOut(PrintStream(captured))
		try {
			testBean.performSomeTask()
		} finally {
			System.setOut(originalOut)
		}

		val output = captured.toString()
		assertTrue(output.contains("Task completed"), "performSomeTask 본문이 실행되지 않음")
		assertTrue(output.contains("performSomeTask"), "Aspect 로그에 join point 시그니처가 없음")
		assertTrue(output.contains("takes:"), "Aspect가 실행 시간을 로깅하지 않음")
	}

}

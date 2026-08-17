package com.example.aop

import org.springframework.stereotype.Component

@Component
class Test {

    @TimeMonitor
    fun performSomeTask() {
        try {
            Thread.sleep(1_000L)
            println("Task completed")
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}

// public class Test {

//     @TimeMonitor
//     public void performSomeTask() {
//         try {
//             Thread.sleep(1_000L);
//             System.out.println("Task completed");
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         }
//     }
// }

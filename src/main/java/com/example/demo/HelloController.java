package com.example.demo;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/messages")
public class HelloController {

    private final Map<Integer, String> messages = new LinkedHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    // Create (POST)
    @PostMapping("/create")
    public Map<String, String> createMessage(@RequestBody String message) {
        int id = idCounter.getAndIncrement();
        messages.put(id, message);
        Map<String, String> response = new LinkedHashMap<>();
        response.put("id", String.valueOf(id));
        response.put("message", message);
        return response;
    }

    // Read (GET)
    @GetMapping("/{id}")
    public String getMessage(@PathVariable int id) {
        return messages.getOrDefault(id, "Message not found");
    }

    // Update (Put)
    @PutMapping("/{id}")
    public Map<String, String> updateMessage(@PathVariable int id, @RequestBody String updateMessage) {
        if (messages.containsKey(id)) {
            messages.put(id, updateMessage);
            Map<String, String> response = new LinkedHashMap<>();
            response.put("id", String.valueOf(id));
            response.put("message", updateMessage);
            return response;
        } else {
            throw new IllegalArgumentException("Message not found");
        }
    }

    // Delete (DELETE)
    @DeleteMapping("/{id}")
    public String deleteMessage(@PathVariable int id) {
        if (messages.containsKey(id)) {
            messages.remove(id);
            return "Message deleted";
        } else {
            return "Message not found";
        }
    }

    // List all message (GET)
    @GetMapping("/all")
    public Map<Integer, String> getAllMessages() {
        return messages;
    }
}

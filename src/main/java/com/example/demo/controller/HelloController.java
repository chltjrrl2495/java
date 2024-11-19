package com.example.demo.controller;
import com.example.demo.entity.Message;
import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicInteger;


@RestController
@RequestMapping("/messages")
public class HelloController {

    @Autowired
    Optional<Message> message = messageRepository.findById(id);
//    private MessageRepository messageRepository;

    // Create (POST)
    @PostMapping("/create")
    public Message createMessage(@RequestBody String content) {
        Message message = new Message(content);
        return messageRepository.save(message);
    }

    // Read (GET)
    @GetMapping("/{id}")
    public String getMessage(@PathVariable Long id) {
        Optional<Message> message = messageRepository.findById(id);
        return message.map(Message::getContent).orElse("Message not found");
    }

    // Update (Put)
    @PutMapping("/{id}")
    public String getMessage(@PathVariable Long id, @RequestBody String updateContent) {
        Optional<Message> existngMessage = messageRepository.findById(id);
        if (existngMessage.isPresent()) {
            Message message = existngMessage.get();
            message.setContent(updateContent);
            return messageRepository.save(message);
        } else {
            throw new IllegalArgumentException("Message not found");
        }
    }

    // Delete (DELETE)
    @DeleteMapping("/{id}")
    public String deleteMessage(@PathVariable Long id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return "Message deleted";
        } else {
            return "Message not found";
        }
    }

    // List all message (GET)
    @GetMapping("/all")
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}

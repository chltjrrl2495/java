package com.example.demo.controller;

import com.example.demo.entity.Message;
import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
public class HelloController {

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping("/create")
    public Message createMessage(@RequestBody String content) {
        return messageRepository.save(new Message(content));
    }

    @GetMapping("/{id}")
    public String getMessage(@PathVariable Long id) {
        Optional<Message> message = messageRepository.findById(id);
        return message.map(Message::getContent).orElse("Message not found");
    }

    @PutMapping("/{id}")
    public Message updateMessage(@PathVariable Long id, @RequestBody String updatedContent) {
        return messageRepository.findById(id)
                .map(msg -> {
                    msg.setContent(updatedContent);
                    return messageRepository.save(msg);
                })
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));
    }

    @DeleteMapping("/{id}")
    public String deleteMessage(@PathVariable Long id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return "Message deleted successfully";
        }
        return "Message not found";
    }

    @GetMapping("/all")
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}

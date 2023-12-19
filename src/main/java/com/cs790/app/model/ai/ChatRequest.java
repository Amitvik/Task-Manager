package com.cs790.app.model.ai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {

  /*  n – can be specified if we want to increase the number of responses to generate. The default value is 1.
    temperature – controls the randomness of the response. The default value is 1 (most random).
    max_tokens – is used to limit the maximum number of tokens in the response.
    The default value is infinity which means that the response will be as long as the model can generate. Generally, it would be a good idea to set this value to a reasonable number to avoid generating very long responses and incurring a high cost.
   */

//    Gpt version to be specified
  private String model;
    private List<Message> messages;

//    n – can be specified if we want to increase the number of responses to generate.
//    The default value is 1.
    private int n;


//    temperature – controls the randomness of the response.
    private double temperature;
//    private int maxTokens;

    public ChatRequest(String model, String prompt) {
        this.model = model;

        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }
}
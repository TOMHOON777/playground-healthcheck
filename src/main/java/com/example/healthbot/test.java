// package com.example.healthbot;

// import java.io.IOException;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.MediaType;
// import org.springframework.scheduling.annotation.EnableScheduling;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;
// import org.springframework.web.client.RestTemplate;

// @SpringBootApplication
// @EnableScheduling
// public class HealthbotApplication {

//   public static void main(String[] args) throws IOException, InterruptedException {
//     SpringApplication.run(HealthbotApplication.class, args);
//   }

//   @Component
//   class WebhookTask {
//     private final RestTemplate restTemplate = new RestTemplate();
//     private final String webhookUrl = "https://discord.com/api/webhooks/1423831190523215924/xIkTQCp1HXinIvQgVOZ4Yr_hVsFwpTAb-7GNr28Rm21zgCptJyJijm5Hfktrlw8E_Dqv";

//     @Scheduled(fixedRate = 3600000) // every 1 hour (3600 * 1000 ms)
//     public void sendWebhook() {
//       HttpHeaders headers = new HttpHeaders();
//       headers.setContentType(MediaType.APPLICATION_JSON);
//       String payload = "{ \"content\": \"🚨 ALERT: 서버 응답없음! 확인 필요...!!\" }";

//       HttpEntity<String> request = new HttpEntity<>(payload, headers);
//       String response = restTemplate.postForObject(webhookUrl, request, String.class);

//       System.out.println("Webhook response: " + response);
//     }
//   }
// }
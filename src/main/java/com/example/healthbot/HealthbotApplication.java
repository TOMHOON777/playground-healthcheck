package com.example.healthbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class HealthbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthbotApplication.class, args);
	}

	@Component
	class WebhookTask {
		private final RestTemplate restTemplate = new RestTemplate();

		// your Discord webhook URL
		private final String webhookUrl = "https://discord.com/api/webhooks/1423831190523215924/xIkTQCp1HXinIvQgVOZ4Yr_hVsFwpTAb-7GNr28Rm21zgCptJyJijm5Hfktrlw8E_Dqv";

		// the site you want to monitor
		private final String targetUrl = "http://tomhoon.my:780";

		@Scheduled(fixedRate = 3600000)
		public void checkHealthAndNotify() {
			try {
				// try to GET the target site
				restTemplate.getForObject(targetUrl, String.class);
				System.out.println("✅ Target is OK: " + targetUrl);
			} catch (RestClientException e) {
				// if failed to connect, send webhook
				sendWebhook("🚨 ALERT: 서버 응답없음! 확인 필요!\n```\n" + e.getMessage() + "\n```");
			}
		}

		private void sendWebhook(String message) {
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				String payload = "{ \"content\": \"" + message.replace("\"", "\\\"") + "\" }";

				HttpEntity<String> request = new HttpEntity<>(payload, headers);
				String response = restTemplate.postForObject(webhookUrl, request, String.class);
				System.out.println("Webhook response: " + response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

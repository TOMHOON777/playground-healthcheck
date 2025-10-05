package com.example.healthbot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		private final String ateam_webhookUrl = "https://discord.com/api/webhooks/1424183798739894324/NLQuobDOnL4CM0jHqXyxDjS2POsYhYaZ7bUyzsuy28HCmtcbQk590_0iMUffDCwifNV1";
		private final String bteam_webhookUrl = "https://discord.com/api/webhooks/1423831190523215924/xIkTQCp1HXinIvQgVOZ4Yr_hVsFwpTAb-7GNr28Rm21zgCptJyJijm5Hfktrlw8E_Dqv";

		// the site you want to monitor
		private final String ateam_targetUrl = "http://49.247.160.225";
		private final String bteam_targetUrl = "http://tomhoon.my:780";

		@Scheduled(fixedRate = 3600000)
		public void checkHealthAndNotify() {
			List<HashMap> hooks = new ArrayList();
			HashMap<String, String> ateam = new HashMap<>();
			HashMap<String, String> bteam = new HashMap<>();
			ateam.put("webhookUrl", ateam_webhookUrl);
			ateam.put("targetUrl", ateam_targetUrl);

			bteam.put("webhookUrl", bteam_webhookUrl);
			bteam.put("targetUrl", bteam_targetUrl);

			hooks.add(ateam);
			hooks.add(bteam);

			hooks.forEach(item -> {
				try {
					// try to GET the target site
					restTemplate.getForObject((String)item.get("targetUrl"), String.class);
					System.out.println("✅ Target is OK: " + item.get("targetUrl"));
				} catch (RestClientException e) {
					// if failed to connect, send webhook
					sendWebhook("🚨 ALERT: 서버 응답없음! 확인 필요!\n```\n" + e.getMessage() + "\n```", (String)item.get("webhookUrl"));
				}
			});
		}

		private void sendWebhook(String message, String url) {
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				// Use a Map instead of manual string
				Map<String, String> payload = new HashMap<>();
				payload.put("content", message);

				HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);
				String response = restTemplate.postForObject(url, request, String.class);
				System.out.println("Webhook response: " + response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

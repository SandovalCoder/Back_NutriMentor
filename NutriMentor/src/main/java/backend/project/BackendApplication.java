package backend.project;

import backend.project.dtos.DTOUser;
import backend.project.entities.*;
import backend.project.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;


@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner mappingDemo(
			AuthorityService authorityService,
			UserService userService,
			ClientService clientService,
			CategoryService categoryService,
			ProductService productService,
			BuysService buysService,
			BuyDetailService buyDetailService,
			HealthProfessionalService healthProfessionalService,
			HealthGoalService healthGoalService,
			RecommendationService recommendationService,
			ReviewService reviewService,
			QuestionService questionService,
			SpecializationService specializationService,
			TrackingService trackingService
	) {
		return args -> {
			// Insert Authorities (Roles)
			Authority adminAuthority = authorityService.insertAuthority(new Authority(0L, "ADMIN", null));
			Authority userAuthority = authorityService.insertAuthority(new Authority(0L, "USER", null));
			Authority healthProfessionalAuthority = authorityService.insertAuthority(new Authority(0L, "HEALTH_PROFESSIONAL", null));

			// Insert Clients
			Client client1 = clientService.insertClient(new Client(0L, "Alice", "alice@example.com", "123 Street", null, null, null, null, null));
			Client client2 = clientService.insertClient(new Client(0L, "Bob", "bob@example.com", "456 Avenue", null, null, null, null, null));
			Client client3 = clientService.insertClient(new Client(0L, "Charlie", "charlie@example.com", "789 Boulevard", null, null, null, null, null));
			Client client4 = clientService.insertClient(new Client(0L, "Diana", "diana@example.com", "1010 Road", null, null, null, null, null));

			// Insert Users
			userService.addUser(new DTOUser(null, "admin", "password", true, client1.getId(), "ADMIN;USER"));
			userService.addUser(new DTOUser(null, "user1", "password", true, client2.getId(), "USER"));
			userService.addUser(new DTOUser(null, "user2", "password", true, client3.getId(), "USER"));
			userService.addUser(new DTOUser(null, "healthpro", "password", true, client4.getId(), "HEALTH_PROFESSIONAL"));

			// Insert Categories
			Category category1 = categoryService.insertCategory(new Category(0L, "Electronics", "Electronic gadgets and devices", null));
			Category category2 = categoryService.insertCategory(new Category(0L, "Books", "All kinds of books", null));
			Category category3 = categoryService.insertCategory(new Category(0L, "Clothing", "Men and women apparel", null));
			Category category4 = categoryService.insertCategory(new Category(0L, "Health", "Health and wellness products", null));

			// Insert Products
			Product product1 = productService.insertProduct(new Product(0L, "Smartphone", new BigDecimal("299.99"), 50, category1, null, null));
			Product product2 = productService.insertProduct(new Product(0L, "Novel Book", new BigDecimal("15.99"), 150, category2, null, null));
			Product product3 = productService.insertProduct(new Product(0L, "T-shirt", new BigDecimal("9.99"), 200, category3, null, null));
			Product product4 = productService.insertProduct(new Product(0L, "Vitamins", new BigDecimal("20.00"), 100, category4, null, null));

			// Insert Buys (Orders)
			Buys buys1 = buysService.insertBuys(new Buys(0L, new Date(), new BigDecimal("100.00"), client1, null));
			Buys buys2 = buysService.insertBuys(new Buys(0L, new Date(), new BigDecimal("45.00"), client2, null));
			Buys buys3 = buysService.insertBuys(new Buys(0L, new Date(), new BigDecimal("30.00"), client3, null));
			Buys buys4 = buysService.insertBuys(new Buys(0L, new Date(), new BigDecimal("50.00"), client4, null));

			// Insert BuyDetails
			buyDetailService.insertBuyDetail(new BuyDetail(0L, 2, new BigDecimal("59.98"), buys1, product1));
			buyDetailService.insertBuyDetail(new BuyDetail(0L, 3, new BigDecimal("47.97"), buys2, product3));
			buyDetailService.insertBuyDetail(new BuyDetail(0L, 1, new BigDecimal("15.99"), buys3, product2));
			buyDetailService.insertBuyDetail(new BuyDetail(0L, 5, new BigDecimal("100.00"), buys4, product4));

			// Insert Specializations
			Specialization specialization1 = specializationService.insertSpecialization(new Specialization(0L, "Nutrition", "Expert in nutrition and dietetics", null));
			Specialization specialization2 = specializationService.insertSpecialization(new Specialization(0L, "Physiotherapy", "Expert in physical therapy", null));
			Specialization specialization3 = specializationService.insertSpecialization(new Specialization(0L, "Psychology", "Expert in mental health", null));
			Specialization specialization4 = specializationService.insertSpecialization(new Specialization(0L, "Sports Medicine", "Expert in sports injuries and performance", null));

			// Insert Health Professionals
			HealthProfessional hp1 = healthProfessionalService.insertHealthProfessional(new HealthProfessional(0L, "Dr. Smith", "smith@example.com", "Health Center 1", specialization1, null, null));
			HealthProfessional hp2 = healthProfessionalService.insertHealthProfessional(new HealthProfessional(0L, "Dr. Adams", "adams@example.com", "Health Center 2", specialization2, null, null));
			HealthProfessional hp3 = healthProfessionalService.insertHealthProfessional(new HealthProfessional(0L, "Dr. Johnson", "johnson@example.com", "Health Center 3", specialization3, null, null));
			HealthProfessional hp4 = healthProfessionalService.insertHealthProfessional(new HealthProfessional(0L, "Dr. White", "white@example.com", "Health Center 4", specialization4, null, null));

			// Insert Health Goals
			HealthGoal healthGoal1 = healthGoalService.insertHealthGoal(new HealthGoal(0L, "Lose Weight", "Low Carb", client1, hp1, null, null));
			HealthGoal healthGoal2 = healthGoalService.insertHealthGoal(new HealthGoal(0L, "Build Muscle", "High Protein", client2, hp2, null, null));
			HealthGoal healthGoal3 = healthGoalService.insertHealthGoal(new HealthGoal(0L, "Mental Wellness", "Balanced Diet", client3, hp3, null, null));
			HealthGoal healthGoal4 = healthGoalService.insertHealthGoal(new HealthGoal(0L, "Injury Recovery", "Balanced Nutrition", client4, hp4, null, null));

			// Insert Recommendations
			recommendationService.insertRecommendation(new Recommendation(0L, "Eat more fruits and vegetables", healthGoal1));
			recommendationService.insertRecommendation(new Recommendation(0L, "Increase protein intake", healthGoal2));
			recommendationService.insertRecommendation(new Recommendation(0L, "Practice mindfulness daily", healthGoal3));
			recommendationService.insertRecommendation(new Recommendation(0L, "Consume supplements to aid recovery", healthGoal4));

			// Insert Reviews
			reviewService.insertReview(new Review(0L, 5, "Great product!", product1, client1));
			reviewService.insertReview(new Review(0L, 4, "Very informative book.", product2, client2));
			reviewService.insertReview(new Review(0L, 3, "Comfortable t-shirt.", product3, client3));
			reviewService.insertReview(new Review(0L, 5, "Excellent vitamins for health.", product4, client4));

			// Insert Questions
			questionService.insertQuestion(new Question(0L, "How to lose weight?", "Eat balanced meals.", LocalDateTime.now(), LocalDateTime.now().plusDays(1), client1, hp1));
			questionService.insertQuestion(new Question(0L, "Best exercises for back pain?", "Stretch and strengthen.", LocalDateTime.now(), LocalDateTime.now().plusDays(1), client2, hp2));
			questionService.insertQuestion(new Question(0L, "How to reduce stress?", "Practice meditation.", LocalDateTime.now(), LocalDateTime.now().plusDays(1), client3, hp3));
			questionService.insertQuestion(new Question(0L, "How to recover from injury quickly?", "Rest and balanced diet.", LocalDateTime.now(), LocalDateTime.now().plusDays(1), client4, hp4));

			// Insert Trackings
			trackingService.insertTracking(new Tracking(0L, LocalDateTime.now(), LocalDateTime.now().plusDays(30), "70kg", "170cm", "Ongoing", healthGoal1));
			trackingService.insertTracking(new Tracking(0L, LocalDateTime.now(), LocalDateTime.now().plusDays(30), "75kg", "175cm", "Ongoing", healthGoal2));
			trackingService.insertTracking(new Tracking(0L, LocalDateTime.now(), LocalDateTime.now().plusDays(30), "65kg", "165cm", "Ongoing", healthGoal3));
			trackingService.insertTracking(new Tracking(0L, LocalDateTime.now(), LocalDateTime.now().plusDays(30), "80kg", "180cm", "Ongoing", healthGoal4));
		};
	}
}

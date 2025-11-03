package com.lucasavs.market_data_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
//@ActiveProfiles({"jdbc", "test"})
@ActiveProfiles({"jpa", "test"})
class MarketDataServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}

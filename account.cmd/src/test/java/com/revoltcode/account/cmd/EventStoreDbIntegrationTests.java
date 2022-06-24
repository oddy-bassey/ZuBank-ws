package com.revoltcode.account.cmd;

import com.mongodb.client.MongoClients;
import com.revoltcode.account.cmd.domain.aggregate.AccountAggregate;
import com.revoltcode.account.common.dto.AccountType;
import com.revoltcode.account.common.event.account.AccountOpenedEvent;
import com.revoltcode.cqrs.core.event.EventModel;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource("/application.yml")
@SpringBootTest
class EventStoreDbIntegrationTests {

	@Autowired
	private MongoTemplate mongoTemplate;

	/* setup MongoDB configuration manually to particularly test specific versions
	 * or allow spring to automatically configure the DB
	 */
	@Autowired
	private MongodExecutable mongodExecutable;

	// @BeforeEach
	void setup() throws Exception {
		String connectionString = "mongodb://%s:%d";
		String ip = "localhost";
		int port = 27017;

		ImmutableMongodConfig mongodConfig = MongodConfig.builder()
				.version(Version.V4_0_2)
				.net(new Net(ip, port, Network.localhostIsIPv6()))
				.build();

		MongodStarter starter = MongodStarter.getDefaultInstance();
		mongodExecutable = starter.prepare(mongodConfig);
		mongodExecutable.start();
		mongoTemplate = new MongoTemplate(MongoClients.create(String.format(connectionString, ip, port)), "zubankAccount");
	}

	// @AfterEach
	void clean() {
		mongodExecutable.stop();
	}



	@DisplayName("Verify that database is connected and data stored is retrievable")
	@Test
	public void test() {
		String collectionName = "eventStore";

		var event = AccountOpenedEvent.builder()
				.customerId("68660651-ef7c-412e-aa4d-92995c99754c")
				.accountType(AccountType.CURRENT)
				.createdDate(LocalDateTime.now())
				.openingBalance(BigDecimal.valueOf(600))
				.build();

		var eventModel = EventModel.builder()
				.createdDate(LocalDateTime.now())
				.aggregateIdentifier("68660651-ef7c-412e-aa4d-92995c99721b")
				.aggregateType(AccountAggregate.class.getTypeName())
				.version(0)
				.eventType(AccountOpenedEvent.class.getTypeName())
				.eventData(event)
				.build();

		mongoTemplate.save(eventModel, collectionName);
		assertEquals(1, mongoTemplate.findAll(EventModel.class, collectionName).size());
	}
}
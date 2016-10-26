package com.YoungMoney;

import com.YoungMoney.entities.Hurricane;
import com.YoungMoney.services.HurricaneRepo;
import com.YoungMoney.services.UserRestRepo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HurricaneTrackerSpringApplicationTests {

	@Autowired
	UserRestRepo users;

	@Autowired
	HurricaneRepo hurricanes;

	@Autowired
	WebApplicationContext wac;

	MockMvc mockMvc;

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void aTestLogin() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/login")
					.param("username", "Alice")
					.param("password", "123")
		);
		Assert.assertTrue(users.findFirstByName("Alice") != null);
	}

	@Test
	public void bTestCreateHurricane() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/create-hurricane")
					.param("hName", "Sandy")
					.param("hLocation", "Charleston")
					.param("hCat", "THREE")
					.param("hImage", "")
					.param("date", LocalDate.now().toString())
					.sessionAttr("username", "Alice")

		);
		Assert.assertTrue(hurricanes.count() == 1);
	}

	@Test
	public void cTestEdit() throws Exception {
		Hurricane h = hurricanes.findAll().iterator().next();
		mockMvc.perform(
				MockMvcRequestBuilders.post("/edit-hurricane")
						.param("hName", h.name)
						.param("hLocation", h.location)
						.param("hCat", "FOUR")
						.param("hImage", h.image)
						.param("date", h.date.toString())
						.param("id", h.id + "")
						.sessionAttr("username", "Alice")

		);
		h = hurricanes.findAll().iterator().next();
		Assert.assertTrue(h.category == Hurricane.Category.FOUR);
	}

	@Test
	public void dTestDelete() throws Exception {
		Hurricane h = hurricanes.findAll().iterator().next();
		mockMvc.perform(
				MockMvcRequestBuilders.post("/delete-hurricane")
					.sessionAttr("username", "Alice")
					.param("id", h.id + "")
		);

		Assert.assertTrue(hurricanes.count() == 0);
	}
}
